/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.xarql.util.ConnectionManager;
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
            connection = ConnectionManager.get();
            statement = connection.prepareStatement(sql);

            statement.setString(1, username);
            statement.setInt(2, POST_COUNT);

            rs = statement.executeQuery();
            while(rs.next())
                posts.add(Post.interperetPost(rs));

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
        }

        return posts;
    } // execute()

} // FlatPostRetriever
