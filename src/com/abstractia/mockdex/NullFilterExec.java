//
// IndexFilterExec.java
//

package com.abstractia.mockdex;

/**
 * A dead-in-the-water dimensional filter execution.
 */
public class NullFilterExec
    implements IFilterExec
{
    public boolean atEnd()
    {
        return true;
    }

    public Integer getCurrentValue()
    {
        return null;
    }

    public void reset()
    {
    }

    public void advance()
    {
    }
}
