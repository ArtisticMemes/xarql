/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xarql.util.DatabaseQuery;

public class FlatPostRetriever extends DatabaseQuery<ArrayList<Post>>
{
    private String sort;
    private String flow;
    private int    page;

    private ArrayList<Post> posts;

    private static final String DEFAULT_SORT = PostRetriever.DEFAULT_SORT;
    private static final String DEFAULT_FLOW = PostRetriever.DEFAULT_FLOW;
    private static final int    POST_COUNT   = PostRetriever.DEFAULT_POST_COUNT;

    public FlatPostRetriever(String sort, String flow, int page)
    {
        super();
        posts = new ArrayList<Post>();
        setSort(sort);
        setFlow(flow);
        setPage(page);
        // Prepared statements don't support using ? for sorting types
        setCommand("SELECT * FROM polr ORDER BY " + sort + " " + flow + " LIMIT ?, ?");
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
    } // setSort()

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
    } // setFlow()

    private void setPage(int page)
    {
        if(page >= PathReader.MIN_PAGE && page <= PathReader.MAX_PAGE)
            this.page = page;
        else
            this.page = 0;
    } // setPage()

    @Override
    protected void processResult(ResultSet rs) throws SQLException
    {
        posts.add(Post.interperetPost(rs));
    } // processResult()

    @Override
    public ArrayList<Post> getData()
    {
        return posts;
    } // getData()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setInt(1, page * POST_COUNT);
        statement.setInt(2, POST_COUNT);
    } // setVariables()

} // FlatPostRetriever
