/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.xarql.util.DatabaseQuery;

public class PostRetriever extends DatabaseQuery<ArrayList<Post>>
{
    // Request Parameters
    private int    id;
    private String sort;
    private String flow;
    private int    postSkipCount;
    private int    postCount;

    private ArrayList<Post> posts;

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
    }

    public PostRetriever(int id, String sort, String flow)
    {
        this(id, sort, flow, DEFAULT_POST_SKIP_COUNT, DEFAULT_POST_COUNT);
    }

    public PostRetriever(int id)
    {
        this(id, DEFAULT_SORT, DEFAULT_FLOW);
    }

    private void setID(int id)
    {
        if(id < DEFAULT_ID)
            this.id = DEFAULT_ID;
        else
            this.id = id;
    }

    private void setSort(String sort)
    {
        if(sort == null)
            this.sort = DEFAULT_SORT;
        else if(sort.equals("date") || sort.equals("responses") || sort.equals("subresponses") || sort.equals("bump") || sort.equals("subbump"))
            this.sort = sort;
        else
            this.sort = DEFAULT_SORT;
    }

    private void setFlow(String flow)
    {
        if(flow == null)
            this.flow = DEFAULT_FLOW;
        else if(flow.equals("asc") || flow.equals("desc"))
            this.flow = flow;
        else
            this.flow = DEFAULT_FLOW;
    }

    private void setPostSkipCount(int postSkipCount)
    {
        if(postSkipCount >= 0 && postSkipCount < FARTHEST_POST)
            this.postSkipCount = postSkipCount;
        else
            this.postSkipCount = DEFAULT_POST_SKIP_COUNT;
    }

    private void setPostCount(int postCount)
    {
        if(postCount > 0 && postCount < FARTHEST_POST - postSkipCount)
            this.postCount = postCount;
        else
            this.postCount = DEFAULT_POST_COUNT;
    }

    // Return a specific set of posts
    @Override
    public boolean execute()
    {
        int page = postSkipCount / postCount;
        if(PageCache.getPageAsList(id + "|" + sort + "|" + flow + "|" + page) != null)
        {
            posts = PageCache.getPageAsList(id + "|" + sort + "|" + flow + "|" + page);
            return true;
        }
        else
        {
            posts = new ArrayList<>();
            setCommand("SELECT * FROM polr WHERE id=?");
            if(makeRequest())
            {
                setCommand("SELECT * FROM polr WHERE removed=0 AND answers=? ORDER BY " + sort + " " + flow + " LIMIT " + postSkipCount + ", " + postCount);
                if(makeRequest())
                {
                    new PageCache(id, sort, flow, page, posts);
                    return true;
                }
                else
                    return false;
            }
            else
                return false;
        }
    }

    @Override
    protected void processResult(ResultSet rs) throws SQLException
    {
        posts.add(Post.interperetPost(rs));
    }

    @Override
    public ArrayList<Post> getData()
    {
        return posts;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, id);
    }

}
