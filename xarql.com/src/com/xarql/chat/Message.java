package com.xarql.chat;

import java.sql.Timestamp;

public class Message extends WebsocketPackage
{
    private static final long MESSAGE_LIFESPAN = 3600000;

    public Message(String content, Client client) throws IllegalArgumentException
    {
        super(content, client);
    } // Message()

    public boolean isExpired()
    {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(getCreationDate().compareTo(now) + MESSAGE_LIFESPAN < 0)
            return true;
        else
            return false;
    } // isExpired()

} // Message
