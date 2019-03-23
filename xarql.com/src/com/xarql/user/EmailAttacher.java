package com.xarql.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.xarql.util.ConnectionManager;
import com.xarql.util.TextFormatter;

public class EmailAttacher
{
    private static final int MAX_VARIABLE_LENGTH = AccountProcessor.MAX_VARIABLE_LENGTH;
    private static final int MIN_USERNAME_LENGTH = AccountProcessor.MIN_USERNAME_LENGTH;
    private static final int MAX_EMAIL_LENGTH    = 512;

    private static final String COMMAND = "UPDATE user_secure SET email=? WHERE username=?";

    private String username;
    private String email;

    public EmailAttacher(Account account, String email) throws Exception
    {
        setUsername(account.getUsername());
        setEmail(email);
        account.setEmail(email);
        execute();
    } // PasswordChanger()

    private String getCommand()
    {
        return COMMAND;
    } // getCommand()

    private boolean execute() throws Exception
    {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = getCommand();

        try
        {
            connection = ConnectionManager.get();
            statement = connection.prepareStatement(query);
            setVariables(statement);
            statement.executeUpdate();
            return true;
        }
        catch(SQLException s)
        {
            throw s;
        }
        finally
        {
            // Close in reversed order.
            if(statement != null)
            {
                try
                {
                    statement.close();
                }
                catch(SQLException s)
                {
                }
            }
        }
    } // execute()

    private void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(2, username);
        statement.setString(1, email);
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
    } // setEmail()

} // Email
