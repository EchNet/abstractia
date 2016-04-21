package com.abstractia.mockdex;

import com.abstractia.model.*;
import java.util.*;

public class NavigationResult 
{
    private NavigationState state;
    private List<Record> records = new ArrayList<Record>();
    private boolean atRecordEnd;
    private Map<Dimension, List<DimRefinement>> dimRefinements;

    public NavigationState getState()
    {
        return state;
    }

    public void setState(NavigationState state)
    {
        this.state = state;
    }

    public List<Record> getRecords()
    {
        return records;
    }

    public void setRecords(List<Record> records)
    {
        this.records = records;
    }

    public boolean isAtRecordEnd()
    {
        return atRecordEnd;
    }

    public void setAtRecordEnd(boolean atRecordEnd)
    {
        this.atRecordEnd = atRecordEnd;
    }

    public Map<Dimension, List<DimRefinement>> getDimRefinements()
    {
        return dimRefinements;
    }

    public void setDimRefinements(Map<Dimension, List<DimRefinement>> dimRefinements)
    {
        this.dimRefinements = dimRefinements;
    }
}
