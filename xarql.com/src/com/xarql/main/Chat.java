/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.main;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.chat.Message;
import com.xarql.chat.MessageRetriever;
import com.xarql.util.BuildTimer;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Chat
 */
@WebServlet ("/chat")
public class Chat extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Chat()
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
        request.setAttribute("authenticated", ServletUtilities.userIsAuth(request));

        MessageRetriever mr = new MessageRetriever(response);
        ArrayList<Message> messages = mr.execute();
        request.setAttribute("messages", messages);
        if(messages.size() > 0)
        {
            request.setAttribute("lastID", messages.get(messages.size() - 1).getId());
        }
        else
            request.setAttribute("lastID", 0);
        request.getRequestDispatcher("/src/chat/chat.jsp").forward(request, response);
        return;

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

} // Chat
