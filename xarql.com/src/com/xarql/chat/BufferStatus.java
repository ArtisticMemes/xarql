package com.xarql.chat;

public class BufferStatus extends WebsocketPackage
{
    public static final String BUFFER = "buffer";

    public BufferStatus(boolean value, Client source)
    {
        super(source);
        setHeader(BUFFER, value);
    } // BufferStatus()

} // BufferStatus
