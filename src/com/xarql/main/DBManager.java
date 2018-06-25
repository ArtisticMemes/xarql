package com.xarql.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
	
	public static Connection getConnection()
	{
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://sql.xarql.com/main", Secrets.DBUser, Secrets.DBPass);
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
