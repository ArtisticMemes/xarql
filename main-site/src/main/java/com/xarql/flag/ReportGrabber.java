package com.xarql.flag;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.xarql.util.DatabaseQuery;

public class ReportGrabber extends DatabaseQuery<ArrayList<Report>>
{
    private ArrayList<Report> reports = new ArrayList<>(25);

    private static final String REPORT_GRAB = "SELECT * FROM flag ORDER BY date DESC LIMIT 25";

    public ReportGrabber()
    {
        super(REPORT_GRAB);
    }

    @Override
    protected void processResult(ResultSet rs) throws SQLException
    {
        String type = rs.getString("type");
        String description = rs.getString("content");
        int id = rs.getInt("post_id");
        Report report = new Report(type, description, id);
        reports.add(report);
    }

    @Override
    public ArrayList<Report> getData()
    {
        return reports;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        return;
    }

}
