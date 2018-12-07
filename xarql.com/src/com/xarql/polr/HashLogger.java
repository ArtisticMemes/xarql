package com.xarql.polr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xarql.util.DBManager;
import com.xarql.util.TextFormatter;

public class HashLogger
{
    private String            postContent;
    private int               postID;
    private PreparedStatement statement;
    private Connection        connection;
    private ResultSet         rs;
    // grab hashtags from content

    // loop while there are more tags
    // check if tag is in polr_tags
    // if yes --> update count
    // if no --> insert new entry
    // add entry to polr_tags_relations

    public HashLogger(String postContent, int postID)
    {
        this.postContent = postContent;
        this.postID = postID;
    } // HashLogger()

    public boolean execute()
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        try
        {
            this.connection = connection;
            this.statement = statement;
            this.rs = rs;

            ArrayList<String> tags = TextFormatter.getHashtags(postContent);
            for(int i = 0; i < tags.size(); i++)
            {
                int tagID = 0;
                if(tagExists(tags.get(i)))
                {
                    tagID = getIDFor(tags.get(i));
                    updateCount(tags.get(i));
                }
                else
                    makeNewTag(tags.get(i));

                addToRelations(tags.get(i), tagID);
            }

        }
        catch(SQLException sql)
        {
            System.err.println(sql);
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

        return true;

    } // execute()

    public int getIDFor(String tag) throws SQLException
    {
        connection = DBManager.getConnection();
        statement = connection.prepareStatement("SELECT id FROM polr_tags WHERE content=?");

        statement.setString(1, tag);

        rs = statement.executeQuery();
        while(rs.next())
            return rs.getInt("id");

        throw new SQLException("Couldn't get entry");
    } // getIDFor()

    public void addToRelations(String tag, int tagID) throws SQLException
    {
        connection = DBManager.getConnection();
        statement = connection.prepareStatement("INSERT INTO polr_tags_relations (tag, post_id, content) VALUES (?, ?, ?)");

        statement.setInt(1, tagID);
        statement.setInt(2, postID);
        statement.setString(3, tag);

        statement.executeUpdate();
    } // addToRelations()

    public void makeNewTag(String tag) throws SQLException
    {
        connection = DBManager.getConnection();
        statement = connection.prepareStatement("INSERT INTO polr_tags (content, count) VALUES (?, 0)");

        statement.setString(1, tag);
        statement.executeUpdate();

    } // makeNewTag()

    public void updateCount(String tag) throws SQLException
    {

        connection = DBManager.getConnection();
        statement = connection.prepareStatement("UPDATE polr_tags SET count=count+1 WHERE content=?");

        statement.setString(1, tag);

        statement.executeUpdate();

    } // updateCount()

    public boolean tagExists(String tag) throws SQLException
    {

        connection = DBManager.getConnection();
        statement = connection.prepareStatement("SELECT id FROM polr_tags WHERE content=?");

        statement.setString(1, tag);

        rs = statement.executeQuery();
        if(rs.next())
            return true;
        else
            return false;

    } // tagExists()

} // HashLogger
