package com.abstractia.model;

import java.util.*;

public class DimRefinement 
{
    private Dval dval;
    private int binCount;

    public DimRefinement()
    {
    }

    public DimRefinement(Dval dval, int binCount)
    {
        this.dval = dval;
        this.binCount = binCount;
    }

    public Dval getDval()
    {
        return dval;
    }

    public void setDval(Dval dval)
    {
        this.dval = dval;
    }

    public int getBinCount()
    {
        return binCount;
    }

    public void setBinCount(int binCount)
    {
        this.binCount = binCount;
    }
}
