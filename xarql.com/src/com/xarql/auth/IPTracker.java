package com.xarql.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import com.xarql.util.DBManager;

public class IPTracker
{

    public IPTracker()
    {
        // TODO Auto-generated constructor stub
    } // IPTracker()

    public static void logPolrPost(HttpServletRequest request, int postID)
    {
        log(request, postID, "polr/post");
    } // logPolrPost()

    public static void logChatSend(HttpServletRequest request, int sendID)
    {
        log(request, sendID, "chat/send");
    } // logChatSend()

    public static void logPolrEditRemove(HttpServletRequest request, int targetPostID)
    {
        log(request, targetPostID, "polr/edit/remove");
    } // logPolrEditRemoval()

    public static void logPolrEditRestore(HttpServletRequest request, int targetPostID)
    {
        log(request, targetPostID, "polr/edit/restore");
    } // logPolrEditRestore()

    public static void logPolrEditReplace(HttpServletRequest request, int targetPostID)
    {
        log(request, targetPostID, "polr/edit/replace");
    } // logPolrEditReplace()

    private static void log(HttpServletRequest request, int submissionID, String submissionType)
    {
        String sessionCookie = AuthTable.get(request.getRequestedSessionId()).getTomcatSession();
        String ip = request.getRemoteAddr();

        Connection connection = null;
        PreparedStatement statement = null;
        String query = "INSERT INTO ip_tracking (submission_id, submission_type, address, session_cookie, date) VALUES (?, ?, ?, ?, ?)";

        try
        {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, submissionID);
            statement.setString(2, submissionType);
            statement.setString(3, ip);
            statement.setString(4, sessionCookie);
            statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            statement.executeUpdate();
            return;
        }
        catch(SQLException s)
        {
            System.err.println(s.toString());
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
                { // do nothing
                }
            if(connection != null)
                try
                {
                    connection.close();
                }
                catch(SQLException s)
                { // do nothing
                }
        }
    } // log()

} // IPTracker
