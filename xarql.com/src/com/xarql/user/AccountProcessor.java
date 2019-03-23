package com.xarql.user;

import com.xarql.util.TextFormatter;

public class AccountProcessor
{
    public static final int MIN_USERNAME_LENGTH = 1;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_VARIABLE_LENGTH = 128;

    // Only used to validate variables
    private String username;
    private String password;

    public AccountProcessor(String username, String password) throws Exception
    {
        setUsername(username);
        setPassword(password);

        AccountGrabber ag = new AccountGrabber(username);
        if(ag.execute())
        {
            if(ag.getID() == -1)
            {
                AccountCreator ac = new AccountCreator(username, Password.hashPassword(password));
                if(ac.execute())
                    AccountCounter.increaseCount();
            }
            else
                throw new IllegalArgumentException("Username already exists in database");
        }
        else
            throw new IllegalArgumentException("Existing usernames couldn't be checked. Please try again");
    } // AccountProcessor()

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

    private void setPassword(String password) throws IllegalArgumentException
    {
        if(password.length() <= MAX_VARIABLE_LENGTH && password.length() >= MIN_PASSWORD_LENGTH)
        {
            if(!password.equals(username))
            {
                if(TextFormatter.isStandardSet(password))
                    this.password = password;
                else
                    throw new IllegalArgumentException("Password contains non-standard characters. Please only use characters that can be seen on a QWERTY keyboard");
            }
            else
                throw new IllegalArgumentException("Password was the same as the username. Please use a password that is NOT your username.");
        }
        else
            throw new IllegalArgumentException("Password is " + password.length() + " characters long. It must be between " + MIN_PASSWORD_LENGTH + " and " + MAX_VARIABLE_LENGTH + " long.");
    } // setPassword()

} // AccountProcessor
