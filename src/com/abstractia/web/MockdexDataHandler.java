//
// MockdexDataHandler.java
//

package com.abstractia.web;

import com.abstractia.mockdex.NavigationQuery;
import com.abstractia.mockdex.NavigationResult;
import java.io.*;
import java.util.*;

/**
 * 
 */
public class MockdexDataHandler
    extends PipelineBlock
{
    public PipelineTask createTask(PipelineContext context)
    {
        return new PipelineTask(context)
        {
            public Object run()
                throws Exception
            {
                NavigationQuery query =  getContext().gett(NavigationQuery.class, "navigationQuery");
                NavigationResult result = Services.getInstance().getMdex().navigate(query);
                getContext().put("navigationResult", result);
                return result;
            }
        };
    }
}
