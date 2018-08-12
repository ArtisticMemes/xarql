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
	
	public Timestamp getDate()
	{
		return date;
	} // getDate()
	
	public String getSession()
	{
		return session;
	} // getSession()
	
	public String toString()
	{
		return getId() + " " + getMessage() + " " + getDate().toString() + " " + getSession();
	} // toString()
} // Message
