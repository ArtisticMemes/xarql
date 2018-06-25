package com.xarql.polr;

import javax.servlet.http.HttpServletResponse;

public class PostCreator {
	
	// Attributes --> To be set by end user
	private Post post;
	
	public PostCreator(Post post)
	{
		this.post = post;
	} // PostCreator(String title, String content, int answers)
	
	public void updatePost(Post post)
	{
		this.post = post;
	} // updatePost(Post post)
	
	// Connect to database and create new entry. Throw a generic exception if anything goes wrong
	public void execute(HttpServletResponse response)
	{
		
	} // public void execute(HttpServletResponse response)
}
