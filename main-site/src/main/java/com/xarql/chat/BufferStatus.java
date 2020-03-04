package com.xarql.chat;

public class BufferStatus extends WebsocketPackage
{
    public BufferStatus(boolean value, Client source)
    {
        super(source);
        setHeader(Headers.BUFFER, value);
    }
}
