/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.chat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.xarql.auth.AuthSession;
import com.xarql.util.DBManager;
import com.xarql.util.TextFormatter;

public class MessageCreator
{
    private boolean     goodParameters;
    private String      message;
    private AuthSession session;

    private int determinedID;

    private static final int MAX_MESSAGE_LENGTH = 256;

    public MessageCreator(String message, AuthSession session)
    {
        goodParameters = true;
        setMessage(message);
        setSession(session);
    } // MessageCreator()

    private void setMessage(String message)
    {
        if(message.length() > 0 && message.length() < MAX_MESSAGE_LENGTH)
            this.message = TextFormatter.full(message);
        else
            goodParameters = false;
    } // setMessage()

    private void setSession(AuthSession session)
    {
        this.session = session;
    } // setSession()

    public int getDeterminedID()
    {
        return determinedID;
    } // getDeterminedID()

    public boolean execute(HttpServletResponse response)
    {
        if(goodParameters)
        {
            ChatRoom.add(message, session);

            determinedID = determineID(response);
            if(determinedID == 0)
                return false;

            Connection connection = null;
            PreparedStatement statement = null;
            String query = "INSERT INTO chat (message, session) VALUES (?, ?)";

            try
            {
                connection = DBManager.getConnection();
                // System.out.println("Connection gotten");
                statement = connection.prepareStatement(query);
                statement.setString(1, message);
                statement.setString(2, session.getColor());
                statement.executeUpdate();
                // System.out.println("Update Executed");
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

    private int determineID(HttpServletResponse response)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        String query = "SELECT MAX(id) FROM chat";

        // System.out.println("Parameters = good");
        try
        {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(query);

            rs = statement.executeQuery();
            rs.first();
            int id = rs.getInt(1) + 1;
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

} // MessageCreator
