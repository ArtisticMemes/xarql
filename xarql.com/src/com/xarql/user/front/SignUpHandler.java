package com.xarql.user.front;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.main.DeveloperOptions;
import com.xarql.user.AccountProcessor;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class SignUpHandler
 */
@WebServlet ("/SignUpHandler")
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
    } // SignUpHandler()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities.rejectGetMethod(response);
    } // doGet()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(ServletUtilities.hasParameters(new String[]{
                "username", "password"
        }, request, response))
        {
            try
            {
                new AccountProcessor(response, request.getParameter("username"), request.getParameter("password"));
                response.sendRedirect(DOMAIN + "/user/login?prefill=" + request.getParameter("username"));
            }
            catch(Exception e)
            {
                response.sendRedirect(DOMAIN + "/user/signup?fail=" + e.getMessage());
            }
        }
        else
            response.sendError(400);
    } // doPost()

} // SignUpHandler
