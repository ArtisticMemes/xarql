package com.xarql.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

public abstract class DatabaseUpdate extends DatabaseInteractor
{
    public DatabaseUpdate(String command, HttpServletResponse response)
    {
        super(command, response);
    } // DatabaseUpdate()

    public DatabaseUpdate(HttpServletResponse response)
    {
        super(response);
    } // DatabaseUpdate()

    @Override
    protected boolean makeRequest()
    {
        commandIndex++;
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

} // DatabaseUpdate
