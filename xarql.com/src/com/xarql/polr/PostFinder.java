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

public class PostFinder
{
    private HttpServletResponse response;
    private String              query;

    // Defaults & Limits
    private static final int POST_COUNT = PostRetriever.DEFAULT_POST_COUNT;

    public PostFinder(HttpServletResponse response, String query)
    {
        this.response = response;
        setQuery(query);
    } // PostFinder(response, query)

    private void setQuery(String query)
    {
        this.query = query + "*";
    } // setQuery()

    public ArrayList<Post> execute()
    {
        if(query.equals("*"))
            return new ArrayList<Post>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ArrayList<Post> posts = new ArrayList<Post>();
        String sql = "SELECT *, MATCH (title,content) AGAINST (? IN BOOLEAN MODE) as score FROM polr WHERE removed=0 AND MATCH (title,content) AGAINST (? IN BOOLEAN MODE) > 0 ORDER BY score DESC LIMIT 0, ?";

        try
        {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, query);
            statement.setString(2, query);
            statement.setInt(3, POST_COUNT);

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
                Post currentPost = new Post(rsId, rsTitle, rsContent, rsAnswers, rsRemoved, rsDate, rsBump, rsSubbump, rsResponses, rsSubresponses);
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

} // PostFinder
