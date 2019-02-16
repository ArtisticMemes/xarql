package com.xarql.chat;

import com.xarql.util.TextFormatter;

public class Message extends WebsocketPackage
{
    private static final String CLIENT_NAME = WebsocketPackage.CLIENT_NAME;

    public Message(String content, Client client) throws IllegalArgumentException
    {
        super(true, TextFormatter.full(content));
        setHeader(CLIENT_NAME, client.getColor());
    } // Message()

} // Message
