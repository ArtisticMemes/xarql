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
import javax.servlet.http.HttpServletResponse;
import com.xarql.util.ConnectionManager;
import com.xarql.util.DatabaseUpdate;
import com.xarql.util.TextFormatter;

public class PostCreator extends DatabaseUpdate
{

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
    } // setTitle(String title)

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
    } // setContent()

    private void setAnswers(int answers) throws IllegalArgumentException
    {
        if(answers < MIN_ID)
        {
            answers = MIN_ID;
            throw new IllegalArgumentException("Replying to ID too low.");
        }
        else
            this.answers = answers;
    } // setAnswers()

    private void setAuthor(String author)
    {
        if(author != null && !author.equals(""))
            this.author = author;
        else
            this.author = DEFAULT_AUTHOR;
    } // setAuthor()

    public int getAnswers()
    {
        return answers;
    } // getAnswers()

    public int getDeterminedID()
    {
        return determinedID;
    }

    public boolean usethis()
    {
        if(new PostExistenceChecker(answers).use())
        {
            // These should only return false if the sql connection is faulty, as the
            // conditions in which they fail were tested for in the above if statement
            if(createPost("INSERT INTO polr (title, content, answers, author) VALUES (?, ?, ?, ?)", title, content, answers, author, response) == false)
                return false;
            // System.out.println("updating stats next");
            new PostStatUpdater(answers).use();
            if(updateStats(answers) == false)
                return false;

            PageCache.clear();

            determinedID = PolrMaxID.useStatic();
            if(determinedID == 0)
                return false;

            HashLogger hl = new HashLogger(content, determinedID);
            hl.execute();
            hl = new HashLogger(title, determinedID);
            hl.execute();

            return true; // Will execute if neither of the above 2 return statements have
        }
        else
            return false;
    }

    private boolean updateStats(int startingId, HttpServletResponse response)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        // System.out.println("Parameters = good");
        try
        {
            connection = ConnectionManager.get();
            updateStatLoop(startingId, true, connection, statement, rs);
            return true;
        }
        catch(SQLException s)
        {
            try
            {
                response.sendError(500);
                return false;
            }
            catch(IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
            return false;
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
        }
    } // updateStats()

    private void updateStatLoop(int answers, boolean firstRun, Connection connection, PreparedStatement statement, ResultSet rs) throws SQLException
    {
        // System.out.println("updateStatLoop");
        // On the first post
        if(firstRun == true)
        {
            // System.out.println("First run on updateStatLoop");
            // Increase responses and subresponses by 1
            statement = connection.prepareStatement("UPDATE polr SET responses=responses+1 WHERE id=?"); // responses
            statement.setInt(1, answers);
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE polr SET subresponses=subresponses+1 WHERE id=?"); // subresponses
            statement.setInt(1, answers);
            statement.executeUpdate();

            // Set bump and subbump to now
            // System.out.println("Bump & Subbump in first run on statloop");
            Timestamp now = new Timestamp(System.currentTimeMillis());
            statement = connection.prepareStatement("UPDATE polr SET bump=? WHERE id=?"); // bump
            statement.setTimestamp(1, now);
            statement.setInt(2, answers);
            statement.executeUpdate();
            statement = connection.prepareStatement("UPDATE polr SET subbump=? WHERE id=?"); // subbump
            statement.setTimestamp(1, now);
            statement.setInt(2, answers);
            statement.executeUpdate();
        } // if(firstRun == true)

        // On all subsequent posts
        if(answers != 0)
        {
            // System.out.println("Inside of answers!=0");
            // Get next id
            statement = connection.prepareStatement("SELECT answers FROM polr WHERE id=?");
            statement.setInt(1, answers);
            rs = statement.executeQuery();
            if(rs.next())
                answers = rs.getInt("answers");

            // System.out.println("Increasing subresponses next");
            // Increase subresponses
            statement = connection.prepareStatement("UPDATE polr SET subresponses=subresponses+1 WHERE id=?");
            statement.setInt(1, answers);
            statement.executeUpdate();

            // System.out.println("Updating subbump next");
            // Set subbump to now
            Timestamp now = new Timestamp(System.currentTimeMillis());
            statement = connection.prepareStatement("UPDATE polr SET subbump=? WHERE id=?");
            statement.setTimestamp(1, now);
            statement.setInt(2, answers);
            statement.executeUpdate();

            // System.out.println("Continuing to next updateStatLoop next");
            updateStatLoop(answers, false, connection, statement, rs); // Continue to next post
        } // if(answers != 0)
    } // updateStatLoop()

    private boolean createPost(String query, String title, String content, int answers, String author, HttpServletResponse response)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        // System.out.println("Parameters = good");
        try
        {
            connection = ConnectionManager.get();
            statement = connection.prepareStatement(query);

            statement.setString(1, title);
            statement.setString(2, content);
            statement.setInt(3, answers);
            statement.setString(4, author);

            statement.executeUpdate();
            return true;
        }
        catch(SQLException s)
        {
            try
            {
                response.sendError(500);
                return false;
            }
            catch(IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }
        finally
        {
            // Close in reversed order.
            // if (rs != null) try { rs.close(); } catch (SQLException s) {}
            if(statement != null)
                try
                {
                    statement.close();
                }
                catch(SQLException s)
                {
                }
        }
    } // ArrayList createPost()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {

    }

    @Override
    protected boolean execute()
    {

    }

} // PostCreator
