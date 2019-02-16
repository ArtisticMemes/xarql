package com.xarql.chat;

import javax.websocket.Session;

import com.xarql.util.TextFormatter;

public class Message extends WebsocketPackage
{
    private static final String CLIENT_NAME = WebsocketPackage.CLIENT_NAME;

    public Message(String content, Session client) throws IllegalArgumentException
    {
        super(true, TextFormatter.full(content));
        setHeader(CLIENT_NAME, client.getId());
    } // Message()

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub

    } // main()

} // Message
