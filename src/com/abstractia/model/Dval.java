package com.abstractia.model;

public class Dval
{
    private String name;
    private String value;

    public Dval(String name)
    {
        this.name = name;
        this.value = name;
    }

    public Dval(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof Dval)
        {
            Dval that = (Dval) obj;
            return this.name.equals(that.name) && this.value.equals(that.value);
        }
        return false;
    }

    public String toString()
    {
        return value;
    }
}
