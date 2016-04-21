package com.abstractia.display;

import com.abstractia.model.Record;
import java.util.*;

public class FeaturedModel
    extends ComponentModel
{
    private Record record;

    public Record getRecord()
    {
        return record;
    }

    public void setRecord(Record record)
    {
        this.record = record;
    }
}
