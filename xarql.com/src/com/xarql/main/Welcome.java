/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.main;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.sql.Timestamp;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.auth.AuthTable;
import com.xarql.chat.ChatRoom;
import com.xarql.util.BuildTimer;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Welcome
 */
@WebServlet ("/Welcome")
public class Welcome extends HttpServlet
{
    public static final String     DOMAIN           = DeveloperOptions.getDomain();
    private static final long      serialVersionUID = 1L;
    private static final Timestamp startTime        = new Timestamp(System.currentTimeMillis());
    private static final String    CONTEXT          = DeveloperOptions.getContext();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Welcome()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        BuildTimer bt = new BuildTimer(request);
        ServletUtilities.standardSetup(request);
        request.setAttribute("auth_sessions", AuthTable.size());
        request.setAttribute("live_chats", ChatRoom.size());
        request.setAttribute("live_time", timeSinceStart());
        response.setHeader("Cache-Control", "public, max-age=86400");

        int activeSessions = 0;
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName;
        try
        {
            objectName = new ObjectName("Catalina:type=Manager,context=/" + CONTEXT + ",host=localhost");
            activeSessions = (Integer) mBeanServer.getAttribute(objectName, "activeSessions");
        }
        catch(Exception e)
        {
            // Do nothing
        }

        request.setAttribute("total_sessions", activeSessions);
        request.getRequestDispatcher("/src/welcome/welcome.jsp").forward(request, response);
    } // doGet()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // TODO Auto-generated method stub
        doGet(request, response);
    } // doPost()

    public String timeSinceStart()
    {
        long timeSince = System.currentTimeMillis() - startTime.getTime();
        int days = (int) timeSince / 86400000;
        timeSince %= 86400000;
        int hours = (int) timeSince / 3600000;
        timeSince %= 3600000;
        int minutes = (int) timeSince / 60000;
        timeSince %= 60000;
        int seconds = (int) timeSince / 1000;
        return(days + "d " + hours + "h " + minutes + "m " + seconds + "s");
    } // timeSinceStart()

} // Welcome
