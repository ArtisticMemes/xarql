package com.xarql.chat;

public class TypingStatus extends WebsocketPackage
{
    public static final String TYPING = "typing";

    public TypingStatus(boolean value, Client client) throws IllegalArgumentException
    {
        super(client);
        setHeader(TYPING, value);
    } // TypingStatus()

} // TypingStatus
