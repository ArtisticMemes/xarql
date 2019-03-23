package com.xarql.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DatabaseQuery<RequestedDataClass>extends DatabaseInteractor
{
    public DatabaseQuery(String command)
    {
        super(command);
    } // DatabaseQuery()

    public DatabaseQuery()
    {
        super();
    } // DatabaseQuery()

    protected abstract void processResult(ResultSet rs) throws SQLException;

    protected abstract RequestedDataClass getData();

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
