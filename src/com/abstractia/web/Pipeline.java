//
// Pipeline.java
//

package com.abstractia.web;

import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
/**
 * 
 */
public class Pipeline
    extends PipelineBlock
{
    private List<PipelineBlock> blocks = new ArrayList<PipelineBlock>();

    public void addBlock(PipelineBlock block)
    {
        blocks.add(block);
    }

    public PipelineTask createTask(PipelineContext context)
    {
        return new PipelineTask(context)
        {
            public Object run()
                throws Exception
            {
                Object result = null;

                for (PipelineBlock block : blocks)
                {
                    result = block.createTask(getContext()).run();
                }

                return result;
            }
        };
    }
}
