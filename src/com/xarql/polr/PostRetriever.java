package com.xarql.polr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.xarql.main.DBManager;

public class PostRetriever {
	// Request Parameters
	private int id;
	private String sort;
	private String order;
	
	// Defaults and Limits
	private static final int DEFAULT_ID = 0;
	private static final String DEFAULT_SORT = "bump";
	private static final String DEFAULT_ORDER = "desc";
	
	public PostRetriever(int id, String sort, String order)
	{
		setID(id);
		setSort(sort);
		setOrder(order);
	} // PostRetriever(int id, String sort, String order)
	
	public PostRetriever(int id)
	{
		this(id, DEFAULT_SORT, DEFAULT_ORDER);
	} // PostRetriever(int id)
	
	private void setID(int id)
	{
		this.id = id;
	} // setID(int id)
	
	private void setSort(String sort)
	{
		if(sort.equals("date") || sort.equals("responses") || sort.equals("bump"))
			this.sort = sort;
		else
			this.sort = DEFAULT_SORT;
	} // setSort(String sort)
	
	private void setOrder(String order)
	{
		if(order.equals("asc") || order.equals("desc"))
			this.order = order;
		else
			this.order = DEFAULT_ORDER;
	} // setSort(String sort)
	
	// Return a specific set of posts
	public ArrayList execute(HttpServletResponse response)
	{
		// TODO: Write sql querying
		ArrayList posts = new ArrayList();
		posts.add(sqlQuery("SELECT * FROM polr WHERE id=?", id, response));
		posts.add(sqlQuery("SELECT * FROM polr WHERE answers=?", id, response));
		return posts;
	} // ArrayList execute(HttpServletResponse response)
	
	private ArrayList sqlQuery(String query, int id, HttpServletResponse response)
	{
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
		ArrayList posts = new ArrayList();

	    try 
	    {
	        connection = DBManager.getConnection();
	        statement = connection.prepareStatement(query);
	        
	        statement.setInt(1, id);
	        
	        rs = statement.executeQuery();
	        while (rs.next())
	        {
	        	int rsID = rs.getInt("id");
	        	String rsTitle = rs.getString("title");
	        	String rsContent = rs.getString("content");
	        	int rsResponses = rs.getInt("responses");
	        	Date rsBump = rs.getDate("bump");
	        	Post currentPost = new Post(rsID, rsTitle, rsContent, rsResponses, rsBump);
	        	posts.add(currentPost);
	        }
	        
	    }
	    catch(SQLException s)
	    {
	    	posts.clear();
	    	try 
	    	{
				response.sendError(500);
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
	} // ArrayList sqlQuery(String query, int id, HttpServletResponse response)
	
	
	/*
	// Return everything in polr
	private ArrayList getALL()
	{
		return posts;
	} // getAll()
	*/
	
}
