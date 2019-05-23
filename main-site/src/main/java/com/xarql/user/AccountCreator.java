package com.xarql.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.xarql.util.DatabaseUpdate;

public class AccountCreator extends DatabaseUpdate
{
    private static final String COMMAND = "INSERT INTO user_secure (username, hash) VALUES (?, ?)";

    private final String username;
    private final String password;

    public AccountCreator(String username, String password)
    {
        super(COMMAND);
        this.username = username;
        this.password = password;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, username);
        statement.setString(2, password);
    }

}
