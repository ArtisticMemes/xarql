package com.xarql.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xarql.util.DatabaseQuery;

public class AccountGrabber extends DatabaseQuery<String>
{
    private static final String ACCOUNT_QUERY = "SELECT * FROM user_secure WHERE username=?";

    private int    id;
    private String username;
    private String hash;
    private String email;

    public AccountGrabber(String username)
    {
        super(ACCOUNT_QUERY);
        this.username = username;
        id = -1;
    } // AccountGrabber()

    @Override
    protected void processResult(ResultSet rs) throws SQLException
    {
        username = rs.getString("username");
        if(username != null && username.length() > 0)
        {
            id = rs.getInt("id");
            hash = rs.getString("hash");
            email = rs.getString("email");
        }
        else
            id = -1;
    } // processResult()

    @Override
    protected String getData()
    {
        return username;
    } // getData()

    public int getID()
    {
        return id;
    } // getID()

    public String getUsername()
    {
        return username;
    } // getUsername()

    public String getHash()
    {
        return hash;
    } // getHash()

    public String getEmail()
    {
        return email;
    } // getEmail()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, username);
    } // setVariables()

} // AccountGrabber
