/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.help;

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
 * Servlet implementation class Help
 */
@WebServlet ("/help")
public class Help extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Help()
    {
        super();
    } // Help()

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/help/help", getServletContext());
    } // init()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        response.setHeader("Cache-Control", "public, max-age=86400");
        request.getRequestDispatcher("/src/help/help.jsp").forward(request, response);
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

} // Help
