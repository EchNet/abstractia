//
// MockdexQueryBuilder.java
//

package com.abstractia.web;

import com.abstractia.mockdex.NavigationQuery;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * MockdexQueryBuilder parses the servlet request into a navigation request.
 */
public class MockdexQueryBuilder
    extends PipelineBlock
{
    private String requestKey = "request";
    private String navigationQueryKey = "navigationQuery";

    public void setRequestKey(String requestKey)
    {
        this.requestKey = requestKey;
    }

    public PipelineTask createTask(PipelineContext context)
    {
        return new PipelineTask(context)
        {
            public Object run()
                throws Exception
            {
                HttpServletRequest req = getContext().gett(HttpServletRequest.class, requestKey);

                NavigationQuery query = new NavigationQuery();
                query.setRecordDetailLevel (1);

                // TODO: do something with path info.
                String pathInfo = req.getPathInfo();

                for (Object key : req.getParameterMap().keySet())
                {
                    String name = key.toString();
                    String value = req.getParameter(name);

                    if (name.startsWith("D"))
                    {
                        query.getDimFilterSpecs().add(name.substring(1) + "=" + value);
                    }
                    else if (name.equals("O"))
                    {
                        query.setRecordStart(Integer.parseInt(value));
                    }
                    else if (name.equals("R"))
                    {
                        Integer r = Integer.valueOf(value);
                        String rs = query.getRecordFilterSpec();
                        if (rs == null)
                        {
                            rs = r.toString();
                        }
                        else
                        {
                            rs += "," + r;
                        }
                        query.setRecordFilterSpec(rs);
                    }
                    else
                    {
                        throw new ServletException("unrecognized parameter: " + key);
                    }
                }

                getContext().put(navigationQueryKey, query);
                return query;
            }
        };
    }
}
