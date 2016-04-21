//
// PipelineContext.java
//

package com.abstractia.web;

import java.util.*;
import javax.servlet.http.*;

/**
 * 
 */
public class PipelineContext
    extends HashMap<String, Object>
{
    public PipelineContext(HttpServletRequest request,
                           HttpServletResponse response)
    {
        put("request", request);
        put("response", response);
    }

    public <T> T get(Class<T> clazz, String key)
    {
        try
        {
            return (T) get(key);
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException("Hey, context item " + key + " is not an instance of " + clazz);
        }
    }

    public <T> T gett(Class<T> clazz, String key)
    {
        T obj = get(clazz, key);
        if (obj == null)
        {
            throw new NullPointerException("Hey, there's no context item named " + key + "!");
        }
        return obj;
    }
}
