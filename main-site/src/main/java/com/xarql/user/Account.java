package com.xarql.user;

import com.xarql.util.TextFormatter;

public class Account
{
    public static final int MIN_USERNAME_LENGTH = 1;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_VARIABLE_LENGTH = 128;

    private int    id;
    private String username;
    private String email;

    public Account(String username, String password) throws Exception
    {
        AccountGrabber ag = new AccountGrabber(username);
        if(ag.use() != null)
        {
            if(ag.getID() == -1)
                throw new Exception("This username is not associated with an account");
            else
                try
                {
                    if(Password.checkPassword(password, ag.getHash()))
                    {
                        setID(ag.getID());
                        setUsername(username);
                        setEmail(ag.getEmail());
                    }
                    else
                        throw new Exception("The given password is incorrect");
                }
                catch(IllegalArgumentException iae)
                {
                    throw new Exception("The stored hash for this account's password is malformed.");
                }

        }
        else
            throw new Exception("Account couldn't be retrieved. Please try again");
    }

    private void setID(int id)
    {
        this.id = id;
    }

    public int getID()
    {
        return id;
    }

    private void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        if(email == null)
            return "No email";
        else
            return email;
    }

    public static boolean checkNameAndPassErrorless(String username, String password)
    {
        try
        {
            return checkNameAndPass(username, password);
        }
        catch(IllegalArgumentException e)
        {
            return false;
        }
    }

    public static boolean checkNameAndPass(String username, String password) throws IllegalArgumentException
    {
        checkUsername(username);
        checkPassword(password);
        if(password.equalsIgnoreCase(username))
            throw new IllegalArgumentException("Password was the same as the username. Please use a password that is NOT your username.");
        return true;
    }

    public static boolean checkUsernameErrorless(String username)
    {
        try
        {
            return checkUsername(username);
        }
        catch(IllegalArgumentException e)
        {
            return false;
        }
    }

    public static boolean checkUsername(String username) throws IllegalArgumentException
    {
        if(username.length() <= MAX_VARIABLE_LENGTH && username.length() > MIN_USERNAME_LENGTH)
        {
            if(TextFormatter.isAlphaNumeric(username))
                return true;
            else
                throw new IllegalArgumentException("Username contains non-alpha numeric characters");
        }
        else
            throw new IllegalArgumentException("Username is " + username.length() + " characters long. It must be between " + MIN_USERNAME_LENGTH + " and " + MAX_VARIABLE_LENGTH + " long.");
    }

    public static boolean checkPasswordErrorless(String password)
    {
        try
        {
            return checkPassword(password);
        }
        catch(IllegalArgumentException e)
        {
            return false;
        }
    }

    public static boolean checkPassword(String password)
    {
        if(password.length() <= MAX_VARIABLE_LENGTH && password.length() >= MIN_PASSWORD_LENGTH)
        {

            if(TextFormatter.isStandardSet(password))
                return true;
            else
                throw new IllegalArgumentException("Password contains non-standard characters. Please only use characters that can be seen on a QWERTY keyboard");

        }
        else
            throw new IllegalArgumentException("Password is " + password.length() + " characters long. It must be between " + MIN_PASSWORD_LENGTH + " and " + MAX_VARIABLE_LENGTH + " long.");
    }

}
