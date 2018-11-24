/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.chat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.auth.AuthTable;
import com.xarql.auth.IPTracker;
import com.xarql.util.Secrets;
import com.xarql.util.ServletUtilities;
import com.xarql.util.TextFormatter;

/**
 * Servlet implementation class SendProcessor
 */
@WebServlet ("/SendProcessor")
public class SendProcessor extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendProcessor()
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
    } // doGet()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("message", request.getParameter("message"));

        // null pointer exception prevention
        if(request.getAttribute("message") == null)
        {
            response.sendError(400);
            return;
        }

        String message = request.getAttribute("message").toString();

        if(ServletUtilities.userIsAuth(request))
        {
            // Censor bad words; send "forbidden" error
            if(TextFormatter.shouldCensor(message))
            {
                response.sendError(403);
                return;
            }

            if(message.contains(Secrets.MOD_SIGNATURE) && AuthTable.get(request.getRequestedSessionId()).isMod() == false)
            {
                response.sendError(401);
                return;
            }

            MessageCreator mc = new MessageCreator(message, AuthTable.get(request.getRequestedSessionId()));
            if(mc.execute(response))
            {
                IPTracker.logChatSend(AuthTable.get(request.getRequestedSessionId()), request.getRemoteAddr(), mc.getDeterminedID());
                response.setStatus(200);
            }
            return;
        }
        else
        {
            response.sendError(401, "http://xarql.com/auth");
            return;
        }

    } // doPost()

} // SendProcessor
