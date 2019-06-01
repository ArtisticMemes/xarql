package com.xarql.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.xarql.util.DatabaseQuery;

public class AccountMaxID extends DatabaseQuery<Integer>
{
    private static final String COMMAND = "SELECT MAX(id) FROM user_secure";

    private static Integer max;

    public AccountMaxID()
    {
        super(COMMAND);
    }

    public static Integer useStatic()
    {
        return new AccountMaxID().use();
    }

    @Override
    protected Integer getData()
    {
        return max;
    }

    @Override
    protected boolean execute()
    {
        if(!makeRequest())
        {
            max = -1;
            return false;
        }
        else
            return true;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        return;
    }

    @Override
    protected void processResult(ResultSet rs) throws SQLException
    {
        max = rs.getInt(1);
    }

}
