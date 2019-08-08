package com.xarql.user.front;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.auth.AuthSession;
import com.xarql.user.Account;
import com.xarql.util.DeveloperOptions;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class LogInHandler
 */
@WebServlet ("/user/log_in/form")
public class LogInHandler extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogInHandler()
    {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities.rejectGetMethod(response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        if(util.hasParams("username", "password"))
            try
            {
                new AuthSession(request.getRequestedSessionId(), new Account(request.getParameter("username").trim().toLowerCase(), request.getParameter("password")));
                response.sendRedirect(DOMAIN + "/user");
            }
            catch(Exception e)
            {
                response.sendRedirect(DOMAIN + "/user/log_in?prefill=" + util.useParam("username") + "&fail=" + e.getMessage());
            }
        else
            response.sendError(400);
    }

}
