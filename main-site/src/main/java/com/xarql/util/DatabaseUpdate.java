package com.xarql.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.xarql.main.DeveloperOptions;

/**
 * Updates the database with some data.
 *
 * @author Bryan Johnson
 */
public abstract class DatabaseUpdate extends DatabaseInteractor<Boolean>
{
    private static final boolean TESTING = DeveloperOptions.getTesting();

    /**
     * Sets the command variable to the provided command and the index to 0.
     *
     * @param command A String containing an SQL query
     * @see DatabaseInteractor#DatabaseInteractor(String)
     */
    public DatabaseUpdate(String command)
    {
        super(command);
    }

    /**
     * Sets the command variable to null and the index to 0.
     *
     * @see DatabaseInteractor#DatabaseInteractor()
     */
    public DatabaseUpdate()
    {
        super();
    }

    /**
     * @see DatabaseInteractor#use()
     * @return true
     */
    @Override
    protected final Boolean getData()
    {
        return true;
    }

    /**
     * Updates the database using the command. Stack traces of errors will be
     * printed during testing. 1 - Increments the index. 2 - Grabs the command. 3 -
     * Grabs a connection. 4 - Executes the command.
     *
     * @return false for failure. true for success.
     */
    @Override
    protected final boolean makeRequest()
    {
        nextIndex();
        PreparedStatement statement = null;
        String query = getCommand();

        try
        {
            statement = ConnectionManager.get().prepareStatement(query);
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
