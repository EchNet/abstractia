package com.abstractia.display;

import java.util.*;
import com.abstractia.model.*;

public class BreadcrumbsModel 
    extends ComponentModel
{
    private List<DimFilter> dimFilters = new ArrayList<DimFilter>();
    private NavigationState state;

    public List<DimFilter> getDimFilters()
    {
        return dimFilters;
    }

    public void setDimFilters(List<DimFilter> dimFilters)
    {
        this.dimFilters = dimFilters;
    }

    public NavigationState getState()
    {
        return state;
    }

    public void setState(NavigationState state)
    {
        this.state = state;
    }
}
