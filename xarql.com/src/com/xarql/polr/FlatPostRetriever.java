/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.xarql.util.DatabaseQuery;

public class FlatPostRetriever extends DatabaseQuery<ArrayList<Post>>
{
    private String sort;
    private String flow;
    private int    page;

    private ArrayList<Post> posts;

    private static final String DEFAULT_SORT        = PostRetriever.DEFAULT_SORT;
    private static final String DEFAULT_FLOW        = PostRetriever.DEFAULT_FLOW;
    private static final int    POST_COUNT          = PostRetriever.DEFAULT_POST_COUNT;
    private static final String FLAT_POST_RETRIEVAL = "SELECT * FROM polr ORDER BY ? ? LIMIT ?, ?";

    public FlatPostRetriever(String sort, String flow, int page)
    {
        super(FLAT_POST_RETRIEVAL);
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

    @Override
    protected void processResult(ResultSet rs) throws SQLException
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
    } // processResult()

    @Override
    protected ArrayList<Post> getData()
    {
        return posts;
    } // getData()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, sort);
        statement.setString(2, flow);
        statement.setInt(3, page * POST_COUNT);
        statement.setInt(4, POST_COUNT);
    } // setVariables()

} // FlatPostRetriever
