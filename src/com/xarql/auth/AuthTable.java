/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.auth;

import com.xarql.util.TrackedHashMap;

public class AuthTable {
	private static TrackedHashMap<String, AuthSession> sessions = new TrackedHashMap<String, AuthSession>();
	
	public AuthTable()
	{
		sessions.clear();
	} // AuthTable()
	
	public static void add(AuthSession session)
	{
		if(session.verified() && !session.expired())
			sessions.add(session.getTomcatSession(), session);
		trim();
	} // add()
	
	public static boolean contains(String tomcatSession)
	{
		if(sessions.contains(tomcatSession))
			return true;
		else
			return false;
	} // contains()
	
	public static void trim()
	{
		for(int i = 0; i < sessions.size(); i++)
		{
			if(sessions.get(sessions.key(i)).expired())
				sessions.remove(sessions.key(i));
		}
	} // trim()
} // AuthTable
