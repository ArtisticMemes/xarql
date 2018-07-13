package com.xarql.polr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.xarql.main.DBManager;

public class PostCreator {
	
	// Attributes --> To be set by end user
	private String title;
	private String content;
	private int answers;
	private boolean goodParameters;
	
	// Limits
	public static final int MAX_TITLE_LENGTH = 128;
	public static final int MAX_CONTENT_LENGTH = 4096;
	public static final int MIN_ID = 0;
	
	public PostCreator(String title, String content, int answers)
	{
		goodParameters = true;
		setTitle(title);
		setContent(content);
		setAnswers(answers);
	} // PostCreator(String title, String content, int answers)
	
	private void setTitle(String title)
	{
		// TODO: Create word filter
		// title = wordFilter(title);
		if(title.length() > MAX_TITLE_LENGTH)
		{
			this.title = "[TITLE TOO LONG]";
			this.goodParameters = false;
		}
		else
			this.title = title;
	} // setTitle(String title)
	
	private void setContent(String content)
	{
		// TODO: Create word filter
		// content = wordFilter(content);
		if(content.length() > MAX_CONTENT_LENGTH)
		{
			this.content = "[CONTENT TOO LONG]";
			this.goodParameters = false;
		}
		else
			this.content = content;
	}
	
	private void setAnswers(int answers)
	{
		// TODO: Add existence check
		if(answers < MIN_ID)
		{
			answers = MIN_ID;
			this.goodParameters = false;
		}
		else
			this.answers = answers;
	}
	
	public void execute(HttpServletResponse response)
	{
		sqlQuery("INSERT INTO polr (title, content, answers) values (\"?\", \"?\", ?)", title, content, answers, response);
	} // execute(HttpServletResponse response)
	
	private void sqlQuery(String query, String title, String content, int answers, HttpServletResponse response)
	{
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    
	    if(goodParameters)
	    {
		    try 
		    {
		        connection = DBManager.getConnection();
		        statement = connection.prepareStatement(query);
		        
		        statement.setString(1, title);
		        statement.setString(2, content);
		        statement.setInt(3, answers);
		        
		        rs = statement.executeQuery();
		    }
		    catch(SQLException s)
		    {
		    	try 
		    	{
					response.sendError(500);
					return;
				} 
		    	catch (IOException e) 
		    	{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return;
		    }
		    finally 
		    {
		        // Close in reversed order.
		        if (rs != null) try { rs.close(); } catch (SQLException s) {}
		        if (statement != null) try { statement.close(); } catch (SQLException s) {}
		        if (connection != null) try { connection.close(); } catch (SQLException s) {}
		    }
	    }
	    else
	    {
	    	try {
				response.sendError(400);
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
		return;
	} // ArrayList sqlQuery(String query, int id, HttpServletResponse response)
}
