//
// RecordSet.java
//

package com.abstractia.mockdex;

import com.abstractia.model.*;
import java.util.*;

/**
 * How records are represented in the mighty MockDEX.
 */
public class RecordSet
{
    private Set<Integer> recordNumberSet = new HashSet<Integer>();
    private Integer[] recordNumbers;

    public void add (Integer recordNumber)
    {
        recordNumberSet.add(recordNumber);
        recordNumbers = null;
    }

    public boolean hasRecord(Integer recordNumber)
    {
        return recordNumberSet.contains(recordNumber);
    }

    public Record getRecord(Integer recordNumber, Metadata metadata)
    {
        if (recordNumberSet.contains(recordNumber))
        {
            return composeRecord(recordNumber, metadata);
        }
        return null;
    }

    public Integer[] allNumbers()
    {
        if (recordNumbers == null)
        {
            recordNumbers = (Integer[]) recordNumberSet.toArray(new Integer[0]);
            Arrays.sort(recordNumbers);
        }
        return recordNumbers;
    }

    private Record composeRecord(Integer recordNumber, Metadata metadata)
    {
        Record r = new Record(recordNumber);

        int n = recordNumber.intValue();
        for (Dimension d : metadata.getDimensionList())
        {
            int c = d.cardinality();
            r.put(d.getName(), d.getDval(n % c));
            n /= c;
        }

        return r;
    }
}
