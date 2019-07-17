/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.xarql.main.DeveloperOptions;

public class Post
{
    // Attributes
    private int     id;
    private String  title;
    private String  content;
    private int     answers;
    private boolean removed;
    private String  author;
    private String  warning;
    // Attributes For Sorting
    private Timestamp date;
    private Timestamp bump;
    private Timestamp subbump;
    private int       responses;
    private int       subresponses;

    // Limits
    public static final int MIN_ID               = 0;
    public static final int MIN_RESPONSES        = 0;
    public static final int MIN_SUBRESPONSES     = 0;
    public static final int PREVIEW_LENGTH_LIMIT = 512;

    // Constants
    public static final String DELETION_MESSAGE         = "<span class=\"warn\">[POST REMOVED]</span>";
    public static final String TITLE_DELETION_MESSAGE   = DELETION_MESSAGE;
    public static final String CONTENT_DELETION_MESSAGE = "<span class=\"warn\">CONTENT NOT AVAILABLE</span><br><span class=\"warn\">REPLYING NOT PERMITTED</span>";
    public static final String DOMAIN                   = DeveloperOptions.getDomain();
    public static final String DEFAULT_AUTHOR           = PostCreator.DEFAULT_AUTHOR;
    public static final String DEFAULT_WARNING          = PostCreator.DEFAULT_WARNING;
    private static final long  ONE_WEEK_MILLIS          = 604800000;

    // Constructor
    public Post(int id, String title, String content, int answers, int removed, Timestamp date, Timestamp bump, Timestamp subbump, int responses, int subresponses, String author, String warning)
    {
        setId(id);
        setTitle(title);
        setContent(content);
        setAnswers(answers);
        setRemoved(removed);
        setDate(date);
        setBump(bump);
        setSubbump(subbump);
        setResponses(responses);
        setSubresponses(subresponses);
        setAuthor(author);
        setWarning(warning);
    }

    // Mutators (All private)

    private void setId(int id)
    {
        if(id >= MIN_ID)
            this.id = id;
        else
            this.id = MIN_ID;
    }

    private void setTitle(String title)
    {
        if(title == null)
            this.title = "";
        else
            this.title = title;
    }

    private void setContent(String content)
    {
        this.content = content;
    }

    private void setAnswers(int answers)
    {
        if(answers >= MIN_ID)
            this.answers = answers;
        else
            this.answers = MIN_ID;
    }

    private void setRemoved(int removed)
    {
        if(removed == 0)
            this.removed = false;
        else
        {
            this.removed = true;
            setTitle(TITLE_DELETION_MESSAGE);
            setContent(CONTENT_DELETION_MESSAGE);
        }
    }

    private void setDate(Timestamp date)
    {
        this.date = date;
    }

    private void setBump(Timestamp bump)
    {
        this.bump = bump;
    }

    private void setSubbump(Timestamp subbump)
    {
        this.subbump = subbump;
    }

    private void setResponses(int responses)
    {
        if(responses >= MIN_RESPONSES)
            this.responses = responses;
        else
            this.responses = MIN_RESPONSES;
    }

    private void setSubresponses(int subresponses)
    {
        if(subresponses >= MIN_SUBRESPONSES)
            this.subresponses = subresponses;
        else
            this.subresponses = MIN_SUBRESPONSES;
    }

    private void setAuthor(String author)
    {
        if(author != null && !author.equals(""))
            this.author = author;
        else
            this.author = DEFAULT_AUTHOR;
    }

    private void setWarning(String warning)
    {
        if(warning != null && !warning.equals(""))
            this.warning = warning;
        else
            this.warning = DEFAULT_WARNING;
    }

    // Selectors (All public)

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title.replace("{DOMAIN}", DOMAIN);
    }

    public String getTitleText()
    {
        return title.replaceAll("<[^>]*>", "");
    }

    public String getContent()
    {
        return content.replace("{DOMAIN}", DOMAIN);
    }

    public String getContentText()
    {
        return content.replaceAll("<[^>]*>", "");
    }

    public String getContentPreview()
    {
        int limit;
        if(getContentText().length() < PREVIEW_LENGTH_LIMIT)
            limit = getContentText().length();
        else
            limit = PREVIEW_LENGTH_LIMIT;
        return getContentText().substring(0, limit - 1);
    }

    public int getAnswers()
    {
        return answers;
    }

    public boolean getRemoved()
    {
        return removed;
    }

    public Timestamp getDate()
    {
        return date;
    }

    public String getDisplayDate()
    {
        return getDate().toString().substring(0, 16);
    }

    public Timestamp getBump()
    {
        return bump;
    }

    public String timeSinceBump()
    {
        long timeSince = System.currentTimeMillis() - bump.getTime();
        if(timeSince < 60000) // Less than 1 minute
            return timeSince / 1000 + "s";
        else if(timeSince < 3600000) // Less than 1 hour
            return timeSince / 60000 + "m";
        else if(timeSince < 86400000) // Less than 1 day
            return timeSince / 3600000 + "h";
        else
            return timeSince / 86400000 + "d";
    }

    public Timestamp getSubbump()
    {
        return subbump;
    }

    public String timeSinceSubbump()
    {
        long timeSince = System.currentTimeMillis() - subbump.getTime();
        if(timeSince < 60000) // Less than 1 minute
            return timeSince / 1000 + "s";
        else if(timeSince < 3600000) // Less than 1 hour
            return timeSince / 60000 + "m";
        else if(timeSince < 86400000) // Less than 1 day
            return timeSince / 3600000 + "h";
        else
            return timeSince / 86400000 + "d";
    }

    public int getResponses()
    {
        return responses;
    }

    public int getSubresponses()
    {
        return subresponses;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getWarning()
    {
        return warning;
    }

    public boolean isExpired()
    {
        Timestamp lastWeek = new Timestamp(System.currentTimeMillis() - ONE_WEEK_MILLIS);
        return lastWeek.compareTo(getSubbump()) > 0 && getId() != 0;
    }

    @Override
    public String toString()
    {
        return getTitleText() + " ~ " + getContentText();
    }

    public String replyStats()
    {
        String output = "";

        // Replies
        if(getResponses() == 0)
            output += "no replies";
        else if(getResponses() == getSubresponses())
            output += "replies: " + getResponses();
        else
            output += "replies: " + getResponses() + ", sub: " + indirectReplies();

        // Bump
        if(getBump().equals(getDate()))
            output += "";
        else if(getBump().equals(getSubbump()))
            output += " ~ bump: " + timeSinceBump();
        else
            output += " ~ bump: " + timeSinceBump() + ", sub: " + timeSinceSubbump();

        return output;
    }

    public int indirectReplies()
    {
        return getSubresponses() - getResponses();
    }

    /* Static Methods Below */

    public static Post interperetPost(ResultSet rs) throws SQLException
    {
        return new Post(rs.getInt("id"), rs.getString("title"), rs.getString("content"), rs.getInt("answers"), rs.getInt("removed"), rs.getTimestamp("date"), rs.getTimestamp("bump"), rs.getTimestamp("subbump"), rs.getInt("responses"), rs.getInt("subresponses"), rs.getString("author"), rs.getString("warning"));
    }

}
