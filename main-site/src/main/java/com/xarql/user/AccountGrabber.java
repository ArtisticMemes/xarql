package com.xarql.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.xarql.util.DatabaseQuery;

public class AccountGrabber extends DatabaseQuery<String>
{
    private static final String ACCOUNT_QUERY    = "SELECT * FROM user_secure WHERE username=?";
    private static final String ACCOUNT_ID_QUERY = "SELECT * FROM user_secure WHERE id=?";

    private int    id;
    private String username;
    private String hash;
    private String email;

    public AccountGrabber(String username)
    {
        super(ACCOUNT_QUERY);
        this.username = username;
        id = -1;
    }

    public AccountGrabber(Integer id)
    {
        super(ACCOUNT_ID_QUERY);
        this.id = id;
        nextIndex();
    }

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
    }

    @Override
    public String getData()
    {
        return username;
    }

    public int getID()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public String getHash()
    {
        return hash;
    }

    public String getEmail()
    {
        return email;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        if(getCommand().equals(ACCOUNT_QUERY))
            statement.setString(1, username);
        else
            statement.setInt(1, id);
    }

}
