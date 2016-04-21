//
// PipelineTask.java
//

package com.abstractia.web;

/**
 * 
 */
public class PipelineTask
{
    private PipelineContext context;

    public PipelineTask(PipelineContext context)
    {
        this.context = context;
    }

    public PipelineContext getContext()
    {
        return context;
    }

    /**
     * Override me.
     */
    public Object run()
        throws Exception
    {
        return null;
    }
}
