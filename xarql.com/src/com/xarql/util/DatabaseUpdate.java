package com.xarql.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.xarql.main.DeveloperOptions;

public abstract class DatabaseUpdate extends DatabaseInteractor
{
    private static final boolean TESTING = DeveloperOptions.getTesting();

    public DatabaseUpdate(String command)
    {
        super(command);
    } // DatabaseUpdate()

    public DatabaseUpdate()
    {
        super();
    } // DatabaseUpdate()

    @Override
    protected boolean makeRequest()
    {
        nextIndex();
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
            if(TESTING)
                System.out.println(s);
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
                    // do nothing
                }
            }
        }
    } // makeRequest()

} // DatabaseUpdate
