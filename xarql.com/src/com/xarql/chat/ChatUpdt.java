/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.chat;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChatUpdt
 */
@WebServlet ("/ChatUpdt")
public class ChatUpdt extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatUpdt()
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
        int lastID;
        try
        {
            lastID = Integer.parseInt(request.getParameter("last"));
        }
        catch(NumberFormatException nfe)
        {
            response.sendError(400);
            return;
        }
        MessageRetriever mr = new MessageRetriever(response, lastID);
        ArrayList<Message> messages = mr.execute();
        request.setAttribute("messages", messages);
        if(messages.size() > 0)
        {
            request.setAttribute("lastID", messages.get(messages.size() - 1).getId());
            request.getRequestDispatcher("/src/chat/updt.jsp").forward(request, response);
            return;
        }
        else
        {
            request.setAttribute("lastID", 0);
            response.setStatus(304);
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
        // TODO Auto-generated method stub
        doGet(request, response);
    } // doPost()

} // ChatUpdt
