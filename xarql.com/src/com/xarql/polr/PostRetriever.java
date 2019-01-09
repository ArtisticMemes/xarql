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

public class PostRetriever
{
    // Request Parameters
    private int    id;
    private String sort;
    private String flow;
    private int    postSkipCount;
    private int    postCount;

    // Defaults and Limits
    private static final int   DEFAULT_ID              = 0;
    public static final String DEFAULT_SORT            = "subbump";
    public static final String DEFAULT_FLOW            = "desc";
    private static final int   DEFAULT_POST_SKIP_COUNT = 0;
    public static final int    DEFAULT_POST_COUNT      = 20;

    private static final int FARTHEST_POST = 100;

    public PostRetriever(int id, String sort, String flow, int postSkipCount, int postCount)
    {
        setID(id);
        setSort(sort);
        setFlow(flow);
        setPostSkipCount(postSkipCount);
        setPostCount(postCount);
    } // PostRetriever(int id, String sort, String flow, int postSkipCount, int
      // postCount)

    public PostRetriever(int id, String sort, String flow)
    {
        this(id, sort, flow, DEFAULT_POST_SKIP_COUNT, DEFAULT_POST_COUNT);
    } // PostRetriever(int id, String sort, String flow)

    public PostRetriever(int id)
    {
        this(id, DEFAULT_SORT, DEFAULT_FLOW);
    } // PostRetriever(int id)

    private void setID(int id)
    {
        if(id < DEFAULT_ID)
            this.id = DEFAULT_ID;
        else
            this.id = id;
    } // setID(int id)

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

    private void setPostSkipCount(int postSkipCount)
    {
        if(postSkipCount >= 0 && postSkipCount < FARTHEST_POST)
            this.postSkipCount = postSkipCount;
        else
            this.postSkipCount = DEFAULT_POST_SKIP_COUNT;
    } // setPostSkipCount(int postSkipCount)

    private void setPostCount(int postCount)
    {
        if(postCount > 0 && postCount < (FARTHEST_POST - postSkipCount))
            this.postCount = postCount;
        else
            this.postCount = DEFAULT_POST_COUNT;
    } // setPostCount(int postCount)

    // Return a specific set of posts
    public ArrayList<Post> execute(HttpServletResponse response)
    {
        int page = postSkipCount / postCount;
        if(PageCache.getPageAsList(id + "|" + sort + "|" + flow + "|" + page) != null)
            return PageCache.getPageAsList(id + "|" + sort + "|" + flow + "|" + page);
        else
        {
            ArrayList<Post> posts = new ArrayList<Post>();
            posts.addAll(sqlQuery("SELECT * FROM polr WHERE id=?", id, response));
            posts.addAll(sqlQuery("SELECT * FROM polr WHERE removed=0 AND answers=? ORDER BY " + sort + " " + flow + " LIMIT " + postSkipCount + ", " + postCount, id, response));
            PageCache pc = new PageCache(id, sort, flow, page, posts);
            return posts;
        }
    } // ArrayList execute(HttpServletResponse response)

    private ArrayList<Post> sqlQuery(String query, int id, HttpServletResponse response)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        ArrayList<Post> posts = new ArrayList<Post>();

        try
        {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(query);

            statement.setInt(1, id);

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
    } // ArrayList sqlQuery(String query, int id, HttpServletResponse response)

} // PostRetriever
