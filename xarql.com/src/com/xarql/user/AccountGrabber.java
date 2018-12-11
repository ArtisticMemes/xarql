package com.xarql.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xarql.util.DBManager;

public class AccountGrabber
{
    private String googleID;
    private String username;
    private String command;

    public AccountGrabber(String googleID)
    {
        this.command = googleID;
    } // AccountGrabber()

    protected void processResult(ResultSet rs) throws SQLException
    {
        username = rs.getString("username");
    } // processResult()

    protected String getData()
    {
        return username;
    } // getData()

    public boolean execute()
    {
        return makeRequest();
    } // execute()

    private String getCommand()
    {
        return command;
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
        statement.setString(1, googleID);
    } // setVariables()

} // AccountGrabber
