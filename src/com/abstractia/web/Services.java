//
// Services.java
//

package com.abstractia.web;

import com.abstractia.mockdex.Mockdex;
import java.io.*;
import java.util.*;
import javax.servlet.*;    
import javax.servlet.http.*;    
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

/**
 * This is a singleton, for now.
 */
public class Services
{
    private static Services instance;

    private Mockdex mdex;

    public static void init(ServletConfig servletConfig)
        throws ServletException
    {
        if (instance == null)
        {
            String dataPath = servletConfig.getInitParameter("dataPath");
            if (dataPath == null)
            {
                throw new ServletException("no dataPath provided.");
            }

            Mockdex mdex;
            try
            {
                mdex = new Mockdex(parseDocument(dataPath));
            }
            catch (Exception e)
            {
                throw new ServletException (e);
            }

            instance = new Services();
            instance.mdex = mdex;
        }
    }

    public static Services getInstance()
    {
        return instance;
    }

    public Mockdex getMdex()
    {
        return mdex;
    }

    private static Document parseDocument(String fileName)
        throws Exception
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
        dbf.setValidating(false);
        dbf.setIgnoringComments(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new FileInputStream(fileName));
        is.setSystemId(fileName);
        return db.parse(is);
    }
}
