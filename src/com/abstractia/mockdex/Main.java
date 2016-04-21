//
// Main.java
//

package com.abstractia.mockdex;

import com.abstractia.model.*;
import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class Main
{
    public static void main(String[] args)
    {
        try
        {
            new Main(args).run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private String dataPath;
    private Mockdex mdex;
    private NavigationQuery query;

    public Main(String[] args)
    {
        dataPath = args[0];
    }

    public void run()
        throws Exception
    {
        mdex = new Mockdex(parseDocument(dataPath));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        for (;;)
        {
            System.out.print ("> ");
            System.out.flush ();
            String line = in.readLine();
            if (line == null)
                break;
            try
            {
                line = line.trim();
                if (line.length () == 0)
                {
                    if (query == null)
                    {
                        query = newQuery();
                    }
                    else
                    {
                        query.setRecordStart(query.getRecordStart() + query.getRecordLimit());
                    }
                    go();
                }
                else if (line.equals("q") || line.equals("quit") ||
                         line.equals("exit"))
                {
                    break;
                }
                else
                {
                    query = newQuery();
                    StringTokenizer toks = new StringTokenizer(line);
                    query.setDimFilterSpecs(new ArrayList<String>());
                    while (toks.hasMoreTokens())
                    {
                        String term = toks.nextToken();
                        if (term.indexOf("=") < 0) 
                            throw new Exception("missing =");
                        query.getDimFilterSpecs().add(term);
                    }
                    go();
                }
            }
            catch (Exception e)
            {
                System.out.println("ERROR: " + e.toString());
                query = null;
            }
        }
    }

    private NavigationQuery newQuery()
    {
        NavigationQuery query = new NavigationQuery();
        query.setRecordDetailLevel(1);
        return query;
    }

    private void go()
        throws Exception
    {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        NavigationResult nav = mdex.navigate(query);
        for (Record rec : nav.getRecords())
        {
            RecordBinder.print(rec, out);
        }
        out.flush();
    }

    private Document parseDocument(String fileName)
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
