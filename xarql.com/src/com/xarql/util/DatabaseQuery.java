package com.xarql.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xarql.main.DeveloperOptions;

public abstract class DatabaseQuery<RequestedDataClass>extends DatabaseInteractor
{
    private static final boolean TESTING = DeveloperOptions.getTesting();

    public DatabaseQuery(String command)
    {
        super(command);
    } // DatabaseQuery()

    public DatabaseQuery()
    {
        super();
    } // DatabaseQuery()

    protected abstract void processResult(ResultSet rs) throws SQLException;

    public abstract RequestedDataClass getData();

    @Override
    protected boolean makeRequest()
    {
        nextIndex();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = getCommand();

        try
        {
            connection = ConnectionManager.get();
            statement = connection.prepareStatement(query);
            setVariables(statement);
            rs = statement.executeQuery();
            while(rs.next())
                processResult(rs);
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
            if(rs != null)
            {
                try
                {
                    rs.close();
                }
                catch(SQLException s)
                {
                    // do nothing
                }
            }
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

} // DatabaseQuery
