//
// CAS/forge.  no, really.
//

package com.abstractia.casinova;

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

    private int numRecords = 10000;
    private List<String> inputFiles = new ArrayList<String>();
    private List<Dimension> dimensions = new ArrayList<Dimension>();
    private int numCombos;
    private PrintWriter out;

    public Main(String[] args)
    {
        out = new PrintWriter(new OutputStreamWriter(System.out));

        for (int i = 0; i < args.length; ++i)
        {
            if (args[i].startsWith("-"))
            {
                if (args[i].equals("-n"))
                {
                    ++i;
                    numRecords = Integer.parseInt(args[i]);
                }
            }
            else
            {
                openDir(args[i]);
            }
        }
    }

    private void openDir(String dirName)
    {
        File dir = new File(dirName);
        String[] files = dir.list();
        for (int i = 0; i < files.length; ++i)
        {
            if (files[i].endsWith(".xml"))
            {
                inputFiles.add(new File(dir, files[i]).toString());
            }
        }
    }

    public void run()
        throws Exception
    {
        if (inputFiles.size() == 0)
        {
            throw new Exception ("no input files.");
        }

        loadMetadata();

        if (numRecords > numCombos / 100)
        {
            throw new Exception ("that's too many records.");
        }

        printHeader();
        printInputs();
        printMetadata();
        generateData();
        printFooter();
    }

    private void printHeader()
    {
        out.println("<?xml version=\"1.0\"?>");
        out.println("<abstractia>");
    }

    private void printFooter()
    {
        out.println("</abstractia>");
        out.flush();
    }

    private void printInputs()
    {
        out.println("  <input>");

        out.println("    <numRecords>" + numRecords + "</numRecords>");

        for (String fileName : inputFiles)
        {
            out.println("    <file>" + fileName + "</file>");
        }

        out.println("  </input>");
    }

    private void loadMetadata()
        throws Exception
    {
        for (String fileName : inputFiles)
        {
            Document doc = parseDocument(fileName);

            NodeList dimNodes = doc.getElementsByTagName("dimension");
            for (int i = 0; i < dimNodes.getLength(); ++i)
            {
                Element dimElement = (Element) dimNodes.item(i);
                dimensions.add(DimensionBinder.parse(dimElement));
            }
        }

        numCombos = calcNumCombos();
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

    private void printMetadata()
    {
        out.println("<metadata><numCombos>" + numCombos + "</numCombos>");

        for (Dimension d : dimensions)
        {
            DimensionBinder.print(d, out);
        }

        out.println("</metadata>");
    }

    private void generateData()
    {
        printIndexes(buildIndexes(generateRecordNumbers()));
    }

    private Integer[] generateRecordNumbers()
    {
        Set<Integer> generated = new HashSet<Integer>();

        for (int i = 0; i < numRecords; ++i)
        {
            for (;;)
            {
                int recordNumber = (int) (Math.random() * numCombos);
                Integer irn = new Integer(recordNumber);
                if (!generated.contains(irn))
                {
                    generated.add(irn);
                    break;
                }
            }
        }

        Integer[] recordNumbers = (Integer[]) generated.toArray(new Integer[0]);
        Arrays.sort(recordNumbers);
        return recordNumbers;
    }

    private Map<String, List<Integer> > buildIndexes(Integer[] recordNumbers)
    {
        Map<String, List<Integer> > indexes = new HashMap<String, List<Integer> >();

        for (int i = 0; i < recordNumbers.length; ++i)
        {
            Integer rn = recordNumbers[i];
            int n = rn.intValue();
            for (Dimension d : dimensions)
            {
                int c = d.cardinality();
                Dval dval = d.getDval(n % c);

                String indexId = d.getName() + "/" + dval.getName();
                List<Integer> index = indexes.get(indexId);
                if (index == null)
                {
                    index = new ArrayList<Integer>();
                    indexes.put(indexId, index);
                }
                index.add(rn);

                n /= c;
            }
        }

        return indexes;
    }

    private void printIndexes (Map<String, List<Integer> > indexes)
    {
        for (String key : indexes.keySet())
        {
            StringTokenizer toks = new StringTokenizer(key, "/");
            String dimensionName = toks.nextToken();
            String dvalName = toks.nextToken();

            out.print("<index dimension=\"" + dimensionName +
                      "\" dval=\"" + dvalName + "\">");

            List<Integer> list = indexes.get(key);
            for (int i = 0; i < list.size(); ++i)
            {
                if (i > 0) out.print(",");
                out.print(list.get(i));
            }

            out.println("</index>");
        }
    }

    private int calcNumCombos()
    {
        int numCombos = 0;
        if (dimensions.size() > 0)
        {
            numCombos = 1;
            for (Dimension d : dimensions)
            {
                numCombos *= d.cardinality();
            }
        }
        return numCombos;
    }
}
