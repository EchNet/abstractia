package com.abstractia.mockdex;

import java.util.*;

public class NavigationQuery 
{
    private String recordFilterSpec;
    private List<String> dimFilterSpecs = new ArrayList<String>();
    private int recordStart;
    private int recordLimit = 10;
    private int recordDetailLevel;

    public String getRecordFilterSpec()
    {
        return recordFilterSpec;
    }

    public void setRecordFilterSpec(String recordFilterSpec)
    {
        this.recordFilterSpec = recordFilterSpec;
    }

    public List<String> getDimFilterSpecs()
    {
        return dimFilterSpecs;
    }

    public void setDimFilterSpecs(List<String> dimFilterSpecs)
    {
        this.dimFilterSpecs = dimFilterSpecs;
    }

    public int getRecordStart()
    {
        return recordStart;
    }

    public void setRecordStart(int recordStart)
    {
        this.recordStart = recordStart;
    }

    public int getRecordLimit()
    {
        return recordLimit;
    }

    public void setRecordLimit(int recordLimit)
    {
        this.recordLimit = recordLimit;
    }

    public int getRecordDetailLevel()
    {
        return recordDetailLevel;
    }

    public void setRecordDetailLevel(int recordDetailLevel)
    {
        this.recordDetailLevel = recordDetailLevel;
    }
}
