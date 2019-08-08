/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.auth.AuthTable;
import com.xarql.user.AccountCounter;
import com.xarql.user.AccountGrabber;
import com.xarql.user.AccountMaxID;
import com.xarql.util.DeveloperOptions;
import com.xarql.util.JSPBuilder;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Welcome
 */
@WebServlet ("/Welcome")
public class Welcome extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String CONTEXT = DeveloperOptions.getContext();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Welcome()
    {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/welcome/welcome", getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        new ServletUtilities(request);
        request.setAttribute("auth_sessions", AuthTable.size());

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

        int userCount = AccountCounter.getCount();
        request.setAttribute("user_count", userCount);

        String newestUsername = new AccountGrabber(AccountMaxID.useStatic()).use();
        request.setAttribute("newest_username", newestUsername);

        request.getRequestDispatcher("/src/welcome/welcome.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

}
