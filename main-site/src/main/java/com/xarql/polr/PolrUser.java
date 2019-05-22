package com.xarql.polr;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.user.AccountProcessor;
import com.xarql.util.JSPBuilder;
import com.xarql.util.ServletUtilities;
import com.xarql.util.TextFormatter;

/**
 * Servlet implementation class PolrUser
 */
@WebServlet ("/PolrUser")
public class PolrUser extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String JSP_PATH            = "/src/polr/user.jsp";
    private static final int    MAX_USERNAME_LENGTH = AccountProcessor.MAX_VARIABLE_LENGTH;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolrUser()
    {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/polr/user", getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        String name = util.useParam("name");

        if(name != null)
        {
            name = name.toLowerCase();
            if(!TextFormatter.isAlphaNumeric(name) || name.length() > MAX_USERNAME_LENGTH)
            {
                response.sendError(400);
                return;
            }

            request.setAttribute("name", name);
            UserPostRetriever upr;
            try
            {
                upr = new UserPostRetriever(name);
            }
            catch(Exception e)
            {
                response.sendError(400);
                return;
            }
            request.setAttribute("posts", upr.use());
            request.getRequestDispatcher(JSP_PATH).forward(request, response);
            return;
        }
        else
        {
            request.getRequestDispatcher(JSP_PATH).forward(request, response);
            return;
        }
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
