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
import com.xarql.main.TextFormatter;

public class MessageCreator {
	private boolean goodParameters;
	private String message;
	private String session;
	
	public MessageCreator(String message, String session)
	{
		goodParameters = true;
		setMessage(message);
		setSession(session);
	} // MessageCreator()
	
	private void setMessage(String message)
	{
		if(message.length() > 0)
			this.message = TextFormatter.full(message);
		else
			goodParameters = false;
	} // setMessage()
	
	private void setSession(String session)
	{
		if(session.length() == 32)
			this.session = session;
		else
			goodParameters = false;
	} // setSession()
	
	public boolean execute(HttpServletResponse response)
	{
		if(goodParameters)
		{
			Connection connection = null;
		    PreparedStatement statement = null;
		    String query = "INSERT INTO chat (message, session) VALUES (?, ?)";

		    try 
		    {
		        connection = DBManager.getConnection();
		        //System.out.println("Connection gotten");
		        statement = connection.prepareStatement(query);
		        statement.setString(1, message);
		        statement.setString(2, session);
		        statement.executeUpdate();
		        //System.out.println("Update Executed");
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
		}
		else
		{
			try 
			{
				response.sendError(400);
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
	} // execute()
}
