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
	
	public AuthSession(String tomcatSession, String googleIdToken)
	{
		setGoogleId(googleIdToken);
		setTomcatSession(tomcatSession);
		setCreationTime();
		AuthTable.add(this);
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
	
	private void setGoogleId(String googleIdToken)
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
	} // setGoogleIdByToken()
	
	public String getGoogleId()
	{
		return googleId;
	} // getGoogleId()
	
} // AuthSession
