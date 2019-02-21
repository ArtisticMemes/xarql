package com.xarql.chat;

public class ErrorReport extends WebsocketPackage
{

    public ErrorReport(Throwable e) throws IllegalArgumentException
    {
        super(false, "error:" + e.toString());
    } // ErrorReport()

} // ErrorReport
