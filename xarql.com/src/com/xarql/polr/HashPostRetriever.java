/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */

package com.xarql.polr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.xarql.util.DatabaseQuery;

public class HashPostRetriever extends DatabaseQuery
{
    private String           hash;
    private ArrayList<Post>  posts;
    private static final int DEFAULT_POST_COUNT = PostRetriever.DEFAULT_POST_COUNT;

    public HashPostRetriever(HttpServletResponse response, String hash)
    {
        super("SELECT polr.* FROM polr INNER JOIN polr_tags_relations ON polr.id=polr_tags_relations.post_id WHERE polr_tags_relations.content=? ORDER BY date desc LIMIT ?", response);
        this.hash = hash.toLowerCase();
        posts = new ArrayList<Post>(PostRetriever.DEFAULT_POST_COUNT);
    } // HashPostRetriever

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
        Post currentPost = new Post(rsId, rsTitle, rsContent, rsAnswers, rsRemoved, rsDate, rsBump, rsSubbump, rsResponses, rsSubresponses, rsAuthor);
        posts.add(currentPost);
    } // processResult()

    @Override
    protected ArrayList<Post> getData()
    {
        return posts;
    } // getData()

    @Override
    public boolean execute()
    {
        return super.makeRequest();
    } // execute()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, hash);
        statement.setInt(2, DEFAULT_POST_COUNT);
    } // setVariables()

} // HashPostRetriever
