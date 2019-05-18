/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.polr;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.auth.IPTracker;
import com.xarql.auth.SpamFilter;
import com.xarql.main.DeveloperOptions;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class PolrPost
 */
@WebServlet (description = "Processes post requests", urlPatterns = {
        "/polr/post"
})
public class PostProcessor extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostProcessor()
    {
        super();
    } // PostProcessor()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.sendError(400);
        return;
    } // doGet()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        if(util.userIsAuth())
        {
            if(!SpamFilter.shouldBlock(request))
            {
                String content;
                String title;
                int answers;
                try
                {
                    content = util.require("content");
                    title = util.useParam("title");
                    answers = util.requireInt("answers");
                }
                catch(NoSuchElementException e)
                {
                    response.sendError(400);
                    return;
                }

                String author = PostCreator.DEFAULT_AUTHOR;
                if(util.userHasAccount())
                    author = util.getAccount().getUsername();

                PostCreator pc = new PostCreator(title, content, answers, author);
                if(pc.execute(response))
                {
                    IPTracker.logPolrPost(request, pc.getDeterminedID());
                    response.setStatus(200);
                    final PrintWriter pw = response.getWriter();
                    pw.println("<p>Posting was a success!</p>");
                    pw.println("<a href=\"" + DOMAIN + "/polr/" + pc.getDeterminedID() + "\">Your Post</a>");
                    pw.println("<p>You are most likely seeing this page because you disabled JavaScript.</p>");
                }
                else
                    response.sendError(500);
            }
            else
                response.sendError(429);
        }
        else
            response.sendError(401);
    } // doPost()

} // PostProcessor
