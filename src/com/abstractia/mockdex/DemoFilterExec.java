//
// DemoFilterExec.java
//

package com.abstractia.mockdex;

import com.abstractia.model.*;

/**
 * Special filter for demo.
 */
public class DemoFilterExec
    implements IFilterExec
{
    private Integer[] recordNumbers;
    private Metadata metadata;
    private int index;

    public DemoFilterExec(Integer[] recordNumbers, Metadata metadata)
    {
        this.recordNumbers = recordNumbers;
        this.metadata = metadata;
    }

    public boolean atEnd()
    {
        return index >= recordNumbers.length;
    }

    public Integer getCurrentValue()
    {
        return recordNumbers[index];
    }

    public void reset()
    {
        index = 0;

        if (!atEnd() && !acceptCurrent())
        {
            advance(); 
        }
    }

    public void advance()
    {
        if (!atEnd() && acceptCurrent())
        {
            ++index;
        }

        if (atEnd()) return;

        while (!acceptCurrent())
        {
            ++index;
            if (atEnd()) return;
        }
    }

    private boolean acceptCurrent()
    {
        int n = recordNumbers[index].intValue();

        for (Dimension d : metadata.getDimensionList())
        {
            int c = d.cardinality();
            if ((n % c) == 0)
                return false;
            n /= c;
        }
        return true;
    }
}
