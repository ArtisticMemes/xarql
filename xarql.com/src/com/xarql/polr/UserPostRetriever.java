/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.xarql.util.DBManager;
import com.xarql.util.TextFormatter;

public class UserPostRetriever
{
    private HttpServletResponse response;
    private String              username;

    private static final int POST_COUNT = PostRetriever.DEFAULT_POST_COUNT;

    public UserPostRetriever(HttpServletResponse response, String username) throws Exception
    {
        this.response = response;
        setUsername(username);
    } // FlatPostRetriever()

    private void setUsername(String username) throws IllegalArgumentException
    {
        if(username == null || username.equals(""))
            throw new IllegalArgumentException("No username given");
        else if(!TextFormatter.isAlphaNumeric(username))
            throw new IllegalArgumentException("Invalid username");
        else
            this.username = username;
    } // setUsername()

    public ArrayList<Post> execute()
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ArrayList<Post> posts = new ArrayList<Post>();
        String sql = "SELECT * FROM polr WHERE author=? ORDER BY date DESC LIMIT ?";

        try
        {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setInt(2, POST_COUNT);

            rs = statement.executeQuery();
            while(rs.next())
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
                String rsAuthor = rs.getString("author");
                String rsWarning = rs.getString("warning");
                Post currentPost = new Post(rsId, rsTitle, rsContent, rsAnswers, rsRemoved, rsDate, rsBump, rsSubbump, rsResponses, rsSubresponses, rsAuthor, rsWarning);
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
            catch(IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return posts;
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

        return posts;
    } // execute()

} // FlatPostRetriever
