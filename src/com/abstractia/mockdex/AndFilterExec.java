//
// AndFilterExec.java
//

package com.abstractia.mockdex;

import java.util.*;

/**
 * Executor of a raft of dimensional filters.
 */
public class AndFilterExec
    implements IFilterExec
{
    private List<IFilterExec> children = new ArrayList<IFilterExec>();
    
    public void addChild(IFilterExec child)
    {
        children.add(child);
    }

    public boolean atEnd()
    {
        for (Iterator<IFilterExec> iter = children.iterator(); iter.hasNext(); )
        {
            if (iter.next().atEnd())
            {
                return true;
            }
        }
        return false;
    }

    public Integer getCurrentValue()
    {
        return children.get(0).getCurrentValue();
    }

    public void reset()
    {
        for (Iterator<IFilterExec> iter = children.iterator(); iter.hasNext(); )
        {
            iter.next().reset();
        }

        if (!atEnd() && getMinValue() < getMaxValue())
        {
            advance(); 
        }
    }

    public void advance()
    {
        if (getMinValue() == getMaxValue())
        {
            for (Iterator<IFilterExec> iter = children.iterator(); iter.hasNext(); )
            {
                iter.next().advance();
            }
        }

        if (atEnd()) return;

        int maxValue;

        while (getMinValue() < (maxValue = getMaxValue()))
        {
            for (Iterator<IFilterExec> iter = children.iterator(); iter.hasNext(); )
            {
                IFilterExec child = iter.next();
                while (child.getCurrentValue() < maxValue)
                {
                    child.advance();
                    if (child.atEnd()) return;
                }
            }
        }
    }

    private int getMinValue()
    {
        int minValue = children.get(0).getCurrentValue().intValue();
        for (int i = 1; i < children.size(); ++i)
        {
            minValue = Math.min(minValue, children.get(i).getCurrentValue().intValue());
        }
        return minValue;
    }

    private int getMaxValue()
    {
        int maxValue = children.get(0).getCurrentValue().intValue();
        for (int i = 1; i < children.size(); ++i)
        {
            maxValue = Math.max(maxValue, children.get(i).getCurrentValue().intValue());
        }
        return maxValue;
    }
}
