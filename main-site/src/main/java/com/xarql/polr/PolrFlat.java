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
 * Servlet implementation class PolrFlat
 */
@WebServlet ("/PolrFlat")
public class PolrFlat extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DEFAULT_SORT = PostRetriever.DEFAULT_SORT;
    private static final String DEFAULT_FLOW = PostRetriever.DEFAULT_FLOW;
    private static final int    MIN_PAGE     = PathReader.MIN_PAGE;
    private static final int    MAX_PAGE     = PathReader.MAX_PAGE;

    private static final String PAGE = PathReader.PAGE;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolrFlat()
    {
        super();
    } // PolrFlat()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);

        String sort = util.useParam("sort", DEFAULT_SORT);
        String flow = util.useParam("flow", DEFAULT_FLOW);
        boolean ajax = util.useBoolean("ajax", false);
        int page = util.useInt(PAGE);
        if(page < MIN_PAGE || page > MAX_PAGE)
            page = MIN_PAGE; // default
        request.setAttribute(PAGE, page);

        FlatPostRetriever fpr = new FlatPostRetriever(sort, flow, page);
        if(fpr.execute())
        {
            ArrayList<Post> posts = fpr.getData();
            request.setAttribute("posts", posts);
            String loc;
            if(ajax)
                loc = "/src/polr/flat-ajax.jsp";
            else
                loc = "/src/polr/flat.jsp";
            request.getRequestDispatcher(loc).forward(request, response);
        }
        else
            response.sendError(500, "Could not retrieve posts from class FlatPostRetriever");
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

} // PolrFlat
