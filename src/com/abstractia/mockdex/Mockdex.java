//
// Mockdex.java
//

package com.abstractia.mockdex;

import com.abstractia.model.*;
import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

/**
 * The bleeding MockDEX.
 */
public class Mockdex
{
    private Metadata metadata = new Metadata();
    private RecordSet recordSet = new RecordSet();
    private DimensionalIndex[] indexes;

    public Mockdex(Document doc)
    {
        loadMetadata(doc);
        initIndexes();
        loadIndexes(doc);
    }

    public Iterator<String> dimensionNames()
    {
        return metadata.getDimensionNames().iterator();
    }

    public NavigationResult navigate(final NavigationQuery query)
    {
        final NavigationResult result = new NavigationResult();

        new Runnable()
        {
            private NavigationState state = new NavigationState();
            private IFilterExec filterExec;
            private AndFilterExec afe;
            private int[][] binCounts;

            public void run()
            {
                state.setRecordStart(query.getRecordStart());
                result.setState(state);

                handleRecordFilterSpec();
                handleDimFilterSpecs();
                handleUnfiltered();
                initBinCounts();
                evaluate();
                captureRefinements();
            }

            private void handleRecordFilterSpec()
            {
                String recordFilterSpec = query.getRecordFilterSpec();
                if ("demo".equals(recordFilterSpec))
                {
                    filterExec = new DemoFilterExec(recordSet.allNumbers(), metadata);
                }
                else if (recordFilterSpec != null && recordFilterSpec.length() > 0)
                {
                    // The only other form of record filter spec currently - 
                    // a comma-separated list of record numbers.

                    filterExec = new IndexFilterExec(createLiteralRecordIndex(recordFilterSpec));
                }
            }

            private Integer[] createLiteralRecordIndex(String spec)
            {
                StringTokenizer toks = new StringTokenizer(spec, ",");
                List<Integer> numList = new ArrayList<Integer>();
                while (toks.hasMoreTokens())
                {
                    Integer num = Integer.valueOf(toks.nextToken());
                    if (recordSet.hasRecord(num))
                    {
                        numList.add(num);
                    }
                }
                Integer[] numArray = (Integer[]) numList.toArray(new Integer[0]);
                Arrays.sort(numArray);
                return numArray;
            }

            private void handleDimFilterSpecs()
            {
                List<String> filterSpecs = query.getDimFilterSpecs();
                if (filterSpecs != null)
                {
                    for (String filterSpec : filterSpecs)
                    {
                        IFilterExec ife;
                        try
                        {
                            // Parse the dimension=dval spec, look up the dval's index.
                            DimensionalIndex dimIndex = parseDimIndex(filterSpec);

                            // Get ready to walk the dval's index.
                            ife = new IndexFilterExec(dimIndex.getRecordNumbers());

                            addCrumb(dimIndex);
                        }
                        catch (java.io.IOException e)
                        {
                            ife = new NullFilterExec();
                        }

                        // Combine this index walker with previous ones, if any.
                        if (filterExec == null)
                        {
                            filterExec = ife;
                        }
                        else if (afe == null)
                        {
                            afe = new AndFilterExec();
                            afe.addChild(filterExec);
                            afe.addChild(ife);
                            filterExec = afe;
                        }
                        else
                        {
                            afe.addChild(ife);
                        }
                    }
                }
            }

            private void handleUnfiltered()
            {
                if (filterExec == null)
                {
                    filterExec = new IndexFilterExec(recordSet.allNumbers());
                }
            }

            private void evaluate()
            {
                filterExec.reset();
                int count = 0;
                boolean lastRecordShown = false;
                while (!filterExec.atEnd())
                {
                    Integer recordNumber = filterExec.getCurrentValue();

                    tallyBinCounts(recordNumber);

                    int off = count - query.getRecordStart();
                    lastRecordShown = off >= 0 && off < query.getRecordLimit();
                    if (lastRecordShown)
                    {
                        result.getRecords().add(
                            makeRecord(recordNumber, query.getRecordDetailLevel()));
                    }

                    filterExec.advance();
                    ++count;
                }
                result.setAtRecordEnd(lastRecordShown);
            }

            private DimensionalIndex parseDimIndex(String filterSpec)
                throws java.io.IOException  /* is there a better type to throw? */
            {
                StringTokenizer toks = new StringTokenizer(filterSpec, "=");
                String dimensionName = toks.nextToken();
                String dvalName = toks.nextToken();

                DimensionalIndex dimIndex = findDimIndex(dimensionName, dvalName);
                if (dimIndex == null)
                    throw new java.io.IOException();
                return dimIndex;
            }

            private void addCrumb(DimensionalIndex dimIndex)
            {
                DimFilter df = new DimFilter();
                df.setDimension(dimIndex.getDimension());
                df.setDval(dimIndex.getDval());
                state.getDimFilters().add(df);
            }

            private void initBinCounts()
            {
                binCounts = new int[metadata.getDimensionList().size()][];
                int dimIndex = 0;
                for (Dimension d : metadata.getDimensionList())
                {
                    if (d.cardinality() <= 20)
                    {
                        binCounts[dimIndex] = new int[d.cardinality()];
                    }
                    ++dimIndex;
                }
            }

            private void tallyBinCounts(Integer recordNumber)
            {
                int n = recordNumber.intValue();
                int dimIndex = 0;
                for (Dimension d : metadata.getDimensionList())
                {
                    int c = d.cardinality();
                    int dvalIndex = n % c;
                    if (binCounts[dimIndex] != null)
                        binCounts[dimIndex][dvalIndex] += 1;
                    n /= c;
                    ++dimIndex;
                }
            }

            private void captureRefinements()
            {
                result.setDimRefinements(new HashMap<Dimension, List<DimRefinement>>());

                int dimIndex = 0;
                for (Dimension d : metadata.getDimensionList())
                {
                    int[] dimCounts = binCounts[dimIndex];
                    if (dimCounts != null)
                    {
                        int nbins = 0;
                        for (int i = 0; i < dimCounts.length; ++i)
                        {
                            if (dimCounts[i] > 0)
                                ++nbins;
                        }
                        if (nbins > 1)
                        {
                            List<DimRefinement> refinements = new ArrayList<DimRefinement>();
                            for (int i = 0; i < dimCounts.length; ++i)
                            {
                                if (dimCounts[i] > 0)
                                {
                                    refinements.add(new DimRefinement(d.getDvals().get(i), dimCounts[i]));
                                }
                            }
                            result.getDimRefinements().put(d, refinements);
                        }
                    }
                    ++dimIndex;
                }
            }
        }.run();

        return result;
    }

