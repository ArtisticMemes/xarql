/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.auth;

import com.xarql.util.TrackedHashMap;

public class AuthTable {
	private static TrackedHashMap<String, AuthSession> sessions = new TrackedHashMap<String, AuthSession>();
	private static int requestCount = 0;
	
	public AuthTable()
	{
		sessions.clear();
		requestCount = 0;
	} // AuthTable()
	
	public static void add(AuthSession session)
	{
		if(session.verified() && !session.expired())
		{
			if(sessions.contains(session.getTomcatSession()))
				sessions.remove(session.getTomcatSession());
			sessions.add(session.getTomcatSession(), session);
		}
		requestCount++;
		if(requestCount >= 8) // Trim after every 8 additions
		{
			trim();
			requestCount = 0;
		}
	} // add()
	
	public static boolean contains(String tomcatSession)
	{
		if(sessions.contains(tomcatSession) && !sessions.get(tomcatSession).expired())
			return true;
		else
		{
			sessions.remove(tomcatSession);
			return false;
		}
	} // contains()
	
	public static void trim()
	{
		for(int i = 0; i < sessions.size(); i++)
		{
			if(sessions.get(sessions.key(i)).expired())
			{
				sessions.remove(sessions.key(i));
				i--;
			}
		}
	} // trim()
	
	public static AuthSession get(String session)
	{
		return sessions.get(session);
	} // get()
} // AuthTable
