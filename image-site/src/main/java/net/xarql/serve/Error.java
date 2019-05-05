/*
 * MIT License http://g.xarql.net Copyright (c) 2018 Bryan Christopher Johnson
 */
package net.xarql.serve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Error
 */
@WebServlet ("/Error")
public class Error extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private HttpServletRequest currentRequest = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Error()
    {
        super();
    } // Error()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        util.standardSetup();

        currentRequest = request;
        request.setAttribute("code", request.getParameter("code"));
        if(attributeEmpty("code"))
        {
            request.setAttribute("code", "???");
        }
        String code = request.getAttribute("code").toString();
        String type;
        switch(code)
        {
            case "404" :
                type = "Not Found";
                break;
            case "401" :
                type = "Unauthenticated";
                break;
            case "400" :
                type = "Bad Request";
                break;
            case "403" :
                type = "Forbidden";
                break;
            case "500" :
                type = "Server-Side Issue";
                break;
            default :
                code = "???";
                type = "Unknown";
        }
        request.setAttribute("code", code);
        request.setAttribute("type", type);
        request.getRequestDispatcher("/src/error/error.jsp").forward(request, response);
    } // doGet()

    // Check if the attribute has content
    private boolean attributeEmpty(String name)
    {
        if(currentRequest.getAttribute(name) == null || currentRequest.getAttribute(name).toString().equals(""))
            return true;
        else
            return false;
    } // attributeEmpty()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    } // doPost()

} // Error
