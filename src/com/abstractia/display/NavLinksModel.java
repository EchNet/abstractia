package com.abstractia.display;

import java.util.*;
import com.abstractia.model.*;

public class NavLinksModel 
    extends ComponentModel
{
    private Map<Dimension, List<DimRefinement>> dimRefinements;
    private NavigationState state;

    public Map<Dimension, List<DimRefinement>> getDimRefinements()
    {
        return dimRefinements;
    }

    public void setDimRefinements(Map<Dimension, List<DimRefinement>> dimRefinements)
    {
        this.dimRefinements = dimRefinements;
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
