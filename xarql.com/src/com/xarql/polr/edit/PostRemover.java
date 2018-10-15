package com.xarql.polr.edit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.xarql.polr.PageCache;
import com.xarql.util.DBManager;

public class PostRemover
{
    private boolean             goodParameters;
    private int                 id;
    private HttpServletResponse response;

    public PostRemover(int id, HttpServletResponse response)
    {
        goodParameters = true;
        this.response = response;
        setId(id);
    } // PostRemover()

    private void setId(int id)
    {
        if(id <= 0)
            goodParameters = false;
        else
            this.id = id;
    } // setId(int id)

    public boolean execute()
    {
        if(goodParameters)
        {
            Connection connection = null;
            PreparedStatement statement = null;
            String query = "UPDATE polr SET removed=1 WHERE id=?";

            try
            {
                connection = DBManager.getConnection();
                statement = connection.prepareStatement(query);
                statement.setInt(1, id);
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
