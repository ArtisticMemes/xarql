package com.xarql.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.xarql.util.DatabaseUpdate;

public class AccountCreator extends DatabaseUpdate
{
    private String googleID;
    private String username;

    public AccountCreator(HttpServletResponse response, String googleID, String username)
    {
        super(response);
        this.googleID = googleID;
        this.username = username;
    } // AccountCreator()

    @Override
    public boolean execute()
    {
        return super.makeRequest();
    } // execute()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, googleID);
        statement.setString(2, username);
    } // setVariables()

} // AccountCreator