    private Record makeRecord(Integer recordNumber, int recordDetailLevel)
    {
        Record record;
        if (recordDetailLevel > 0)
        {
            record = recordSet.getRecord(recordNumber, metadata);
        }
        else
        {
            record = new Record(recordNumber);
        }
        return record;
    }

    private void loadMetadata(Document doc)
    {
        NodeList dimNodes = doc.getElementsByTagName("dimension");
        for (int i = 0; i < dimNodes.getLength(); ++i)
        {
            Element dimElement = (Element) dimNodes.item(i);
            Dimension dimension = DimensionBinder.parse(dimElement);
            if (dimension.getName() == null)
                throw new RuntimeException ("dimension with no name");
            for (Dval dval : dimension.getDvals())
            {
                if (dval.getName() == null)
                    throw new RuntimeException ("dval with no name");
            }
            metadata.add(dimension);
        }
    }

    private void initIndexes()
    {
        List<DimensionalIndex> tempList = new ArrayList<DimensionalIndex>();
        for (Dimension dimension : metadata.getDimensionList())
        {
            for (Dval dval : dimension.getDvals())
            {
                DimensionalIndex dindex = new DimensionalIndex();
                dindex.setDimension(dimension);
                dindex.setDval(dval);
                tempList.add(dindex);
            }
        }
        indexes = (DimensionalIndex[]) tempList.toArray(new DimensionalIndex[0]);
    }

    private void loadIndexes(Document doc)
    {
        NodeList indexNodes = doc.getElementsByTagName("index");
        for (int i = 0; i < indexNodes.getLength(); ++i)
        {
            Element indexElement = (Element) indexNodes.item(i);
            String dimensionName = indexElement.getAttribute("dimension");
            String dvalName = indexElement.getAttribute("dval");
            StringTokenizer toks = new StringTokenizer(indexElement.getTextContent(), ",");

            Integer[] rns = new Integer[toks.countTokens()];
            for (int j = 0; j < rns.length; ++j)
            {
                Integer recordNumber = Integer.valueOf(toks.nextToken());
                recordSet.add(recordNumber);
                rns[j] = recordNumber;
            }
            findDimIndex(dimensionName, dvalName).setRecordNumbers(rns);
        }
    }

    private DimensionalIndex findDimIndex(String dimensionName, String dvalName)
    {
        for (DimensionalIndex di : indexes)
        {
            if (di.getDimension().getName().equals(dimensionName) &&
                di.getDval().getName().equals(dvalName))
            {
                return di;
            }
        }
        return null;
    }
}
