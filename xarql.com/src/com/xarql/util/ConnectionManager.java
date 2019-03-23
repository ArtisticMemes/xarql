/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager
{
    private static Connection connection = null;

    public static Connection get()
    {
        if(connection == null)
        {
            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/main", Secrets.DBUser, Secrets.DBPass);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
        return connection;
    } // getConnection()

    public static void close()
    {
        if(connection != null)
        {
            try
            {
                connection.close();
            }
            catch(SQLException s)
            {
            }
        }
    } // close()

} // DBManager
