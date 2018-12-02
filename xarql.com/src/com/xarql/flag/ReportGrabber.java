package com.xarql.flag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.xarql.util.DatabaseQuery;

public class ReportGrabber extends DatabaseQuery
{
    private ArrayList<Report> reports = new ArrayList<Report>(25);

    public ReportGrabber(HttpServletResponse response)
    {
        super("SELECT * FROM flag LIMIT 25", response);
    } // ReportGrabber()

    @Override
    protected void processResult(ResultSet rs) throws SQLException
    {
        String type = rs.getString("type");
        String description = rs.getString("content");
        int id = rs.getInt("post_id");
        Report report = new Report(type, description, id);
        reports.add(report);
    } // processResult

    @Override
    protected ArrayList<Report> getData()
    {
        return reports;
    } // getData();

    @Override
    public boolean execute()
    {
        return super.makeRequest();
    } // execute()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        return;
    } // setVariables()

} // ReportGrabber
