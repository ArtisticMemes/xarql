package com.xarql.flag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xarql.util.DatabaseQuery;

public class ReportExistenceChecker extends DatabaseQuery<Boolean>
{
    private boolean exists;
    private int     postID;

    private static final String COMMAND = "SELECT * FROM flag WHERE post_id=?";

    public ReportExistenceChecker(int postID)
    {
        super(COMMAND);
        exists = false;
        this.postID = postID;
    } // ReportExistenceChecker()

    @Override
    protected void processResult(ResultSet rs) throws SQLException
    {
        exists = true;
    } // processResult()

    @Override
    public Boolean getData()
    {
        return exists;
    } // getData()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, postID);
    } // setVariables()

} // ReportExistenceChecker
