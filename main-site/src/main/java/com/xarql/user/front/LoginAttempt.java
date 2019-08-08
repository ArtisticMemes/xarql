package com.xarql.user.front;

public final class LoginAttempt
{
    public final String  username;
    public final boolean success;
    public final String  comment;

    public LoginAttempt(String username, boolean success, String comment)
    {
        this.username = username;
        this.success = success;
        this.comment = comment;
    }

    @Override
    public String toString()
    {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<attempt><username>" + username + "</username>\n" + "<success>" + success + "</success>\n" + "<comment>" + comment + "</comment>\n</attempt>\n";
    }

    public String getUsername()
    {
        return username;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public String getComment()
    {
        return comment;
    }

}
