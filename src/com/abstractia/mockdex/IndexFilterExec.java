//
// IndexFilterExec.java
//

package com.abstractia.mockdex;

/**
 * A dimensional filter execution.  Single index.
 */
public class IndexFilterExec
    implements IFilterExec
{
    private Integer[] recordNumbers;
    private int index;

    public IndexFilterExec(Integer[] recordNumbers)
    {
        this.recordNumbers = recordNumbers;
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
    }

    public void advance()
    {
        ++index;
    }
}
