/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.chat;

import java.util.Random;

import com.xarql.auth.AuthSession;

public class ChatSession extends AuthSession
{
	public static Random r = new Random();
	private String color; // For displaying as their identity
	
	public ChatSession(String tomcatSession, String input, String inputType)
	{
		super(tomcatSession, input, inputType);
		randomizeColor();
	} // ChatSession(String tomcatSession, String input, String inputType)
	
	public ChatSession(AuthSession session)
	{
		super(session);
		randomizeColor();
	} // ChatSession(AuthSession session)
	
	private void randomizeColor()
	{
		int colorValue = r.nextInt(0xffffff + 1);
		color = String.format("#%06x", colorValue);
	} // randomizeColor()
	
	public String getColor()
	{
		return color;
	} // getColor()
} // ChatSession
