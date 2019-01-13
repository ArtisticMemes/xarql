package com.xarql.user.front;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.main.DeveloperOptions;
import com.xarql.user.AccountGrabber;
import com.xarql.util.Secrets;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class ProfilePage
 */
@WebServlet ("/ProfilePage")
public class ProfilePage extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN = DeveloperOptions.getDomain();

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
        String username = request.getParameter("name");
        if(username != null && !username.equals(""))
        {
            ServletUtilities util = new ServletUtilities(request);
            util.standardSetup();

            AccountGrabber ag = new AccountGrabber(username);
            if(ag.execute())
            {
                if(ag.getID() == -1)
                    response.sendError(404);
                else
                {
                    // NotificationHandler nh = new NotificationHandler(true, username);
                    // request.setAttribute("notifications", nh.getList());
                    request.setAttribute("username", username);
                    request.setAttribute("is_mod", Secrets.modList().contains(username));
                    request.getRequestDispatcher("/src/user/view.jsp").forward(request, response);
                }
            }
            else
                response.sendError(500, "SQL error occured");
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
