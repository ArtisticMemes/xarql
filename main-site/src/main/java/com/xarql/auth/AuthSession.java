/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.auth;

import java.sql.Timestamp;
import java.util.Random;
import com.xarql.rsc.Secrets;
import com.xarql.user.Account;

public class AuthSession
{
    public static Random r = new Random(); // For generating colors

    private boolean   verified;
    private String    tomcatSession;
    private String    color;
    private Timestamp updateTime;
    private Account   account;
    private Timestamp lastSubmitTime;
    private boolean   killed;

    private static final int MAX_LIFETIME_MINUTES = 60;

    public AuthSession(String tomcatSession, String input, String inputType)
    {
        if(inputType.equals("recaptcha"))
            verifyRecaptcha(input);
        else
            verified = inputType.equals("account");

        setTomcatSession(tomcatSession);
        setUpdateTime();
        randomizeColor();
        AuthTable.add(this);
        setLastSubmitTime(new Timestamp(System.currentTimeMillis() - 60000));
        setKilled(false);
    }

    public AuthSession(String tomcatSession, Account account)
    {
        this(tomcatSession, "", "account");
        setAccount(account);
    }

    protected AuthSession(AuthSession session)
    {
        verified = session.verified;
        tomcatSession = session.tomcatSession;
        updateTime = session.updateTime;
        setKilled(false);
    }

    public boolean verified()
    {
        return verified;
    }

    private void setTomcatSession(String tomcatSession)
    {
        this.tomcatSession = tomcatSession;
    }

    public String getTomcatSession()
    {
        return tomcatSession;
    }

    private void randomizeColor()
    {
        color = generateColor();
    }

    // Useful for other parts of xarql
    public static String generateColor()
    {
        int colorValue = r.nextInt(0xffffff + 1);
        return String.format("#%06x", colorValue).toLowerCase();
    }

    public String getColor()
    {
        return color;
    }

    private void setUpdateTime()
    {
        updateTime = new Timestamp(System.currentTimeMillis());
    }

    private void setLastSubmitTime(Timestamp lastSubmitTime)
    {
        this.lastSubmitTime = lastSubmitTime;
    }

    private void setKilled(boolean killed)
    {
        this.killed = killed;
    }

    public void kill()
    {
        setKilled(true);
        AuthTable.remove(getTomcatSession());
    }

    public void updateLastSubmitTime()
    {
        setUpdateTime();
        lastSubmitTime = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getLastSubmitTime()
    {
        return lastSubmitTime;
    }

    public boolean expired() // Checks if older than 1 hour
    {
        Timestamp eldestPossible = new Timestamp(System.currentTimeMillis() - 60000 * MAX_LIFETIME_MINUTES);
        return updateTime.before(eldestPossible) || killed;
    }

    private void verifyRecaptcha(String recpatchaResponse)
    {
        verified = VerifyRecaptcha.verify(recpatchaResponse);
    }

    public Account getAccount()
    {
        return account;
    }

    public void setAccount(Account account)
    {
        this.account = account;
    }

    // Checks if this AuthSession belongs to a moderator
    public boolean isMod()
    {
        return account != null && Secrets.modList().contains(account.getUsername());
    }

}
