/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.auth;

import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.Random;

import com.xarql.user.Account;
import com.xarql.util.Secrets;

public class AuthSession
{
    public static Random r = new Random(); // For generating colors

    private boolean   verified;
    private String    tomcatSession;
    private String    color;
    private Timestamp creationTime;
    private String    googleId;
    private Account   account;

    private static final int MAX_LIFETIME_MINUTES = 60;

    public AuthSession(String tomcatSession, String input, String inputType)
    {
        if(inputType.equals("google"))
            verifyGoogleId(input);
        else if(inputType.equals("recaptcha"))
            verifyRecaptcha(input);
        else
            verified = false;

        setTomcatSession(tomcatSession);
        setCreationTime();
        randomizeColor();
        AuthTable.add(this);
    } // AuthSession()

    protected AuthSession(AuthSession session)
    {
        this.verified = session.verified;
        this.tomcatSession = session.tomcatSession;
        this.creationTime = session.creationTime;
        try
        {
            this.googleId = session.getGoogleId();
        }
        catch(Exception e)
        {
            this.googleId = "";
        }
    } // AuthSession()

    public boolean verified()
    {
        return verified;
    } // verified()

    private void setTomcatSession(String tomcatSession)
    {
        this.tomcatSession = tomcatSession;
    } // setTomcatSession()

    public String getTomcatSession()
    {
        return tomcatSession;
    } // getTomcatSession()

    private void randomizeColor()
    {
        int colorValue = r.nextInt(0xffffff + 1);
        color = String.format("#%06x", colorValue);
    } // randomizeColor()

    public String getColor()
    {
        return color;
    } // getColor()

    private void setCreationTime()
    {
        creationTime = new Timestamp(System.currentTimeMillis());
    } // setCreationTime()

    public boolean expired() // Checks if older than 1 hour
    {
        Timestamp eldestPossible = new Timestamp(System.currentTimeMillis() - (60000 * MAX_LIFETIME_MINUTES));
        if(creationTime.before(eldestPossible))
            return true;
        else
            return false;
    } // expired()

    private void verifyGoogleId(String googleIdToken)
    {
        try
        {
            googleId = VerifyGoogle.verify(googleIdToken);
            verified = true;
        }
        catch(GeneralSecurityException gse)
        {
            verified = false;
        }
    } // verifyGoogleId()

    public String getGoogleId() throws NullPointerException, GeneralSecurityException
    {
        if(verified)
        {
            if(googleId.equals(""))
                throw new NullPointerException("User's Google ID is unknown");
            else
                return googleId;
        }
        else
            throw new GeneralSecurityException("User isn't verified");
    } // getGoogleId()

    private void verifyRecaptcha(String recpatchaResponse)
    {
        if(VerifyRecaptcha.verify(recpatchaResponse))
            verified = true;
        else
            verified = false;
    } // verifyRecaptcha()

    public boolean setAccount(String googleID)
    {
        try
        {
            this.account = new Account(googleID);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    } // attachAccount()

    public Account getAccount()
    {
        return account;
    } // getAccount()

    // Checks if this AuthSession belongs to a moderator
    public boolean isMod()
    {
        if(Secrets.modList().contains(googleId))
            return true;
        else
            return false;
    } // isMod()

} // AuthSession
