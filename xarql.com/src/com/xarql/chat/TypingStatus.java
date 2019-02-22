package com.xarql.chat;

public class TypingStatus extends WebsocketPackage
{
    private static final String CLIENT_NAME = WebsocketPackage.CLIENT_NAME;
    private static final String TYPING      = WebsocketPackage.TYPING;

    public TypingStatus(String typing, Client client) throws IllegalArgumentException
    {
        super(false, "typing");
        setHeader(CLIENT_NAME, client.getColor());
        setHeader(TYPING, typing);
    } // TypingStatus()

} // TypingStatus
