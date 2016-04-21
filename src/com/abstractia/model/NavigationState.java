package com.abstractia.model;

import java.util.*;

public class NavigationState 
{
    private List<DimFilter> dimFilters = new ArrayList<DimFilter>();
    private int recordStart;

    public List<DimFilter> getDimFilters()
    {
        return dimFilters;
    }

    public void setDimFilters(List<DimFilter> dimFilters)
    {
        this.dimFilters = dimFilters;
    }

    public int getRecordStart()
    {
        return recordStart;
    }

    public void setRecordStart(int recordStart)
    {
        this.recordStart = recordStart;
    }

    /**
     * TODO: a model class should not have member methods.
     */
    public NavigationState page(int recordStart)
    {
        NavigationState that = copy();
        that.setRecordStart(recordStart);
        return that;
    }

    /**
     * TODO: a model class should not have member methods.
     */
    public NavigationState addDimFilter(DimFilter dimFilter)
    {
        NavigationState that = copy();

        for (int i = 0; i < that.dimFilters.size(); ++i)
        {
            if (that.dimFilters.get(i).getDimension().equals(dimFilter.getDimension()))
            {
                that.dimFilters.remove(i);
                break;
            }
        }

        that.dimFilters.add(dimFilter);
        that.setRecordStart(0);

        return that;
    }

    /**
     * ditto
     */
    public NavigationState removeDimFilter(DimFilter dimFilter)
    {
        NavigationState that = copy();

        for (int i = 0; i < that.dimFilters.size(); ++i)
        {
            if (that.dimFilters.get(i).equals(dimFilter))
            {
                that.dimFilters.remove(i);
                that.setRecordStart(0);
                break;
            }
        }

        return that;
    }

    private NavigationState copy()
    {
        NavigationState that = new NavigationState();
        that.dimFilters = new ArrayList<DimFilter>();
        for (DimFilter df : this.dimFilters)
        {
            that.dimFilters.add(df);
        }
        that.recordStart = this.recordStart;
        return that;
    }
}
