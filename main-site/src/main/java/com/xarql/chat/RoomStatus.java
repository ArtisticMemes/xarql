package com.xarql.chat;

public class RoomStatus extends WebsocketPackage
{
    public RoomStatus(String name)
    {
        super("You are now in room '" + name + "'");
    }
}
