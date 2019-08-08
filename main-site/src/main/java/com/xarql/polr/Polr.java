/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.util.DeveloperOptions;
import com.xarql.util.JSPBuilder;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Polr
 */
@WebServlet ("/polr")
public class Polr extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    public static final String ID   = "id";
    public static final String SORT = "sort";
    public static final String FLOW = "flow";

    private static final String DEFAULT_SORT = PostRetriever.DEFAULT_SORT;
    private static final String DEFAULT_FLOW = PostRetriever.DEFAULT_FLOW;

    public static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Polr()
    {
        super();
    } // Polr()

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/polr/polr", getServletContext());
    } // init()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);

        String sort = util.useParam(SORT, DEFAULT_SORT);
        String flow = util.useParam(FLOW, DEFAULT_FLOW);

        try
        {
            int id = util.useInt(ID);
            if(sort != DEFAULT_SORT || flow != DEFAULT_FLOW)
                response.sendRedirect(DOMAIN + "/polr/" + id + "&sort=" + sort + "&flow=" + flow);
            else
                response.sendRedirect(DOMAIN + "/polr/" + id);
            return;
        }
        catch(NoSuchElementException nsee)
        {
            response.sendRedirect(DOMAIN + "/polr/0");
            return;
        }
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
} // Polr
