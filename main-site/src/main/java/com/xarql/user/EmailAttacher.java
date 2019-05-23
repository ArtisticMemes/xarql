package com.xarql.user;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.xarql.util.DatabaseUpdate;

public class EmailAttacher extends DatabaseUpdate
{
    private static final int MAX_EMAIL_LENGTH = 512;

    private static final String COMMAND = "UPDATE user_secure SET email=? WHERE username=?";

    private String username;
    private String email;

    public EmailAttacher(Account account, String email) throws Exception
    {
        super(COMMAND);
        setUsername(account.getUsername());
        setEmail(email);
        account.setEmail(email);
        execute();
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(2, username);
        statement.setString(1, email);
    }

    private void setUsername(String username) throws IllegalArgumentException
    {
        Account.checkUsername(username);
        this.username = username;
    }

    private void setEmail(String email) throws IllegalArgumentException
    {
        if(email.length() <= MAX_EMAIL_LENGTH)
        {
            if(email.contains("@") && email.lastIndexOf('@') == email.indexOf('@') && email.contains(".") && email.lastIndexOf('.') > email.indexOf('@'))
                this.email = email;
            else
                throw new IllegalArgumentException("That doesn't look like an email address");
        }
        else
            throw new IllegalArgumentException("Email is " + email.length() + " characters long. The maximum is " + MAX_EMAIL_LENGTH + " characters.");
    }

}
