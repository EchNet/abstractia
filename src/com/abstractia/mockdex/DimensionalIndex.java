//
// DimensionalIndex.java
//

package com.abstractia.mockdex;

import com.abstractia.model.*;

/**
 * A dimensional filter.
 */
public class DimensionalIndex
{
    private Dimension dimension;
    private Dval dval;
    private Integer[] recordNumbers;

    public Dimension getDimension()
    {
        return dimension;
    }

    public void setDimension(Dimension dimension)
    {
        this.dimension = dimension;
    }

    public Dval getDval()
    {
        return dval;
    }

    public void setDval(Dval dval)
    {
        this.dval = dval;
    }

    public Integer[] getRecordNumbers()
    {
        return recordNumbers;
    }

    public void setRecordNumbers(Integer[] recordNumbers)
    {
        this.recordNumbers = recordNumbers;
    }
}
