package com.xarql.user.front;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.auth.AuthSession;
import com.xarql.auth.IPTracker;
import com.xarql.user.Account;
import com.xarql.user.AccountProcessor;
import com.xarql.util.DeveloperOptions;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class SignUpHandler
 */
@WebServlet ("/user/sign_up/form")
public class SignUpHandler extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpHandler()
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
        if(util.userIsAuth())
        {
            if(util.userHasAccount())
                response.sendError(403, "Please log out");
            else if(util.hasParams("username", "password"))
                try
                {
                    String username = request.getParameter("username").trim().toLowerCase();
                    String password = request.getParameter("password");
                    new AccountProcessor(username, password);
                    new AuthSession(request.getRequestedSessionId(), new Account(request.getParameter("username").trim().toLowerCase(), request.getParameter("password")));
                    IPTracker.logNewUser(request);
                    response.sendRedirect(DOMAIN + "/user?msg=Account Created");
                }
                catch(Exception e)
                {
                    String username = request.getParameter("username");
                    if(username != null && !username.equals(""))
                        response.sendRedirect(DOMAIN + "/user/sign_up?prefill=" + username + "&fail=" + e.getMessage());
                    else
                        response.sendRedirect(DOMAIN + "/user/sign_up?fail=" + e.getMessage());
                }
            else
                response.sendError(400);
        }
        else
            response.sendRedirect(DOMAIN + "/auth?redirect=/user/sign_up");
    }

}
