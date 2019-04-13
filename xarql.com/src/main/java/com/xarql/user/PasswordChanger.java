package com.xarql.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.xarql.util.DatabaseUpdate;
import com.xarql.util.TextFormatter;

public class PasswordChanger extends DatabaseUpdate
{
    private static final int MIN_USERNAME_LENGTH = AccountProcessor.MIN_USERNAME_LENGTH;
    private static final int MIN_PASSWORD_LENGTH = AccountProcessor.MIN_PASSWORD_LENGTH;
    private static final int MAX_VARIABLE_LENGTH = AccountProcessor.MAX_VARIABLE_LENGTH;

    private static final String COMMAND = "UPDATE user_secure SET hash=? WHERE username=?";

    private String username;
    private String hash;

    public PasswordChanger(String username, String password) throws Exception
    {
        super(COMMAND);
        setUsername(username);
        setHash(password);
        execute();
    } // PasswordChanger()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, hash);
        statement.setString(2, username);
    } // setVariables()

    private void setUsername(String username) throws IllegalArgumentException
    {
        if(username.length() <= MAX_VARIABLE_LENGTH && username.length() > MIN_USERNAME_LENGTH)
        {
            if(TextFormatter.isAlphaNumeric(username))
                this.username = username;
            else
                throw new IllegalArgumentException("Username contains non-alpha numeric characters");
        }
        else
            throw new IllegalArgumentException("Username is " + username.length() + " characters long. It must be between " + MIN_USERNAME_LENGTH + " and " + MAX_VARIABLE_LENGTH + " long.");
    } // setUsername()

    private void setHash(String password) throws IllegalArgumentException
    {
        if(password.length() <= MAX_VARIABLE_LENGTH && password.length() >= MIN_PASSWORD_LENGTH)
        {
            if(TextFormatter.isStandardSet(password))
                hash = Password.hashPassword(password);
            else
                throw new IllegalArgumentException("Password contains non-standard characters. Please only use characters that can be seen on a QWERTY keyboard");
        }
        else
            throw new IllegalArgumentException("Password is " + password.length() + " characters long. It must be between " + MIN_PASSWORD_LENGTH + " and " + MAX_VARIABLE_LENGTH + " long.");
    } // setHash()

} // PasswordChanger
