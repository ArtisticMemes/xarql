package com.xarql.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Queries the database for some data.
 *
 * @author Bryan Johnson
 * @param <ResponseDataType> The Java class that represents the database rows.
 */
public abstract class DatabaseQuery<ResponseDataType>extends DatabaseInteractor<ResponseDataType>
{
    private static final boolean TESTING = DeveloperOptions.getTesting();

    /**
     * Sets command variable to the provided command and the index to 0.
     *
     * @param command A String containing an SQL query
     * @see DatabaseInteractor#DatabaseInteractor(String)
     */
    public DatabaseQuery(String command)
    {
        super(command);
    }

    /**
     * Sets the command variable to null and the index to 0.
     *
     * @see DatabaseInteractor#DatabaseInteractor()
     */
    public DatabaseQuery()
    {
        super();
    }

    /**
     * One row of the result set is to be processed in to an object that is then
     * held by the child implementing this method.
     *
     * @param rs The set of objects from makeRequest()
     * @throws SQLException If the set can't be processed in the specified way
     */
    protected abstract void processResult(ResultSet rs) throws SQLException;

    /**
     * Queries the database for some data and attempts to process it using child
     * implementation of several methods. Stack traces of errors will be printed
     * during testing. 1 - Increments the index. 2 - Grabs the command. 3 - Grabs a
     * connection. 4 - Prepares a statement and sets its variables. 5 - Executes the
     * command 6 - Processes the result.
     *
     * @return false for failure. true for success.
     */
    @Override
    protected final boolean makeRequest()
    {
        nextIndex();
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = getCommand();

        try
        {
            statement = ConnectionManager.get().prepareStatement(query);
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
                try
                {
                    rs.close();
                }
                catch(SQLException s)
                {
                    // do nothing
                }
            if(statement != null)
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

}
