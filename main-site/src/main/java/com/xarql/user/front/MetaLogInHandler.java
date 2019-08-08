package com.xarql.user.front;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.auth.AuthSession;
import com.xarql.user.Account;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class JSLogInHandler
 */
@WebServlet ("/user/log_in/meta")
public class MetaLogInHandler extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MetaLogInHandler()
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
        LoginAttempt attempt;
        if(util.hasParams("username", "password"))
        {
            String username = util.useParam("username").trim().toLowerCase();
            String password = util.useParam("password");
            try
            {
                new AuthSession(request.getRequestedSessionId(), new Account(username, password));
                attempt = new LoginAttempt(username, true, "You are now logged in.");
            }
            catch(Exception e)
            {
                attempt = new LoginAttempt(username, false, e.getMessage());
            }
        }
        else
        {
            response.sendError(400);
            return;
        }

        response.getOutputStream().print(attempt.toString());
    }

}
