package com.xarql.chat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import com.xarql.main.DBManager;
import com.xarql.polr.Post;

public class MessageRetriever {
	public ArrayList<Message> execute(HttpServletResponse response)
	{
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    ArrayList<Message> messages = new ArrayList<Message>();
	    String query = "SELECT * FROM chat WHERE date>? AND date<?";
	    Timestamp yesterday = new Timestamp(System.currentTimeMillis() - 86400000);
	    Timestamp now = new Timestamp(System.currentTimeMillis());

	    try 
	    {
	        connection = DBManager.getConnection();
	        statement = connection.prepareStatement(query);
	        
	        statement.setTimestamp(1, yesterday);
	        statement.setTimestamp(2, now);
	        
	        rs = statement.executeQuery();
	        while (rs.next())
	        {
	        	int rsId = rs.getInt("id");
	        	String rsMessage = rs.getString("message");
	        	Timestamp rsDate = rs.getTimestamp("date");
	        	String rsSession = rs.getString("session");
	        	Message currentMessage = new Message(rsId, rsMessage, rsDate, rsSession);
	        	messages.add(currentMessage);
	        }
	        return messages;
	    }
	    catch(SQLException s)
	    {
	    	messages.clear();
	    	try 
	    	{
				response.sendError(500);
				return messages;
			} 
	    	catch (IOException e) 
	    	{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return messages;
	    }
	    finally 
	    {
	        // Close in reversed order.
	        if (rs != null) try { rs.close(); } catch (SQLException s) {}
	        if (statement != null) try { statement.close(); } catch (SQLException s) {}
	        if (connection != null) try { connection.close(); } catch (SQLException s) {}
	    }
	} // execute()
} // MessageRetriever
