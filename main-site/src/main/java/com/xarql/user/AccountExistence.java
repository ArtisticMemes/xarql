package com.xarql.user;

public class AccountExistence
{
    public static boolean check(String user)
    {
        AccountGrabber ag = new AccountGrabber(user);
        return ag.use() != null && ag.getID() != -1;
    }

}
