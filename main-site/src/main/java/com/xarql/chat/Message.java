package com.xarql.chat;

import java.sql.Timestamp;

public class Message extends WebsocketPackage
{
    private static final long MESSAGE_LIFESPAN = 600000; // 10 minutes = 600000

    public Message(String content, Client client) throws IllegalArgumentException
    {
        super(content, client);
    }

    public boolean isExpired()
    {
        Timestamp maxAge = new Timestamp(System.currentTimeMillis() - MESSAGE_LIFESPAN);
        return getCreationDate().compareTo(maxAge) < 0;
    }

}
