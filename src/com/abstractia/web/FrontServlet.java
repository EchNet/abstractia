//
// FrontServlet.java
//

package com.abstractia.web;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;    
import javax.servlet.http.*;

@SuppressWarnings("serial")
/**
 * The one servlet.
 */
public class FrontServlet extends HttpServlet
{
    private Map<String, Pipeline> pipelines = new HashMap<String, Pipeline>();
    private Pipeline defaultPipeline; 

    /** 
     * One-time initialization.
     */
    public void init()
        throws ServletException
    {
        // Imagine for a second that all of the following were carried out
        // by interpretation of a Spring config XML file.

        Services.init(getServletConfig());

        defaultPipeline = makeBrowsePipeline();
        pipelines.put("browse", defaultPipeline);
        pipelines.put("dump", makeDumpPipeline());
    }

    private Pipeline makeDumpPipeline()
    {
        // Just VISUALIZE that xml.
        Pipeline pipeline = new Pipeline();
        pipeline.addBlock(new MockdexQueryBuilder());
        pipeline.addBlock(new MockdexDataHandler());
        pipeline.addBlock(new ModelAndViewGrabber("navigationResult", "/dump.jsp"));
        return pipeline;
    }

    private Pipeline makeBrowsePipeline()
    {
        Pipeline pipeline = new Pipeline();
        pipeline.addBlock(new MockdexQueryBuilder());
        pipeline.addBlock(new TriggerQueryBuilder());
        pipeline.addBlock(new TriggerDataHandler());
        pipeline.addBlock(new MockdexDataHandler());
        pipeline.addBlock(new PageModelAssembler());
        pipeline.addBlock(new ModelAndViewGrabber("pageModel", "/default.jsp"));
        return pipeline;
    }

    /**
     * POST is equivalent to GET.
     */
    public void doPost(HttpServletRequest req, 
                       HttpServletResponse resp)
        throws IOException, ServletException
    {
        doGet(req, resp);
    }
    
    /**
     * Serve, little servlet.
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException
    {
        try
        {
            // Pick a pipeline and execute it.
            ModelAndView modelAndView = (ModelAndView) getPipeline(req).createTask(new PipelineContext(req, resp)).run();

            // Pass the response model to JSP renderer as a bean.
            req.setAttribute("model", modelAndView.getModel());

            // Dispatch to the renderer.
            getServletContext().getRequestDispatcher(modelAndView.getView()).include(req, resp);
        }
        catch (ServletException e)
        {
            throw e;
        }
        catch (IOException e)
        {
            throw e;
        }
        catch (RuntimeException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new ServletException(e);
        }
    }

    /**
     * Find the right pipeline for the job.
     */
    private Pipeline getPipeline(HttpServletRequest req)
        throws ServletException
    {
        Pipeline pipeline;

        // Last component of servlet path is the pipeline name.
        String servletPath = req.getServletPath();
        int slash = servletPath.lastIndexOf('/');
        if (slash >= 0)
        {
            pipeline = pipelines.get(servletPath.substring(slash + 1));
        }
        else
        {
            pipeline = defaultPipeline;
        }

        if (pipeline == null)
        {
            throw new ServletException("invalid URL");
        }

        return pipeline;
    }
}
