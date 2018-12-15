package com.xarql.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.util.BuildTimer;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class AccountPage
 */
@WebServlet ("/AccountPage")
public class AccountPage extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountPage()
    {
        super();
        // TODO Auto-generated constructor stub
    } // AccountPage()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        BuildTimer bt = new BuildTimer(request);
        ServletUtilities util = new ServletUtilities(request);
        util.standardSetup();
        request.getRequestDispatcher("/src/user/login.jsp").forward(request, response);
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
