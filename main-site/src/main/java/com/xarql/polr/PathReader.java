/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.io.IOException;
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
 * Servlet implementation class PathReader
 */
@WebServlet ("/PathReader")
public class PathReader extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String SORT         = Polr.SORT;
    private static final String FLOW         = Polr.FLOW;
    public static final String  PAGE         = "page";
    private static final String DEFAULT_SORT = PostRetriever.DEFAULT_SORT;
    private static final String DEFAULT_FLOW = PostRetriever.DEFAULT_FLOW;

    public static final int MIN_PAGE = 0;
    public static final int MAX_PAGE = 4;

    public static final int POSTS_PER_PAGE = PostRetriever.DEFAULT_POST_COUNT;

    public static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PathReader()
    {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/polr/polr", getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        String sort = util.useParam(SORT, DEFAULT_SORT);
        String flow = util.useParam(FLOW, DEFAULT_FLOW);
        util.useParam(PAGE);

        // get page
        int page = util.useInt(PAGE);
        if(page < MIN_PAGE || page > MAX_PAGE)
            page = MIN_PAGE; // default

        int postSkipCount = page * POSTS_PER_PAGE;
        int postCount = POSTS_PER_PAGE;

        if(pathParts.length == 1)
        {
            response.sendRedirect(DOMAIN + "/polr/0");
            return;
        }
        else if(pathParts.length == 2)
        {

            int id;
            try
            {
                if(pathParts[1].contains("&"))
                    id = Integer.parseInt(pathParts[1].substring(0, pathParts[1].indexOf('&')));
                else
                    id = Integer.parseInt(pathParts[1]);
            }
            catch(NumberFormatException nfe)
            {
                response.sendError(400);
                return;
            }
            request.setAttribute("id", id);
            PostRetriever ps = new PostRetriever(id, sort, flow, postSkipCount, postCount);
            if(ps.execute())
            {
                request.setAttribute("posts", ps.getData());
                if(ps.getData().size() > 0)
                {
                    if(ps.getData().get(0).getRemoved())
                        response.sendError(410);
                    else
                        request.getRequestDispatcher("/src/polr/polr.jsp").forward(request, response);
                }
                else
                    response.sendError(404);
                return;
            }
            else
                response.sendError(500);
        }
        else if(pathParts.length > 2)
        {
            response.sendRedirect(DOMAIN + "/polr/" + pathParts[1]);
            return;
        }
        else
        {
            response.sendRedirect(DOMAIN + "/polr/0");
            return;
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

}
