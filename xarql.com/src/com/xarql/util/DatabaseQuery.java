package com.xarql.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

public abstract class DatabaseQuery<RequestedDataClass>extends DatabaseInteractor
{
    public DatabaseQuery(String command, HttpServletResponse response)
    {
        super(command, response);
    } // DatabaseQuery()

    protected abstract void processResult(ResultSet rs);

    protected abstract RequestedDataClass getData();

    @Override
    protected boolean makeRequest()
    {
        commandIndex++;
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
            try
            {
                response.sendError(500);
                return false;
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
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

} // DatabaseQuery
