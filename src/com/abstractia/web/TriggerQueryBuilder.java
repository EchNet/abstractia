//
// TriggerQueryBuilder.java
//

package com.abstractia.web;

import com.abstractia.mockdex.NavigationQuery;
import java.util.*;
import javax.servlet.http.*;

/**
 * TriggerQueryBuilder task forms a trigger query from the data query.
 */
public class TriggerQueryBuilder
    extends PipelineBlock
{
    public PipelineTask createTask(PipelineContext context)
    {
        return new PipelineTask(context)
        {
            public Object run()
                throws Exception
            {
                // Just the facts, Ma'am.
                Map<String, Object> facts = new HashMap<String, Object>();

                // Navigation query parameters qualify as facts.
                NavigationQuery navigationQuery = getContext().gett(NavigationQuery.class, "navigationQuery");
                for (String dfSpec : navigationQuery.getDimFilterSpecs())
                {
                    int eq = dfSpec.indexOf('=');
                    String dimName = dfSpec.substring(0, eq);
                    String dimValue = dfSpec.substring(eq + 1);
                    facts.put("dimension." + dimName, dimValue);
                }
                facts.put("subscript", new Integer(navigationQuery.getRecordStart()));

                // The original HTTP request supplies some more.
                HttpServletRequest req = getContext().gett(HttpServletRequest.class, "request");
                Cookie[] cookies = req.getCookies();
                if (cookies != null)
                {
                    for (int i = 0; i < cookies.length; ++i)
                    {
                        if ("user".equals(cookies[i].getName()))
                        {
                            facts.put("user", cookies[i].getValue());
                        }
                    }
                }
                // TODO...
                //String userAgent = req.getHeader("User-agent");

                // Then there's the system clock.
                Calendar now = Calendar.getInstance();
                facts.put("calendar.dayOfWeek", now.get(Calendar.DAY_OF_WEEK));
                facts.put("calendar.hourOfDay", now.get(Calendar.HOUR_OF_DAY));

                getContext().put("facts", facts);
                return facts;
            }
        };
    }
}
