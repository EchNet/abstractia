package com.abstractia.model;

import java.util.*;

public class Record 
    extends HashMap<String, Dval>
{
    private Integer recordNumber;

    public Record()
    {
    }

    public Record (Integer recordNumber)
    {
        setRecordNumber(recordNumber);
    }

    public Integer getRecordNumber()
    {
        return recordNumber;
    }

    public void setRecordNumber(Integer recordNumber)
    {
        this.recordNumber = recordNumber;
    }
}
