package com.xarql.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xarql.util.DatabaseQuery;

public class AccountCounter extends DatabaseQuery<Integer>
{
    private static int count = 0;

    public AccountCounter()
    {
        super("SELECT COUNT(id) FROM user_secure");
        if(count == 0)
            execute();
    }

    @Override
    protected void processResult(ResultSet rs) throws SQLException
    {
        count = rs.getInt("COUNT(id)");
    } // processResult()

    @Override
    public Integer getData()
    {
        return count;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        return;
    }

    public static void increaseCount()
    {
        count++;
    }

    public static void decreaseCount()
    {
        count--;
    }

}
