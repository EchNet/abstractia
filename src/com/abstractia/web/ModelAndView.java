//
// ModelAndView.java
//

package com.abstractia.web;

/**
 * A stand-in for the Spring MVC class of the same name.
 */
public class ModelAndView
{
    private Object model;
    private String view;

    public Object getModel()
    {
        return model;
    }

    public void setModel(Object model)
    {
        this.model = model;
    }

    public String getView()
    {
        return view;
    }

    public void setView(String view)
    {
        this.view = view;
    }
}
