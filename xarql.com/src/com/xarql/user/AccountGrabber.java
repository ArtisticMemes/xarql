package com.xarql.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xarql.util.DBManager;

public class AccountGrabber
{
    private static final String ACCOUNT_QUERY = "SELECT * FROM user_secure WHERE username=?";

    private int    id;
    private String username;
    private String hash;

    public AccountGrabber(String username)
    {
        this.username = username;
        id = -1;
    } // AccountGrabber()

    protected void processResult(ResultSet rs) throws SQLException
    {
        username = rs.getString("username");
        if(username != null && username.length() > 0)
        {
            id = rs.getInt("id");
            hash = rs.getString("hash");
        }
        else
            id = -1;
    } // processResult()

    protected String getData()
    {
        return username;
    } // getData()

    public int getID()
    {
        return id;
    } // getID()

    public String getUsername()
    {
        return username;
    } // getUsername()

    public String getHash()
    {
        return hash;
    } // getHash()

    public boolean execute()
    {
        return makeRequest();
    } // execute()

    private String getCommand()
    {
        return ACCOUNT_QUERY;
    } // getCommand()

    public boolean makeRequest()
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = getCommand();

        try
        {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(query);
            setVariables(statement);
            rs = statement.executeQuery();
            while(rs.next())
                processResult(rs);
            return true;
        }
        catch(SQLException s)
        {
            return false;
        }
        finally
        {
            // Close in reversed order.
            if(rs != null)
                try
                {
                    rs.close();
                }
                catch(SQLException s)
                {
                }
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
    } // setVariables()

} // AccountGrabber
