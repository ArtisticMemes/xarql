package com.xarql.polr.edit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.xarql.polr.PageCache;
import com.xarql.util.DBManager;
import com.xarql.util.TextFormatter;

public class PostEditor
{
    private boolean             goodParameters;
    private int                 id;
    private String              title;
    private String              content;
    private HttpServletResponse response;

    public PostEditor(int id, String newTitle, String newContent, HttpServletResponse response)
    {
        goodParameters = true;
        this.response = response;
        setId(id);
        setTitle(newTitle);
        setContent(newContent);
    } // PostRemover()

    private void setId(int id)
    {
        if(id <= 0)
            goodParameters = false;
        else
            this.id = id;
    } // setId(int id)

    private void setContent(String content)
    {
        this.content = TextFormatter.full(content);
    } // setContent()

    private void setTitle(String title)
    {
        this.title = TextFormatter.full(title);
    } // setTitle()

    public boolean execute()
    {
        if(goodParameters)
        {
            Connection connection = null;
            PreparedStatement statement = null;
            String query1 = "UPDATE polr SET title=? WHERE id=?";
            String query2 = "UPDATE polr SET content=? WHERE id=?";

            try
            {
                connection = DBManager.getConnection();

                statement = connection.prepareStatement(query1);
                statement.setString(1, title);
                statement.setInt(2, id);
                statement.executeUpdate();

                statement.close();
                statement = connection.prepareStatement(query2);
                statement.setString(1, content);
                statement.setInt(2, id);
                statement.executeUpdate();

                PageCache.clear();
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
        }
        else
        {
            try
            {
                response.sendError(400);
                return false;
            }
            catch(IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }
    } // execute()

} // PostRemover
