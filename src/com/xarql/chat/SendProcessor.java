/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.chat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.auth.AuthTable;
import com.xarql.polr.PostCreator;

/**
 * Servlet implementation class SendProcessor
 */
@WebServlet("/SendProcessor")
public class SendProcessor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendProcessor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(400);
	} // doGet()

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("message", request.getParameter("message"));
		request.setAttribute("session", request.getRequestedSessionId());
		
		// null pointer exception prevention
		if(request.getAttribute("message") == null || request.getAttribute("session") == null)
		{
			response.sendError(400);
			return;
		}
		
		String message = request.getAttribute("message").toString();
		String session = request.getAttribute("session").toString();
		
		if(AuthTable.contains(session))
		{
			//System.out.println("SendProcessor worked");
			MessageCreator mc = new MessageCreator(message, session);
			if(mc.execute(response))
				response.sendRedirect("http://xarql.com/chat");
			return;
		}
		else
		{
			response.sendError(401, "http://xarql.com/auth");
			return;
		}
		
	} // doPost()

}
