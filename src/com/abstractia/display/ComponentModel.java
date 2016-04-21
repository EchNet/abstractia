package com.abstractia.display;

import java.util.*;

public class ComponentModel
{
    private String variant;

    /**
     * An optional hint to the renderer.
     */
    public String getVariant()
    {
        return variant;
    }

    public void setVariant(String variant)
    {
        this.variant = variant;
    }
}
