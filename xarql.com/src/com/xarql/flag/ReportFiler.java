package com.xarql.flag;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.xarql.util.DatabaseUpdate;

public class ReportFiler extends DatabaseUpdate
{
    private Report report;

    public ReportFiler(HttpServletResponse response, Report report)
    {
        super("INSERT INTO flag (post_id, type, content) VALUES (?, ?, ?)", response);
        this.report = report;
    } //

    @Override
    public boolean execute()
    {
        return super.makeRequest();
    } // execute()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, report.getPostID());
        statement.setString(2, report.getType());
        statement.setString(3, report.getDescription());
    } // setVariables()

} // ReportFiler
