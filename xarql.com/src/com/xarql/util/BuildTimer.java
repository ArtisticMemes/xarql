package com.xarql.util;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

public class BuildTimer
{
    private final Timestamp startTime;

    public BuildTimer(HttpServletRequest request)
    {
        request.setAttribute("build_timer", this);
        startTime = new Timestamp(System.currentTimeMillis());
    } // BuildTimer()

    public long done()
    {
        long buildTime = System.currentTimeMillis() - startTime.getTime();
        if(buildTime == 0)
            return 1;
        else
            return buildTime;
    } // done()

} // BuildTimer
