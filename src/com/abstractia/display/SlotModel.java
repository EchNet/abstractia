package com.abstractia.display;

import java.util.*;

public class SlotModel 
    extends ComponentModel
{
    private String name;
    private ComponentModel component;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public ComponentModel getComponent()
    {
        return component;
    }

    public void setComponent(ComponentModel component)
    {
        this.component = component;
    }
}
