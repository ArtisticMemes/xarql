package com.xarql.polr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.xarql.util.DatabaseQuery;

public class PostExistenceChecker extends DatabaseQuery<Boolean>
{
    private static final String COMMAND         = "SELECT * FROM polr WHERE id=? AND removed=0";
    private static final long   ONE_WEEK_MILLIS = 604800000;

    private int     targetID;
    private boolean exists;

    public PostExistenceChecker(int targetID)
    {
        super(COMMAND);
        this.targetID = targetID;
    }

    @Override
    protected void processResult(ResultSet rs) throws SQLException
    {
        Timestamp lastWeek = new Timestamp(System.currentTimeMillis() - ONE_WEEK_MILLIS);
        exists = lastWeek.compareTo(rs.getTimestamp("subbump")) < 0 || targetID == 0;
    }

    @Override
    protected Boolean getData()
    {
        return exists;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, targetID);
    }

}
