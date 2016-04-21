package com.abstractia.model;

import java.io.*;
import java.util.*;
import org.w3c.dom.*;

public abstract class RecordBinder
{
    /****
    public static Record parse(Element rElement)
    {
        Record r = new Record();

        NodeList attrsAndMore = rElement.getChildNodes();
        for (int i = 0; i < attrsAndMore.getLength(); ++i)
        {
            Node n = attrsAndMore.item(i);
            if (n.getNodeType() == Node.ATTRIBUTE_NODE)
            {
                r.put(n.getNodeName(), n.getNodeValue());
            }
        }

        return r;
    }
    ****/

    public static void print (Record record, PrintWriter out)
    {
        out.print("<record _n=\"" + record.getRecordNumber() + "\"");

        for (String key : record.keySet())
        {
            out.print(" ");
            out.print(key);
            out.print("=\"");

            Dval dval = record.get(key);
            if (dval.getName().equals(dval.getValue()))
            {
                out.print(dval.getName());
            }
            else
            {
                out.print(dval.getName() + "/" + dval.getValue());
            }
            out.print("\"");
        }

        out.println("/>");
    }
}
