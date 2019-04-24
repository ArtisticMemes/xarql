/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.main;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.util.JSPBuilder;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Error
 */
@WebServlet ("/Error")
public class Error extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String CODE = "code";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Error()
    {
        super();
    } // Error()

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/error/error", getServletContext());
    } // init()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);

        String code;
        if(util.hasParam(CODE))
            code = util.useParam(CODE);
        else
        {
            code = "???";
            request.setAttribute(CODE, code);
        }

        String type;
        switch(code)
        {
            case "429" :
                type = "Spam";
                break;
            case "410" :
                type = "Gone";
                break;
            case "405" :
                type = "Bad Method";
                break;
            case "404" :
                type = "Not Found";
                break;
            case "403" :
                type = "Forbidden";
                break;
            case "401" :
                type = "Unauthenticated";
                break;
            case "400" :
                type = "Bad Request";
                break;
            default :
                type = "Unknown";
        }
        request.setAttribute("type", type);
        request.getRequestDispatcher("/src/error/error.jsp").forward(request, response);
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

} // Error
