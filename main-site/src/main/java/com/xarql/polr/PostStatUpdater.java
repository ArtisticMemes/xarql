package com.xarql.polr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.xarql.util.ConnectionManager;
import com.xarql.util.DatabaseUpdate;

public class PostStatUpdater extends DatabaseUpdate
{
    private int answers;

    public PostStatUpdater(int answers)
    {
        this.answers = answers;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {

    }

    @Override
    protected boolean execute()
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try
        {
            connection = ConnectionManager.get();
            updateStatLoop(answers, true, connection, statement, rs);
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
        }
    }

    private void updateStatLoop(int answers, boolean firstRun, Connection connection, PreparedStatement statement, ResultSet rs) throws SQLException
    {
        // On the first post
        if(firstRun == true)
        {
            // Increase responses and subresponses by 1
            statement = connection.prepareStatement("UPDATE polr SET responses=responses+1 WHERE id=?"); // responses
            statement.setInt(1, answers);
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE polr SET subresponses=subresponses+1 WHERE id=?"); // subresponses
            statement.setInt(1, answers);
            statement.executeUpdate();

            // Set bump and subbump to now
            Timestamp now = new Timestamp(System.currentTimeMillis());
            statement = connection.prepareStatement("UPDATE polr SET bump=? WHERE id=?"); // bump
            statement.setTimestamp(1, now);
            statement.setInt(2, answers);
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE polr SET subbump=? WHERE id=?"); // subbump
            statement.setTimestamp(1, now);
            statement.setInt(2, answers);
            statement.executeUpdate();
        }

        // On all subsequent posts
        if(answers != 0)
        {
            // Get next id
            statement = connection.prepareStatement("SELECT answers FROM polr WHERE id=?");
            statement.setInt(1, answers);
            rs = statement.executeQuery();
            if(rs.next())
                answers = rs.getInt("answers");

            // Increase subresponses
            statement = connection.prepareStatement("UPDATE polr SET subresponses=subresponses+1 WHERE id=?");
            statement.setInt(1, answers);
            statement.executeUpdate();

            // Set subbump to now
            Timestamp now = new Timestamp(System.currentTimeMillis());
            statement = connection.prepareStatement("UPDATE polr SET subbump=? WHERE id=?");
            statement.setTimestamp(1, now);
            statement.setInt(2, answers);
            statement.executeUpdate();

            updateStatLoop(answers, false, connection, statement, rs); // Continue to next post
        }
    }

}
