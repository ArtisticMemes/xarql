package com.xarql.user;

public class Account
{
    private int    id;
    private String username;
    private String email;

    public Account(String username, String password) throws Exception
    {
        AccountGrabber ag = new AccountGrabber(username);
        if(ag.use() != null)
        {
            if(ag.getID() == -1)
            {
                throw new Exception("This username is not associated with an account");
            }
            else
            {
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

}
