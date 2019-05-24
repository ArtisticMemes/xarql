package com.xarql.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.xarql.util.DatabaseUpdate;

public class PasswordChanger extends DatabaseUpdate
{
    private static final String COMMAND = "UPDATE user_secure SET hash=? WHERE username=?";

    private String username;
    private String hash;

    public PasswordChanger(String username, String password) throws Exception
    {
        super(COMMAND);
        Account.checkNameAndPass(username, password);
        setUsername(username);
        setHash(password);
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, hash);
        statement.setString(2, username);
    }

    private void setUsername(String username) throws IllegalArgumentException
    {
        Account.checkUsername(username);
        this.username = username;
    }

    private void setHash(String password) throws IllegalArgumentException
    {
        Account.checkPassword(password);
        hash = Password.hashPassword(password);
    }

}
