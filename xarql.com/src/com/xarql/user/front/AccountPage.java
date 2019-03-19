package com.xarql.user.front;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.main.DeveloperOptions;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class AccountPage
 */
@WebServlet ("/AccountPage")
public class AccountPage extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountPage()
    {
        super();
    } // AccountPage()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        util.standardSetup();
        if(util.userHasAccount())
        {
            request.setAttribute("msg", request.getParameter("msg"));
            request.setAttribute("fail", request.getParameter("fail"));
            request.setAttribute("username", util.getAccount().getUsername());
            request.setAttribute("email", util.getAccount().getEmail());
            request.getRequestDispatcher("/src/user/account.jsp").forward(request, response);
        }
        else
            response.sendRedirect(DOMAIN + "/user/log_in");
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

} // AccountPage
