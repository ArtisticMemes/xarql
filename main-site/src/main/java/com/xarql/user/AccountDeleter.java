package com.xarql.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.xarql.util.DatabaseUpdate;

public class AccountDeleter extends DatabaseUpdate
{
    private static final String COMMAND = "DELETE FROM user_secure WHERE username=?";

    private String username;

    public AccountDeleter(String username) throws Exception
    {
        super(COMMAND);
        setUsername(username);
    }

    @Override
    protected boolean execute()
    {
        if(makeRequest())
        {
            AccountCounter.decreaseCount();
            return true;
        }
        else
            return false;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, username);
    }

    private void setUsername(String username) throws IllegalArgumentException
    {
        Account.checkUsername(username);
        this.username = username;
    }

}
