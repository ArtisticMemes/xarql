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

public class PostFinder {
	private HttpServletResponse response;
	private String query;
	private String sort;
	private String flow;
	
	// Defaults & Limits
	private static final String DEFAULT_SORT = PostRetriever.DEFAULT_SORT;
	private static final String DEFAULT_FLOW = PostRetriever.DEFAULT_FLOW;
	private static final int POST_COUNT = 10;

	public PostFinder(HttpServletResponse response, String query, String sort, String flow)
	{
		this.response = response;
		setQuery(query);
		setSort(sort);
		setFlow(flow);
	} // PostFinder(response, query, sort, flow)
	
	public PostFinder(HttpServletResponse response, String query) {
		this(response, query, DEFAULT_SORT, DEFAULT_FLOW);
	} // PostFinder(response, query)
	
	private void setQuery(String query)
	{
		if(query.chars().allMatch(Character::isLetter))
			this.query = "%" + query + "%";
		else
			this.query = "";
	} // setQuery()
	
	private void setSort(String sort)
	{
		if(sort == null)
			this.sort = DEFAULT_SORT;
		else
		{
			if(sort.equals("date") || sort.equals("responses") || sort.equals("subresponses") || sort.equals("bump") || sort.equals("subbump"))
				this.sort = sort;
			else
				this.sort = DEFAULT_SORT;
		}
	} // setSort()
	
	private void setFlow(String flow)
	{
		if(flow == null)
			this.flow = DEFAULT_FLOW;
		else
		{
			if(flow.equals("asc") || flow.equals("desc"))
				this.flow = flow;
			else
				this.flow = DEFAULT_FLOW;
		}
	} // setFLow()
	
	public ArrayList<Post> execute()
	{
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    ArrayList<Post> posts = new ArrayList<Post>();
	    String sql = "SELECT * FROM polr WHERE title LIKE ? OR content LIKE ? ORDER BY ? ? LIMIT 0, ?";

	    try 
	    {
	        connection = DBManager.getConnection();
	        statement = connection.prepareStatement(sql);
	        
	        statement.setString(1, query);
	        statement.setString(2, query);
	        statement.setString(3, sort);
	        statement.setString(4, flow);
	        statement.setInt(5, POST_COUNT);
	        
	        rs = statement.executeQuery();
	        while (rs.next())
	        {
	        	int rsId = rs.getInt("id");
	        	String rsTitle = rs.getString("title");
	        	String rsContent = rs.getString("content");
	        	int rsAnswers = rs.getInt("answers");
	        	int rsRemoved = rs.getInt("removed");
	        	Timestamp rsDate = rs.getTimestamp("date");
	        	Timestamp rsBump = rs.getTimestamp("bump");
	        	Timestamp rsSubbump = rs.getTimestamp("subbump");
	        	int rsResponses = rs.getInt("responses");
	        	int rsSubresponses = rs.getInt("subresponses");
	        	Post currentPost = new Post(rsId, rsTitle, rsContent, rsAnswers, rsRemoved, rsDate, rsBump, rsSubbump, rsResponses, rsSubresponses);
	        	posts.add(currentPost);
	        }
	        
	    }
	    catch(SQLException s)
	    {
	    	posts.clear();
	    	try 
	    	{
				response.sendError(500);
				return posts;
			} 
	    	catch (IOException e) 
	    	{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return posts;
	    }
	    finally 
	    {
	        // Close in reversed order.
	        if (rs != null) try { rs.close(); } catch (SQLException s) {}
	        if (statement != null) try { statement.close(); } catch (SQLException s) {}
	        if (connection != null) try { connection.close(); } catch (SQLException s) {}
	    }
	    
		return posts;
	} // execute()
	
} // PostFinder
