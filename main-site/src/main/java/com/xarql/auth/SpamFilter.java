package com.xarql.auth;

import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;

public class SpamFilter
{
    private static final long BASE_COOL_DOWN = 20000;

    public static boolean shouldBlock(HttpServletRequest request)
    {
        AuthSession as = AuthTable.get(request.getRequestedSessionId());
        Timestamp nextPossibleSubmitTime = new Timestamp(as.getLastSubmitTime().getTime() + BASE_COOL_DOWN);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(now.compareTo(nextPossibleSubmitTime) < 0)
            return true;
        else
        {
            as.updateLastSubmitTime();
            return false;
        }
    }

}
