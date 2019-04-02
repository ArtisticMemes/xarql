package com.xarql.flag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xarql.util.DatabaseQuery;

public class ReportGrabber extends DatabaseQuery<ArrayList<Report>>
{
    private ArrayList<Report> reports = new ArrayList<Report>(25);

    private static final String REPORT_GRAB = "SELECT * FROM flag LIMIT 25 ORDER BY date DESC";

    public ReportGrabber()
    {
        super(REPORT_GRAB);
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
    public ArrayList<Report> getData()
    {
        return reports;
    } // getData();

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        return;
    } // setVariables()

} // ReportGrabber
