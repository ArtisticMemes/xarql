package com.xarql.chat;

public class UserJoin extends WebsocketPackage
{

    public UserJoin(String id) throws IllegalArgumentException
    {
        super(false, "user-join:" + id);
    } // UserJoin

} // UserJoin
