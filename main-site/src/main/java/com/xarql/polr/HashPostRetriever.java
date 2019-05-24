/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */

package com.xarql.polr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.xarql.util.DatabaseQuery;

public class HashPostRetriever extends DatabaseQuery<ArrayList<Post>>
{
    private static final int    DEFAULT_POST_COUNT = PostRetriever.DEFAULT_POST_COUNT;
    private static final String HASHTAG_RETRIEVAL  = "SELECT polr.* FROM polr INNER JOIN polr_tags_relations ON polr.id=polr_tags_relations.post_id WHERE polr_tags_relations.content=? ORDER BY date desc LIMIT ?";

    private String          hash;
    private ArrayList<Post> posts;

    public HashPostRetriever(String hash)
    {
        super(HASHTAG_RETRIEVAL);
        this.hash = hash.toLowerCase();
        posts = new ArrayList<>(PostRetriever.DEFAULT_POST_COUNT);
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
        statement.setString(1, hash);
        statement.setInt(2, DEFAULT_POST_COUNT);
    }

}
