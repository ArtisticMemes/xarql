package com.xarql.chat;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChatUpdt
 */
@WebServlet("/ChatUpdt")
public class ChatUpdt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatUpdt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long last;
		try 
		{
			last = Long.parseLong(request.getParameter("last"));
		}
		catch(NumberFormatException nfe)
		{
			response.sendError(400);
			return;
		}
		Timestamp lastUpdate = new Timestamp(last);
		MessageRetriever mr = new MessageRetriever(response, lastUpdate);
		request.setAttribute("messages", mr.execute());
		request.getRequestDispatcher("/src/chat/updt.jsp").forward(request, response);
	} // doGet()

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	} // doPost()

} // ChatUpdt
