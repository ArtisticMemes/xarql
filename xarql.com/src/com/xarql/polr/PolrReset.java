/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.polr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.xarql.util.DBManager;

public class PolrReset
{
    public static boolean execute(HttpServletResponse response)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String[] query =
        { "CREATE TABLE polr(id int PRIMARY KEY AUTO_INCREMENT, title text, content text NOT NULL, answers int NOT NULL DEFAULT 0, removed tinyint UNSIGNED, date timestamp NOT NULL DEFAULT NOW(), bump timestamp NOT NULL DEFAULT NOW(), subbump timestamp NOT NULL DEFAULT NOW(), responses int NOT NULL DEFAULT 0, subresponses int NOT NULL DEFAULT 0, FULLTEXT(title, content)) CHARACTER SET utf8 COLLATE utf8_general_ci",
                "DELETE FROM polr", "ALTER TABLE polr AUTO_INCREMENT=0",
                "INSERT INTO polr (title, content, answers) VALUES ('ROOT POST', 'ROOT POST', -1)",
                "UPDATE polr SET id=0 WHERE answers=-1" };

        try
        {
            connection = DBManager.getConnection();

            // Create chat table if it doesn't exist
            DatabaseMetaData dbmd = connection.getMetaData();
            rs = dbmd.getTables(null, null, "polr", null);
            if(rs.next())
            {
                // System.out.println("Clearing posts from polr");
                // Delete existing chats and reset the auto increment
                statement = connection.prepareStatement(query[1]);
                statement.execute();
                statement = connection.prepareStatement(query[2]);
                statement.execute();
            }
            else
            {
                // System.out.println("Creating table for polr");
                statement = connection.prepareStatement(query[0]);
                statement.execute();
            }
            statement = connection.prepareStatement(query[3]);
            statement.execute();
            statement = connection.prepareStatement(query[4]);
            statement.execute();
            return true;
        }
        catch(SQLException s)
        {
            try
            {
                response.sendError(500);
                return false;
            }
            catch(IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }
        finally
        {
            // Close in reversed order.
            if(rs != null)
                try
                {
                    rs.close();
                }
                catch(SQLException s)
                {
                }
            if(statement != null)
                try
                {
                    statement.close();
                }
                catch(SQLException s)
                {
                }
            if(connection != null)
                try
                {
                    connection.close();
                }
                catch(SQLException s)
                {
                }
        }
    } // execute()
} // PolrReset
