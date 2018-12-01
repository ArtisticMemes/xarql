package com.xarql.polr.edit;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.xarql.polr.PageCache;
import com.xarql.util.DatabaseUpdate;
import com.xarql.util.TextFormatter;

public class PostEditor extends DatabaseUpdate
{
    private int    id;
    private String title;
    private String content;

    public PostEditor(int id, String newTitle, String newContent, HttpServletResponse response)
    {
        super("", response);
        setId(id);
        setTitle(newTitle);
        setContent(newContent);
    } // PostRemover()

    private void setId(int id)
    {
        this.id = id;
    } // setId()

    private void setContent(String content)
    {
        this.content = TextFormatter.full(content);
    } // setContent()

    private void setTitle(String title)
    {
        this.title = TextFormatter.full(title);
    } // setTitle()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        if(commandIndex == 1)
            statement.setString(1, title);
        else if(commandIndex == 2)
            statement.setString(1, content);
        statement.setInt(2, id);
    } // setVariables()

    @Override
    public boolean execute()
    {
        this.command = "UPDATE polr SET title=? WHERE id=?";
        makeRequest();
        this.command = "UPDATE polr SET content=? WHERE id=?";
        makeRequest();
        PageCache.clear();
        return true;
    } // execute()

} // PostRemover
