package com.xarql.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.xarql.util.DatabaseUpdate;
import com.xarql.util.TextFormatter;

public class AccountDeleter extends DatabaseUpdate
{
    private static final int MIN_USERNAME_LENGTH = AccountProcessor.MIN_USERNAME_LENGTH;
    private static final int MAX_VARIABLE_LENGTH = AccountProcessor.MAX_VARIABLE_LENGTH;

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
        if(username.length() <= MAX_VARIABLE_LENGTH && username.length() > MIN_USERNAME_LENGTH)
        {
            if(TextFormatter.isAlphaNumeric(username))
                this.username = username;
            else
                throw new IllegalArgumentException("Username contains non-alpha numeric characters");
        }
        else
            throw new IllegalArgumentException("Username is " + username.length() + " characters long. It must be between " + MIN_USERNAME_LENGTH + " and " + MAX_VARIABLE_LENGTH + " long.");
    }

}
