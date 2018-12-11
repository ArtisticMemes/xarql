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

import com.xarql.util.BuildTimer;
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

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolrFlat()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        BuildTimer bt = new BuildTimer(request);
        ServletUtilities.standardSetup(request);

        // use sort parameter
        String sort;
        request.setAttribute("sort", request.getParameter("sort"));
        if(request.getAttribute("sort") == null)
            sort = DEFAULT_SORT;
        else
            sort = request.getAttribute("sort").toString();
        request.setAttribute("sort", sort);

        // use flow parameter
        String flow;
        request.setAttribute("flow", request.getParameter("flow"));
        if(request.getAttribute("flow") == null)
            flow = DEFAULT_FLOW;
        else
            flow = request.getAttribute("flow").toString();
        request.setAttribute("flow", flow);

        // use ajax parameter
        boolean ajax;
        request.setAttribute("ajax", request.getParameter("ajax"));
        if(request.getAttribute("ajax") == null)
            ajax = false;
        else
            ajax = Boolean.parseBoolean(request.getAttribute("ajax").toString());

        // get page
        int page;
        request.setAttribute("page", request.getParameter("page"));
        if(request.getAttribute("page") == null)
            page = MIN_PAGE; // default
        else
        {
            try
            {
                page = Integer.parseInt(request.getAttribute("page").toString());
            }
            catch(NumberFormatException nfe)
            {
                page = MIN_PAGE; // default
            }
        }
        if(page < MIN_PAGE || page > MAX_PAGE)
            page = MIN_PAGE; // default
        request.setAttribute("page", page);

        FlatPostRetriever fpr = new FlatPostRetriever(response, sort, flow, page);
        ArrayList<Post> posts = fpr.execute();
        request.setAttribute("posts", posts);
        if(ajax)
            request.getRequestDispatcher("/src/polr/flat-ajax.jsp").forward(request, response);
        else
            request.getRequestDispatcher("/src/polr/flat.jsp").forward(request, response);
    } // doGet()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // TODO Auto-generated method stub
        doGet(request, response);
    } // doPost()

} // PolrFlat
