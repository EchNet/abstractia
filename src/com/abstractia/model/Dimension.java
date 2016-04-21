package com.abstractia.model;

import java.util.*;

public class Dimension
{
    private String name;
    private List<Dval> children = new ArrayList<Dval>();

    public Dimension(String name)
    {
        this.name = name;
    }

    public void add(Dval dval)
    {
        children.add(dval);
    }

    public String getName()
    {
        return name;
    }

    public int cardinality()
    {
        return children.size();
    }

    public List<Dval> getDvals()
    {
        return children;
    }

    public Dval getDval(int index)
    {
        return children.get(index);
    }

    public boolean equals(Object obj)
    {
        return (obj instanceof Dimension) && ((Dimension) obj).getName().equals(name);
    }

    public int hashCode()
    {
        return name.hashCode();
    }
}
