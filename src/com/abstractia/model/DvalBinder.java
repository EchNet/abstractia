package com.abstractia.model;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;

public abstract class DvalBinder
{
    public static Dval parse(Element dvalElement)
    {
        String value = dvalElement.getTextContent();
        if (dvalElement.hasAttribute("name"))
        {
            return new Dval(dvalElement.getAttribute("name"), value);
        }
        else
        {
            return new Dval(value);
        }
    }

    public static void print (Dval dval, PrintWriter out)
    {
        if (dval.getName().equals(dval.getValue()))
        {
            out.println("<dval>" + dval.getValue() + "</dval>");
        }
        else
        {
            out.println("<dval name=\"" + dval.getName() + "\">" + dval.getValue() + "</dval>");
        }
    }
}
