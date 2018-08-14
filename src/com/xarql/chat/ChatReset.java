package com.xarql.chat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.xarql.main.DBManager;

public class ChatReset {
	
	public static boolean execute(HttpServletResponse response)
	{
		Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    String[] query = {
	    		"CREATE TABLE chat(id int PRIMARY KEY AUTO_INCREMENT, message text(256) NOT NULL, date TIMESTAMP, session text(32) NOT NULL)",
	    		"DELETE FROM chat",
	    		"ALTER TABLE chat AUTO_INCREMENT=0"
	    		};

	    try 
	    {
	        connection = DBManager.getConnection();
	        
	        // Create chat table if it doesn't exist
	        DatabaseMetaData dbmd = connection.getMetaData();
	        rs = dbmd.getTables(null, null, "chat", null);
	        if(rs.next())
	        {
	        	// Delete existing chats and reset the auto increment
		        statement = connection.prepareStatement(query[1]);
		        statement.execute();
		        statement = connection.prepareStatement(query[2]);
		        statement.execute();
	        }
	        else
	        {
	        	statement = connection.prepareStatement(query[0]);
	        	statement.execute();
	        }
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
	        if (rs != null) try { rs.close(); } catch (SQLException s) {}
	        if (statement != null) try { statement.close(); } catch (SQLException s) {}
	        if (connection != null) try { connection.close(); } catch (SQLException s) {}
	    }
	} // execute()
}
