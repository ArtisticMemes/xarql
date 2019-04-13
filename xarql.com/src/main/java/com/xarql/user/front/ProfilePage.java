package com.xarql.user.front;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.main.DeveloperOptions;
import com.xarql.polr.UserPostRetriever;
import com.xarql.user.AccountGrabber;
import com.xarql.user.AccountProcessor;
import com.xarql.util.Secrets;
import com.xarql.util.ServletUtilities;
import com.xarql.util.TextFormatter;

/**
 * Servlet implementation class ProfilePage
 */
@WebServlet ("/ProfilePage")
public class ProfilePage extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String  DOMAIN              = DeveloperOptions.getDomain();
    private static final boolean TESTING             = DeveloperOptions.getTesting();
    private static final int     MAX_USERNAME_LENGTH = AccountProcessor.MAX_VARIABLE_LENGTH;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfilePage()
    {
        super();
    } // ProfilePage()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        String username = util.useParam("name");
        if(username != null && !username.equals(""))
        {
            username = username.toLowerCase();

            AccountGrabber ag = new AccountGrabber(username);
            if(ag.execute())
            {
                if(ag.getID() == -1)
                    response.sendError(404);
                else
                {
                    request.setAttribute("username", username);
                    request.setAttribute("is_mod", Secrets.modList().contains(username));

                    if(!TextFormatter.isAlphaNumeric(username) || username.length() > MAX_USERNAME_LENGTH)
                    {
                        response.sendError(400);
                        return;
                    }

                    UserPostRetriever upr;
                    try
                    {
                        upr = new UserPostRetriever(username);
                        upr.execute();
                        request.setAttribute("posts", upr.getData());
                    }
                    catch(Exception e)
                    {
                        if(TESTING)
                            e.printStackTrace();
                        response.sendError(500, "SQL error occured when trying to retrieve this account's posts.");
                        return;
                    }

                    request.getRequestDispatcher("/src/user/view.jsp").forward(request, response);
                }
            }
            else
                response.sendError(500, "SQL error occured when trying to determine the account's existence.");
        }
        else
            response.sendRedirect(DOMAIN + "/user");
    } // doGet()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    } // doPost()

} // ProfilePage
