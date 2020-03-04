package com.xarql.polr.edit;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse;
import com.xarql.polr.PageCache;
import com.xarql.util.DatabaseUpdate;

public class PostRemover extends DatabaseUpdate
{
    private int id;

    private static final String COMMAND = "UPDATE polr SET removed=1 WHERE id=?";

    public PostRemover(int id, HttpServletResponse response)
    {
        super(COMMAND);
        this.id = id;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, id);
    }

    @Override
    public boolean execute()
    {
        boolean output = makeRequest();
        PageCache.clear();
        return output;
    }

}
