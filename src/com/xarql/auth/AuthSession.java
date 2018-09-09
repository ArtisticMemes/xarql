/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.auth;

import java.security.GeneralSecurityException;
import java.sql.Timestamp;

public class AuthSession {
	private boolean verified;
	private String tomcatSession;
	private Timestamp creationTime;
	private String googleId;
	
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
		AuthTable.add(this);
	} // AuthSession
	
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
	}
	
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
	
} // AuthSession
