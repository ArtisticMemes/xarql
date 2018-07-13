package com.xarql.polr;

import java.sql.Timestamp;

public class Post {
	// Attributes
	private int id;
	private String title;
	private String content;
	private int answers;
	private boolean removed;
	// Attributes For Sorting
	private Timestamp date;
	private Timestamp bump;
	private Timestamp subbump;
	private int responses;
	private int subresponses;
	
	// Limits
	public static final int MIN_ID = 0;
	public static final int MIN_RESPONSES = 0;
	public static final int MIN_SUBRESPONSES = 0;
	
	// Constants
	public static final String DELETION_MESSAGE = "[POST REMOVED]";
	
	// Constructor
	public Post(int id, String title, String content, int answers, int removed, Timestamp date,  Timestamp bump, Timestamp subbump, int responses, int subresponses)
	{
		setId(id);
		setTitle(title);
		setContent(content);
		setAnswers(answers);
		setRemoved(removed);
		setDate(date);
		setBump(bump);
		setSubbump(subbump);
		setResponses(responses);
		setSubresponses(subresponses);
	} // Post(all)
	
	// Mutators (All private)
	
	private void setId(int id)
	{
		if(id >= MIN_ID)
			this.id = id;
		else
			this.id = MIN_ID;
	} // setId(int id)
	
	private void setTitle(String title)
	{
		if(title == null)
			this.title = "";
		else
			this.title = title;
	} // setTitle(String title)
	
	private void setContent(String content)
	{
		this.content = content;
	} // setContent(String content)
	
	private void setAnswers(int answers)
	{
		if(answers >= MIN_ID)
			this.answers = answers;
		else
			this.answers = MIN_ID;
	} // setAnswers(int answers)
	
	private void setRemoved(int removed)
	{
		if(removed == 0)
			this.removed = false;
		else
		{
			this.removed = true;
			setTitle(DELETION_MESSAGE);
			setContent(DELETION_MESSAGE);
		}
	} // setRemoved(int removed)
	
	private void setDate(Timestamp date)
	{
		this.date = date;
	} // setDate(Timestamp date)
	
	private void setBump(Timestamp bump)
	{
		this.bump = bump;
	} // setBump(Timestamp bump)
	
	private void setSubbump(Timestamp subbump)
	{
		this.subbump = subbump;
	} // setSubbump(Timestamp subbump)
	
	private void setResponses(int responses)
	{
		if(responses >= MIN_RESPONSES)
			this.responses = responses;
		else
			this.responses = MIN_RESPONSES;
	} // setResponses(int responses)
	
	private void setSubresponses(int subresponses)
	{
		if(subresponses >= MIN_SUBRESPONSES)
			this.subresponses = subresponses;
		else
			this.subresponses = MIN_SUBRESPONSES;
	} // setSubresponses(int subresponses)
	
	
	// Selectors (All public)
	
	public int getId()
	{
		return id;
	} // getId()
	
	public String getTitle()
	{
		return title;
	} // getTitle()
	
	public String getContent()
	{
		return content;
	} // getContent()
	
	public int getAnswers()
	{
		return answers;
	} // getAnswers()
	
	public boolean getRemoved()
	{
		return removed;
	} // getRemoved()
	
	public Timestamp getDate()
	{
		return date;
	} // getDate()
	
	public Timestamp getBump()
	{
		return bump;
	} // getBump()
	
	public Timestamp getSubbump()
	{
		return subbump;
	} // getSubbump()
	
	public int getResponses()
	{
		return responses;
	} // getResponses()
	
	public int getSubresponses()
	{
		return subresponses;
	} // getSubresponses()
	
} // Post
