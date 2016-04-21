package com.abstractia.display;

import com.abstractia.model.*;
import java.util.*;

public class LoginModel
    extends ComponentModel
{
    private boolean loggedIn;
    private NavigationState state;

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
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
