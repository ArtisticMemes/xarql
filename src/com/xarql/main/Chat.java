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

/**
 * Servlet implementation class Chat
 */
@WebServlet("/chat")
public class Chat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Chat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MessageRetriever mr = new MessageRetriever(response);
		ArrayList<Message> messages = mr.execute();
		request.setAttribute("messages", messages);
		if(messages.size() > 0)
			request.setAttribute("lastID", messages.get(messages.size() - 1).getId());
		else
			request.setAttribute("lastID", 0);
		request.getRequestDispatcher("/src/chat/chat.jsp").forward(request, response);
	} // doGet()

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	} // doPost()

}
