package com.xarql.chat;

public class TypingStatus extends WebsocketPackage
{
    public TypingStatus(boolean value, Client client) throws IllegalArgumentException
    {
        super(client);
        setHeader(Headers.TYPING, value);
    } // TypingStatus()

} // TypingStatus
