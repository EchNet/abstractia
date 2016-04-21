package com.abstractia.display;

import java.util.*;

public class PageModel 
    extends ComponentModel
{
    private ComponentModel head;
    private ComponentModel body;

    public ComponentModel getHead()
    {
        return head;
    }

    public void setHead(ComponentModel head)
    {
        this.head = head;
    }

    public ComponentModel getBody()
    {
        return body;
    }

    public void setBody(ComponentModel body)
    {
        this.body = body;
    }
}
