package com.xarql.user.front;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.polr.UserPostRetriever;
import com.xarql.rsc.Secrets;
import com.xarql.user.Account;
import com.xarql.user.AccountGrabber;
import com.xarql.util.DeveloperOptions;
import com.xarql.util.JSPBuilder;
import com.xarql.util.ServletUtilities;
import com.xarql.util.TextFormatter;

/**
 * Servlet implementation class ProfilePage
 */
@WebServlet ("/user/view")
public class ProfilePage extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String  DOMAIN              = DeveloperOptions.getDomain();
    private static final boolean TESTING             = DeveloperOptions.getTesting();
    private static final int     MAX_USERNAME_LENGTH = Account.MAX_VARIABLE_LENGTH;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfilePage()
    {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/user/view", getServletContext());
    }

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
            if(ag.use() != null)
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

                    try
                    {
                        request.setAttribute("posts", new UserPostRetriever(username).use());
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
