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

public class FlatPostRetriever
{
    private HttpServletResponse response;
    private String              sort;
    private String              flow;
    private int                 page;

    private static final String DEFAULT_SORT = PostRetriever.DEFAULT_SORT;
    private static final String DEFAULT_FLOW = PostRetriever.DEFAULT_FLOW;
    private static final int    POST_COUNT   = PostRetriever.DEFAULT_POST_COUNT;

    public FlatPostRetriever(HttpServletResponse response, String sort, String flow, int page)
    {
        this.response = response;
        setSort(sort);
        setFlow(flow);
        setPage(page);
    } // FlatPostRetriever()

    private void setSort(String sort)
    {
        if(sort == null)
            this.sort = DEFAULT_SORT;
        else
        {
            if(sort.equals("date") || sort.equals("responses") || sort.equals("subresponses") || sort.equals("bump") || sort.equals("subbump"))
                this.sort = sort;
            else
                this.sort = DEFAULT_SORT;
        }
    } // setSort(String sort)

    private void setFlow(String flow)
    {
        if(flow == null)
            this.flow = DEFAULT_FLOW;
        else
        {
            if(flow.equals("asc") || flow.equals("desc"))
                this.flow = flow;
            else
                this.flow = DEFAULT_FLOW;
        }
    } // setSort(String sort)

    private void setPage(int page)
    {
        if(page >= PathReader.MIN_PAGE && page <= PathReader.MAX_PAGE)
            this.page = page;
        else
            this.page = 0;
    } // setPage()

    public ArrayList<Post> execute()
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ArrayList<Post> posts = new ArrayList<Post>();
        String sql = "SELECT * FROM polr ORDER BY " + sort + " " + flow + " LIMIT ?, ?";

        try
        {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setInt(1, page * POST_COUNT);
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
                Post currentPost = new Post(rsId, rsTitle, rsContent, rsAnswers, rsRemoved, rsDate, rsBump, rsSubbump, rsResponses, rsSubresponses, rsAuthor);
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
