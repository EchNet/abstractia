//
// PipelineBlock.java
//

package com.abstractia.web;

/**
 * A unit of a pipeline.
 */
public class PipelineBlock
{
    public PipelineTask createTask(PipelineContext context)
    {
        return new PipelineTask(context);
    }
}
