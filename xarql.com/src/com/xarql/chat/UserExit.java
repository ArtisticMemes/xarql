package com.xarql.chat;

public class UserExit extends WebsocketPackage
{

    public UserExit(String id) throws IllegalArgumentException
    {
        super(false, "user-exit:" + id);
    } // UserExit()

} // UserExit
