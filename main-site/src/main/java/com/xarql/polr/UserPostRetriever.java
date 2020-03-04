/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.xarql.util.DatabaseQuery;
import com.xarql.util.TextFormatter;

public class UserPostRetriever extends DatabaseQuery<ArrayList<Post>>
{
    private String          username;
    private ArrayList<Post> posts = new ArrayList<>();

    private static final int    POST_COUNT = PostRetriever.DEFAULT_POST_COUNT;
    private static final String COMMAND    = "SELECT * FROM polr WHERE author=? ORDER BY date DESC LIMIT ?";

    public UserPostRetriever(String username) throws Exception
    {
        super(COMMAND);
        setUsername(username);
    }

    private void setUsername(String username) throws IllegalArgumentException
    {
        if(username == null || username.equals(""))
            throw new IllegalArgumentException("No username given");
        else if(!TextFormatter.isAlphaNumeric(username))
            throw new IllegalArgumentException("Invalid username");
        else
            this.username = username;
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
        statement.setString(1, username);
        statement.setInt(2, POST_COUNT);
    }

}
