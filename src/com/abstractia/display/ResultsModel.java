package com.abstractia.display;

import com.abstractia.model.*;
import java.util.*;

public class ResultsModel 
    extends ComponentModel
{
    private NavigationState state;
    private List<Record> records = new ArrayList<Record>();
    private boolean atRecordEnd;

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
}
