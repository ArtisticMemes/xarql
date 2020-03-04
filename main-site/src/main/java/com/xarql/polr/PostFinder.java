/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.xarql.util.DatabaseQuery;

public class PostFinder extends DatabaseQuery<ArrayList<Post>>
{
    private String          query;
    private ArrayList<Post> posts;

    private static final int    POST_COUNT     = PostRetriever.DEFAULT_POST_COUNT;
    private static final String SEARCH_COMMAND = "SELECT *, MATCH (title,content) AGAINST (? IN BOOLEAN MODE) as score FROM polr WHERE removed=0 AND MATCH (title,content) AGAINST (? IN BOOLEAN MODE) > 0 ORDER BY score DESC LIMIT 0, ?";

    public PostFinder(String query)
    {
        super(SEARCH_COMMAND);
        posts = new ArrayList<>(POST_COUNT);
        setQuery(query);
    }

    private void setQuery(String query)
    {
        query += "*";
        query = query.toLowerCase();
        this.query = query;
    }

    @Override
    public boolean execute()
    {
        if(!query.equals("*"))
            return makeRequest();
        else
            return true;
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
        statement.setString(1, query);
        statement.setString(2, query);
        statement.setInt(3, POST_COUNT);
    }

}
