package com.xarql.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.main.DeveloperOptions;

/**
 * Servlet implementation class AuthStatus
 */
@WebServlet ("/AuthStatus")
public class AuthStatus extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthStatus()
    {
        super();
    } // AuthStatus()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(request.getRequestedSessionId() != null && AuthTable.contains(request.getRequestedSessionId()))
            request.getRequestDispatcher("/src/auth/status.jsp").forward(request, response);
        else
            response.sendError(401, "Client has not authenticated. Go to " + DOMAIN + "/auth");
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

} // AuthStatus
