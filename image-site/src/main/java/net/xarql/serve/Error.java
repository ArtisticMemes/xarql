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
import net.xarql.util.NServletUtilities;

/**
 * Provides users with good looking error pages without developer information
 */
@WebServlet ("/Error")
public class Error extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Error()
    {
        super();
    }

    /**
     * Shows an error page with proper descriptive text for the given error code.
     * This should be used through the web.xml to create error-pages that point to
     * <code>/-/error?code=</code>
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        NServletUtilities util = new NServletUtilities(request);

        String code = util.useParam("code", "???");
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
    }

    /**
     * Invokes doGet()
     *
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

}
