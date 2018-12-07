/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */

package com.xarql.polr;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.util.ServletUtilities;
import com.xarql.util.TextFormatter;

/**
 * Servlet implementation class PolrHash
 */
@WebServlet ("/PolrHash")
public class PolrHash extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String JSP_PATH = "/src/polr/hash.jsp";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolrHash()
    {
        super();
        // TODO Auto-generated constructor stub
    } // PolrHash()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        util.standardSetup();
        String tag = util.useParam("tag");
        tag = tag.toLowerCase();
        if(!TextFormatter.isAlphaNumeric(tag) || tag.length() > 24)
        {
            response.sendError(400);
            return;
        }

        if(tag != null)
        {
            HashPostRetriever hpr = new HashPostRetriever(response, tag);
            if(hpr.execute() && hpr.getData().size() > 0)
                request.setAttribute("posts", hpr.getData());
            request.getRequestDispatcher(JSP_PATH).forward(request, response);
            return;
        }
        else
        {
            request.getRequestDispatcher(JSP_PATH).forward(request, response);
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

} // PolrHash
