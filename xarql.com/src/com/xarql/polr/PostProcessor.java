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

import com.xarql.auth.AuthTable;
import com.xarql.auth.IPTracker;
import com.xarql.auth.SpamFilter;
import com.xarql.util.Secrets;
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

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostProcessor()
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
        ServletUtilities.standardSetup(request);
        if(ServletUtilities.userIsAuth(request))
        {
            if(!SpamFilter.shouldBlock(request))
            {
                request.setAttribute("title", request.getParameter("title"));
                request.setAttribute("content", request.getParameter("content"));
                request.setAttribute("answers", request.getParameter("answers"));

                // null pointer exception prevention
                if(request.getAttribute("title") == null || request.getAttribute("content") == null || request.getAttribute("answers") == null)
                {
                    response.sendError(400);
                    return;
                }

                String title = request.getAttribute("title").toString();
                String content = request.getAttribute("content").toString();

                /*
                 * // Censor bad words; send "forbidden" error
                 * if(TextFormatter.shouldCensor(title) || TextFormatter.shouldCensor(content))
                 * { response.sendError(403); return; }
                 */

                if((content.contains(Secrets.MOD_SIGNATURE) || title.contains(Secrets.MOD_SIGNATURE)) && AuthTable.get(request.getRequestedSessionId()).isMod() == false)
                {
                    response.sendError(401);
                    return;
                }

                // Get an int from the answers string in the request
                int answers;
                try
                {
                    answers = Integer.parseInt(request.getAttribute("answers").toString());
                }
                catch(NumberFormatException nfe)
                {
                    response.sendError(400);
                    return;
                }

                PostCreator pc = new PostCreator(title, content, answers);
                if(pc.execute(response))
                {
                    IPTracker.logPolrPost(request, pc.getDeterminedID());
                    response.setStatus(200);
                }
                return;
            }
            else
            {
                response.sendError(429);
                return;
            }
        }
        else
        {
            response.sendError(401);
            return;
        }
    } // doPost()

} // PostProcessor
