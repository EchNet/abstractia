//
// IFilterExec.java
//

package com.abstractia.mockdex;

/**
 * Interface to a dimensional filter execution.
 */
public interface IFilterExec
{
    public boolean atEnd();

    public Integer getCurrentValue();

    public void reset();

    public void advance();
}
