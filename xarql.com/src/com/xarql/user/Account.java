package com.xarql.user;

public class Account
{
    private String username;

    public Account(String googleID) throws Exception
    {
        AccountGrabber ag = new AccountGrabber(googleID);
        if(ag.execute())
            username = ag.getData();
        else
            throw new Exception("Couldn't grab account");
    } // Account()

    public String getUsername()
    {
        return username;
    } // getUsername()

} // Account
