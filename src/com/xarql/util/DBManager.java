/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	
	public static Connection getConnection()
	{
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/main", Secrets.DBUser, Secrets.DBPass);
			return conn;
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			System.out.println("ClassNotFoundException");
			return null;
		}
		catch (SQLException s)
		{
			s.printStackTrace();
			System.out.println("SQLException");
			return null;
		}
		
	} // public Connection getConnection()
} // DBManager
