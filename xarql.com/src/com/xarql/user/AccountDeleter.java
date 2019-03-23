package com.xarql.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.xarql.util.ConnectionManager;
import com.xarql.util.TextFormatter;

public class AccountDeleter
{
    private static final int MIN_USERNAME_LENGTH = AccountProcessor.MIN_USERNAME_LENGTH;
    private static final int MAX_VARIABLE_LENGTH = AccountProcessor.MAX_VARIABLE_LENGTH;

    private static final String COMMAND = "DELETE FROM user_secure WHERE username=?";

    private String username;

    public AccountDeleter(String username) throws Exception
    {
        setUsername(username);
        execute();
    } // PasswordChanger()

    private String getCommand()
    {
        return COMMAND;
    } // getCommand()

    private boolean execute() throws Exception
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = getCommand();

        try
        {
            connection = ConnectionManager.get();
            statement = connection.prepareStatement(query);
            setVariables(statement);
            statement.executeUpdate();
            return true;
        }
        catch(SQLException s)
        {
            throw s;
        }
        finally
        {
            // Close in reversed order.
            if(statement != null)
            {
                try
                {
                    statement.close();
                }
                catch(SQLException s)
                {
                }
            }
        }
    } // makeRequest()

    private void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, username);
    } // setVariables()

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
    } // setUsername()

} // AccountDeleter
