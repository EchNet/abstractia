package com.abstractia.model;

import java.util.*;

public class DimFilter 
{
    private Dimension dimension;
    private Dval dval;

    public DimFilter()
    {
    }

    public DimFilter(Dimension dimension, Dval dval)
    {
        this.dimension = dimension;
        this.dval = dval;
    }

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

    public boolean equals(Object obj)
    {
        if (obj instanceof DimFilter)
        {
            DimFilter that = (DimFilter) obj;
            return this.dimension.equals(that.dimension) && this.dval.equals(that.dval);
        }
        return false;
    }
}
