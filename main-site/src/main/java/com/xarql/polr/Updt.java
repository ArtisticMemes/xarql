/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Updt
 */
@WebServlet ("/Updt")
public class Updt extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_SORT = PostRetriever.DEFAULT_SORT;
    public static final String DEFAULT_FLOW = PostRetriever.DEFAULT_FLOW;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Updt()
    {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);

        // Get sort and flow, not needed, will go to defaults
        String sort = util.useParam("sort", DEFAULT_SORT);
        String flow = util.useParam("flow", DEFAULT_FLOW);

        if(!util.hasParams("id", "page"))
        {
            response.sendError(400);
            return;
        }
        else
        {
            int id;
            int page;
            try
            {
                id = util.requireInt("id");
                page = util.requireInt("page");
            }
            catch(NoSuchElementException e)
            {
                response.sendError(400);
                return;
            }
            if(page < PathReader.MIN_PAGE || page > PathReader.MAX_PAGE)
                page = PathReader.MIN_PAGE; // default
            int postSkipCount = page * PathReader.POSTS_PER_PAGE;
            int postCount = PathReader.POSTS_PER_PAGE;
            PostRetriever ps = new PostRetriever(id, sort, flow, postSkipCount, postCount);
            if(ps.execute())
            {
                request.setAttribute("posts", ps.getData());
                if(ps.getData().size() > 0)
                    request.getRequestDispatcher("/src/polr/updt.jsp").forward(request, response);
                else
                    response.sendError(404);
                return;
            }
            else
                response.sendError(500);
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

} // Updt
