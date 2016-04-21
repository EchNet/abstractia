package com.abstractia.model;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;

public abstract class DimensionBinder
{
    public static Dimension parse(Element dimElement)
    {
        Dimension dim = new Dimension(dimElement.getAttribute("name"));

        NodeList dvalNodes = dimElement.getElementsByTagName("dval");
        for (int j = 0; j < dvalNodes.getLength(); ++j)
        {
            Element dvalElement = (Element) dvalNodes.item(j);
            dim.add (DvalBinder.parse(dvalElement));
        }

        return dim;
    }

    public static void print (Dimension dimension, PrintWriter out)
    {
        out.print("<dimension name=\"" + dimension.getName() + "\">");

        for (Iterator<Dval> iter = dimension.getDvals().iterator(); iter.hasNext(); )
        {
            DvalBinder.print (iter.next(), out);
        }

        out.println("</dimension>");
    }
}
