/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class PolrFind
 */
@WebServlet ("/PolrFind")
public class PolrFind extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolrFind()
    {
        super();
    } // PolrFind()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);

        // use query parameter
        String query = util.useParam("q", "");

        // use ajax parameter
        boolean ajax;
        request.setAttribute("ajax", request.getParameter("ajax"));
        if(request.getAttribute("ajax") == null)
            ajax = false;
        else
            ajax = Boolean.parseBoolean(request.getAttribute("ajax").toString());

        PostFinder pf = new PostFinder(query);
        if(pf.execute())
        {
            ArrayList<Post> posts = pf.getData();
            request.setAttribute("posts", posts);
            if(ajax)
                request.getRequestDispatcher("/src/polr/find-ajax.jsp").forward(request, response);
            else
                request.getRequestDispatcher("/src/polr/find.jsp").forward(request, response);
        }
        else
            response.sendError(500, "The search couldn't be completed");
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

} // PolrFind
