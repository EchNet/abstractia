//
// ModelAndViewGrabber.java
//

package com.abstractia.web;

/**
 * 
 */
public class ModelAndViewGrabber
    extends PipelineBlock
{
    private String modelKey;
    private String defaultView;

    public ModelAndViewGrabber(String modelKey, String defaultView)
    {
        this.modelKey = modelKey;
        this.defaultView = defaultView;
    }

    public PipelineTask createTask(PipelineContext context)
    {
        return new PipelineTask(context)
        {
            public Object run()
                throws Exception
            {
                ModelAndView mv = new ModelAndView();
                mv.setModel(getContext().gett(Object.class, modelKey));
                mv.setView(getView());
                return mv;
            }

            private String getView()
            {
                String view = (String) getContext().get("view");
                return view == null ? defaultView : view;
            }
        };
    }
}
