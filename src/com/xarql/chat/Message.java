/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.chat;

import java.sql.Timestamp;

public class Message {
	// Attributes
	private int id;
	private String message;
	private Timestamp date;
	private String session;
	
	public Message(int id, String message, Timestamp date, String session)
	{
		setId(id);
		setMessage(message);
		setDate(date);
		setSession(session);
	} // Message()
	
	private void setId(int id)
	{
		this.id = id;
	} // setId()
	
	private void setMessage(String message)
	{
		this.message = message;
	} // setMessage()
	
	private void setDate(Timestamp date)
	{
		this.date = date;
	} // setDate()
	
	private void setSession(String session)
	{
		this.session = session;
	} // setSession()
	
	public int getId()
	{
		return id;
	} // getId()
	
	public String getMessage()
	{
		return message;
	} // getMessage()
	
	public String textColor()
	{
		int r = Integer.parseInt(backgroundColor().substring(0, 2), 16);
		int g = Integer.parseInt(backgroundColor().substring(2, 4), 16);
		int b = Integer.parseInt(backgroundColor().substring(4, 6), 16);
		double luma = 0.2126 * r + 0.7152 * g + 0.0722 * b; // Adjust for human eyes
		//System.out.println(luma);
		if(luma > 80)
			return "000";
		else
			return "FFF";
	} // textColor()
	
	public Timestamp getDate()
	{
		return date;
	} // getDate()
	
	public String timeSince()
	{
		long timeSince = System.currentTimeMillis() - date.getTime();
		if(timeSince < 60000) // Less than 1 minute
			return timeSince / 1000 + "s";
		else if(timeSince < 3600000) // Less than 1 hour
			return timeSince / 60000 + "m";
		else if(timeSince < 86400000) // Less than 1 day
			return timeSince / 3600000 + "h";
		else
			return timeSince / 86400000 + "d";
	} // timeSince()
	
	public String getSession()
	{
		return session;
	} // getSession()
	
	public String backgroundColor()
	{
		return session.substring(0, 6).toLowerCase();
	} // backgroundColor()
	
	public String toString()
	{
		return getId() + " " + getMessage() + " " + getDate().toString() + " " + getSession();
	} // toString()
} // Message
