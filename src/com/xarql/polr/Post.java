package com.xarql.polr;

import java.sql.Date;

public class Post {
	private int id;
	private String title = null;
	private String content = null;
	private int responses;
	private int answers;
	private Date bump;
	
	public Post(int id, String title, String content, int responses, Date bump)
	{
		this.id = id;
		this.title = title;
		this.content = content;
		this.responses = responses;
		this.bump = bump;
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public int getResponses()
	{
		return responses;
	}
}
