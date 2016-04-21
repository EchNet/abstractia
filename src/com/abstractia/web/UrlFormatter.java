//
// UrlFormatter.java
//

package com.abstractia.web;

import com.abstractia.mockdex.Mockdex;
import com.abstractia.model.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 * That's right, I format URLs.
 */
public class UrlFormatter
{
    private HttpServletRequest request;

    public UrlFormatter(HttpServletRequest request)
    {
        this.request = request;
    }

    public String format(NavigationState state)
    {
        StringBuffer url = new StringBuffer();
        url.append(request.getRequestURL());   // is this the request minus query params?

        int paramCount = 0;
        for (DimFilter dimFilter : state.getDimFilters())
        {
            url.append(paramCount > 0 ? "&" : "?");
            url.append("D");
            url.append(dimFilter.getDimension().getName());
            url.append("=");
            url.append(dimFilter.getDval().getName());
            ++paramCount;
        }

        if (state.getRecordStart() != 0)
        {
            url.append(paramCount > 0 ? "&O=" : "?O=");
            url.append(state.getRecordStart());
            ++paramCount;
        }

        return url.toString();
    }
}
