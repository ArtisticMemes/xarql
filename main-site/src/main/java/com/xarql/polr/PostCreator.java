/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */

package com.xarql.polr;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.xarql.util.DatabaseUpdate;
import com.xarql.util.TextFormatter;

public class PostCreator extends DatabaseUpdate
{
    private static final String INSERT_POST = "INSERT INTO polr (title, content, answers, author) VALUES (?, ?, ?, ?)";

    // Attributes --> To be set by end user
    private String title;
    private String content;
    private int    answers;
    private String author;

    private int determinedID;

    // Limits
    public static final int MAX_TITLE_LENGTH   = 64;
    public static final int MAX_CONTENT_LENGTH = 4096;
    public static final int MIN_CONTENT_LENGTH = 1;
    public static final int MIN_ID             = 0;

    // Defaults
    public static final String DEFAULT_AUTHOR  = "Unknown";
    public static final String DEFAULT_WARNING = "None";

    public PostCreator(String title, String content, int answers, String author) throws IllegalArgumentException
    {
        super(INSERT_POST);
        setTitle(title);
        setContent(content);
        setAnswers(answers);
        setAuthor(author);
    }

    private void setTitle(String title) throws IllegalArgumentException
    {
        if(title.length() > MAX_TITLE_LENGTH)
        {
            this.title = "[TITLE TOO LONG]";
            throw new IllegalArgumentException("Title too long.");
        }
        else
            this.title = TextFormatter.full(title);
    }

    private void setContent(String content) throws IllegalArgumentException
    {
        if(content.length() > MAX_CONTENT_LENGTH)
        {
            this.content = "[CONTENT TOO LONG]";
            throw new IllegalArgumentException("Content too long.");
        }
        else if(content.length() < MIN_CONTENT_LENGTH)
        {
            this.content = "[CONTENT TOO SHORT]";
            throw new IllegalArgumentException("Content too short.");
        }
        else
            this.content = TextFormatter.full(content);
    }

    private void setAnswers(int answers) throws IllegalArgumentException
    {
        if(answers < MIN_ID)
        {
            answers = MIN_ID;
            throw new IllegalArgumentException("Replying to ID too low.");
        }
        else
            this.answers = answers;
    }

    private void setAuthor(String author)
    {
        if(author != null && !author.equals(""))
            this.author = author;
        else
            this.author = DEFAULT_AUTHOR;
    }

    public int getAnswers()
    {
        return answers;
    }

    public int getDeterminedID()
    {
        return determinedID;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, title);
        statement.setString(2, content);
        statement.setInt(3, answers);
        statement.setString(4, author);
    }

    @Override
    protected boolean execute()
    {
        Boolean postExists = new PostExistenceChecker(answers).use();
        if(postExists == null || postExists == false)
            return false;

        // These should only return false if the sql connection is faulty, as the
        // conditions in which they fail were tested for
        if(makeRequest() == false)
            return false;

        Boolean statsUpdated = new PostStatUpdater(answers).use();
        if(statsUpdated == null || statsUpdated == false)
            return false;

        PageCache.clear();

        PolrMaxID max = new PolrMaxID();
        if(max.use() != -1)
            determinedID = max.getData();
        else
            return false;

        HashLogger hl = new HashLogger(content, determinedID);
        hl.execute();
        hl = new HashLogger(title, determinedID);
        hl.execute();

        return true; // Will execute if neither of the above 2 return statements have
    }

}
