//
// TriggerDataHandler.java
//

package com.abstractia.web;

import com.abstractia.mockdex.NavigationQuery;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Pretend to query a rules engine.
 */
public class TriggerDataHandler
    extends PipelineBlock
{
    public PipelineTask createTask(PipelineContext context)
    {
        return new PipelineTask(context)
        {
            public Object run()
                throws Exception
            {
                // Put those facts to use.
                Map facts = getContext().gett(Map.class, "facts");

                // In theory, the nav query could be altered by facts,
                // e.g., in the case of boost/bury.
                NavigationQuery query = getContext().gett(NavigationQuery.class, "navigationQuery");

                // Hack up a category template.
                if (facts.get("dimension.bgcolor") != null &&
                    new Integer(0).equals(facts.get("subscript")) )
                {
                    getContext().put("display.featured", "");
                    query.setRecordLimit(query.getRecordLimit() + 1);
                }

                if ("black".equals(facts.get("dimension.color")))
                {
                    getContext().put("display.promo", "black.jpg");
                }

                if (facts.get("user") != null)
                {
                    getContext().put("display.loggedIn", new Boolean(true));
                    getContext().put("display.title", "Welcome back, " + facts.get("user"));
                }
                else
                {
                    query.setRecordFilterSpec("demo");
                }

                return null;
            }
        };
    }
}
