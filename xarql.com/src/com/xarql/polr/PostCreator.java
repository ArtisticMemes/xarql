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

import com.xarql.util.DBManager;
import com.xarql.util.TextFormatter;

public class PostCreator
{

    // Attributes --> To be set by end user
    private String  title;
    private String  content;
    private int     answers;
    private String  author;
    private boolean goodParameters;

    private int determinedID;

    // Limits
    public static final int MAX_TITLE_LENGTH   = 64;
    public static final int MAX_CONTENT_LENGTH = 4096;
    public static final int MIN_CONTENT_LENGTH = 1;
    public static final int MIN_ID             = 0;

    private static final long ONE_WEEK_MILLIS = 604800000;

    // Defaults
    public static final String DEFAULT_AUTHOR  = "Unknown";
    public static final String DEFAULT_WARNING = "None";

    public PostCreator(String title, String content, int answers, String author)
    {
        goodParameters = true;
        setTitle(title);
        setContent(content);
        setAnswers(answers);
        setAuthor(author);
    } // PostCreator(String title, String content, int answers)

    private void setTitle(String title)
    {
        // TODO: Create word filter
        // title = wordFilter(title);
        if(title.length() > MAX_TITLE_LENGTH)
        {
            this.title = "[TITLE TOO LONG]";
            this.goodParameters = false;
        }
        else
            this.title = TextFormatter.full(title);
    } // setTitle(String title)

    private void setContent(String content)
    {
        // TODO: Create word filter
        // content = wordFilter(content);
        if(content.length() > MAX_CONTENT_LENGTH)
        {
            this.content = "[CONTENT TOO LONG]";
            this.goodParameters = false;
        }
        else if(content.length() < MIN_CONTENT_LENGTH)
        {
            this.content = "[CONTENT TOO SHORT]";
            this.goodParameters = false;
        }
        else
            this.content = TextFormatter.full(content);
    } // setContent()

    private void setAnswers(int answers)
    {
        // TODO: Add existence check
        if(answers < MIN_ID)
        {
            answers = MIN_ID;
            this.goodParameters = false;
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
    } // getDeterminedID()

    public boolean execute(HttpServletResponse response) // <-- Not up to naming conventions, but looks Google's naming
    {
        // System.out.println("Attempting to create post");
        if(goodParameters && postExists(answers, response))
        {
            // These should only return false if the sql connection is faulty, as the
            // conditions in which they fail were tested for in the above if statement
            if(createPost("INSERT INTO polr (title, content, answers, author) VALUES (?, ?, ?, ?)", title, content, answers, author, response) == false)
                return false;
            // System.out.println("updating stats next");
            if(updateStats(answers, response) == false)
                return false;

            PageCache.clear();

            determinedID = determineID(response);
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
    } // execute(HttpServletResponse response)

    private int determineID(HttpServletResponse response)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT MAX(id) FROM polr";

        // System.out.println("Parameters = good");
        try
        {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(query);

            rs = statement.executeQuery();
            rs.first();
            int id = rs.getInt(1);
            return id;
        }
        catch(SQLException s)
        {
            try
            {
                response.sendError(500);
                return 0;
            }
            catch(IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return 0;
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
    } // determineID()

    private boolean updateStats(int startingId, HttpServletResponse response)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        // System.out.println("Parameters = good");
        try
        {
            connection = DBManager.getConnection();
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
            if(connection != null)
                try
                {
                    connection.close();
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

    public boolean postExists(int id, HttpServletResponse response)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;

        // System.out.println("Parameters = good");
        try
        {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement("SELECT * FROM polr WHERE id=? AND removed=0");

            statement.setInt(1, answers);

            rs = statement.executeQuery();
            Timestamp lastWeek = new Timestamp(System.currentTimeMillis() - ONE_WEEK_MILLIS);
            if(rs.next() && (lastWeek.compareTo(rs.getTimestamp("subbump")) < 0 || id == 0))
            {
                return true;
            }
            else
            {
                try
                {
                    response.sendError(400);
                    // System.out.println("Prevented post with bad id");
                }
                catch(IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return false;
            }
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
            if(connection != null)
                try
                {
                    connection.close();
                }
                catch(SQLException s)
                {
                }
        }
    } // postExists()

    private boolean createPost(String query, String title, String content, int answers, String author, HttpServletResponse response)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        int result;

        // System.out.println("Parameters = good");
        try
        {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(query);

            statement.setString(1, title);
            statement.setString(2, content);
            statement.setInt(3, answers);
            statement.setString(4, author);

            result = statement.executeUpdate();
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
            if(connection != null)
                try
                {
                    connection.close();
                }
                catch(SQLException s)
                {
                }
        }
    } // ArrayList createPost()
} // PostCreator
