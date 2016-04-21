package com.abstractia.display;

import java.util.*;

public class ContainerModel 
    extends ComponentModel
{
    private List<ComponentModel> components = new ArrayList<ComponentModel>();

    public List<ComponentModel> getComponents()
    {
        return components;
    }

    public void setComponents(List<ComponentModel> components)
    {
        this.components = components;
    }
}
