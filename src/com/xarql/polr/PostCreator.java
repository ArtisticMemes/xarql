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
import com.xarql.main.VerifyRecaptcha;

public class PostCreator {
	
	// Attributes --> To be set by end user
	private String title;
	private String content;
	private int answers;
	private boolean goodParameters;
	
	// Limits
	public static final int MAX_TITLE_LENGTH = 128;
	public static final int MAX_CONTENT_LENGTH = 4096;
	public static final int MIN_CONTENT_LENGTH = 1;
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
			this.title = htmlSafe(title);
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
		else if(content.length() < MIN_CONTENT_LENGTH)
		{
			this.content = "[CONTENT TOO SHORT]";
			this.goodParameters = false;
		}
		else
			this.content = htmlSafe(content);
	} // setContent()
	
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
	} // setAnswers()
	
	public int getAnswers()
	{
		return answers;
	} // getAnswers()
	
	private String htmlSafe(String unsafeText)
	{
		String safeText = unsafeText.replace("&", "&#38;").replace("\"", "&#34;").replace("'", "&#39;").replace("<", "&#60;").replace("=", "&#61;").replace(">", "&#62;").replace("?", "&#63;");
		
		// Enable links with ~
		boolean insideTilde = false;
		String safeLinkedText = "";
		String withinTilde = "";
		for(int i = 0; i < safeText.length(); i++)
		{
			if(safeText.charAt(i) == '~' && insideTilde == false && safeText.substring(i + 1, safeText.length()).contains("~"))
			{
				insideTilde = true;
				safeLinkedText += "<a href=\"";
			}
			else if(safeText.charAt(i) == '~' && insideTilde == true)
			{
				insideTilde = false;
				safeLinkedText += withinTilde + "\" target=\"_blank\">" + withinTilde + "</a>";
				withinTilde = "";
			}
			else if(insideTilde)
			{
				withinTilde += safeText.charAt(i);
			}
			else
			{
				safeLinkedText += safeText.charAt(i);
			}
		}
		return safeLinkedText;
	} // htmlSafe()
	
	public boolean execute(HttpServletResponse response, String g_recaptcha_response) // <-- Not up to naming conventions, but looks Google's naming
	{
		//System.out.println("Attempting to create post");
		if(goodParameters && VerifyRecaptcha.verify(g_recaptcha_response) && postExists(answers, response))
		{
			// These should only return false if the sql connection is faulty, as the conditions in which they fail were tested for in the above if statement
			if(createPost("INSERT INTO polr (title, content, answers) VALUES (?, ?, ?)", title, content, answers, response))
				return false;
			if(updateStats(answers, response) == false)
				return false;
			
			return true; // Will execute if neither of the above 2 return statements have
		}
		else
			return false;
	} // execute(HttpServletResponse response)
	
	private boolean updateStats(int startingId, HttpServletResponse response)
	{
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    
	    	//System.out.println("Parameters = good");
		    try 
		    {
		        connection = DBManager.getConnection();

		        
		        updateStatLoop(startingId, true, connection, statement, rs);
		        return true;
		    }
		    catch(SQLException s)
		    {
		    	try 
		    	{
					response.sendError(500);
					return false;
				} 
		    	catch (IOException e) 
		    	{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return false;
		    }
		    finally 
		    {
		        // Close in reversed order.
		        //if (rs != null) try { rs.close(); } catch (SQLException s) {}
		        if (statement != null) try { statement.close(); } catch (SQLException s) {}
		        if (connection != null) try { connection.close(); } catch (SQLException s) {}
		    }
	} // updateStats()
	
	private void updateStatLoop(int answers, boolean firstRun, Connection connection, PreparedStatement statement, ResultSet rs) throws SQLException
	{
		// responses column
		if(firstRun == true)
		{
			statement = connection.prepareStatement("UPDATE polr SET responses=responses+1 WHERE id=?");
			statement.setInt(1, answers);
			statement.executeUpdate();
			statement = connection.prepareStatement("UPDATE polr SET subresponses=subresponses+1 WHERE id=?");
			statement.setInt(1, answers);
			statement.executeUpdate();
		}
		
		System.out.println("id was : " + answers);
		if(answers != 0)
		{
			System.out.println("Entered if statement");
			// Get next id
			statement = connection.prepareStatement("SELECT answers FROM polr WHERE id=?");
			statement.setInt(1, answers);
			rs = statement.executeQuery();
			if(rs.next())
				answers = rs.getInt("answers");
			System.out.println("Looking to update id=" + answers);
			// Increase subresponses
			statement = connection.prepareStatement("UPDATE polr SET subresponses=subresponses+1 WHERE id=?");
			statement.setInt(1, answers);
			statement.executeUpdate();
			updateStatLoop(answers, false, connection, statement, rs);
		}
	}
	
	private boolean postExists(int id, HttpServletResponse response)
	{
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    
	    	//System.out.println("Parameters = good");
		    try 
		    {
		        connection = DBManager.getConnection();
		        statement = connection.prepareStatement("SELECT * FROM polr where id=?");
		        
		        statement.setInt(1, answers);
		        
		        rs = statement.executeQuery();
		        if(rs.next())
		        {
		        	return true;
		        }
		        else
		        {
		        	try {
						response.sendError(400);
						//System.out.println("Prevented post with bad id");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	return false;
		        }
		    }
		    catch(SQLException s)
		    {
		    	try 
		    	{
					response.sendError(500);
					return false;
				} 
		    	catch (IOException e) 
		    	{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return false;
		    }
		    finally 
		    {
		        // Close in reversed order.
		        //if (rs != null) try { rs.close(); } catch (SQLException s) {}
		        if (statement != null) try { statement.close(); } catch (SQLException s) {}
		        if (connection != null) try { connection.close(); } catch (SQLException s) {}
		    }
	} // postExists()
	
	private boolean createPost(String query, String title, String content, int answers, HttpServletResponse response)
	{
		Connection connection = null;
	    PreparedStatement statement = null;
	    int result;
	    
	    	//System.out.println("Parameters = good");
		    try 
		    {
		        connection = DBManager.getConnection();
		        statement = connection.prepareStatement(query);
		        
		        statement.setString(1, title);
		        statement.setString(2, content);
		        statement.setInt(3, answers);
		        
		        result = statement.executeUpdate();
		        return true;
		    }
		    catch(SQLException s)
		    {
		    	try 
		    	{
					response.sendError(500);
					return false;
				} 
		    	catch (IOException e) 
		    	{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	return false;
		    }
		    finally 
		    {
		        // Close in reversed order.
		        //if (rs != null) try { rs.close(); } catch (SQLException s) {}
		        if (statement != null) try { statement.close(); } catch (SQLException s) {}
		        if (connection != null) try { connection.close(); } catch (SQLException s) {}
		    }
	} // ArrayList createPost()
}
