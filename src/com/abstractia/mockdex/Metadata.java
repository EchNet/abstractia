//
// Metadata.java
//

package com.abstractia.mockdex;

import com.abstractia.model.*;
import java.util.*;

/**
 * How metadata is represented in the bleeding MockDEX.
 */
public class Metadata
{
    private List<Dimension> dimensionList = new ArrayList<Dimension>();
    private Map<String,Dimension> dimensionMap = new HashMap<String,Dimension>();

    public void add(Dimension dimension)
    {
        dimensionList.add(dimension);
        dimensionMap.put(dimension.getName(), dimension);
    }

    public Set<String> getDimensionNames()
    {
        return dimensionMap.keySet();
    }

    public List<Dimension> getDimensionList()
    {
        return dimensionList;
    }
}
