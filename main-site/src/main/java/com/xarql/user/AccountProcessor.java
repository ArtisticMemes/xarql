package com.xarql.user;

public class AccountProcessor
{
    public AccountProcessor(String username, String password) throws Exception
    {
        Account.checkNameAndPass(username, password);

        AccountGrabber ag = new AccountGrabber(username);
        if(ag.use() != null)
        {
            if(ag.getID() == -1)
            {
                if(new AccountCreator(username, Password.hashPassword(password)).use())
                    AccountCounter.increaseCount();
            }
            else
                throw new IllegalArgumentException("Username already exists in database");
        }
        else
            throw new IllegalArgumentException("Existing usernames couldn't be checked. Please try again");
    }

}
