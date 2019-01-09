package com.xarql.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.xarql.util.DBManager;

public class AccountCreator
{
    private static final String COMMAND = "INSERT INTO user_secure (username, hash) VALUES (?, ?)";

    private String username;
    private String password;

    public AccountCreator(String username, String password)
    {
        this.username = username;
        this.password = password;
    } // AccountCreator()

    private String getCommand()
    {
        return COMMAND;
    } // getCommand()

    public boolean execute() throws Exception
    {
        return makeRequest();
    } // execute()

    private boolean makeRequest() throws Exception
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = getCommand();

        try
        {
            connection = DBManager.getConnection();
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
                try
                {
                    statement.close();
                }
                catch(SQLException s)
                {
                }
            if(connection != null)
                try
                {
                    connection.close();
                }
                catch(SQLException s)
                {
                }
        }
    } // makeRequest()

    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, username);
        statement.setString(2, password);
    } // setVariables()

} // AccountCreator
