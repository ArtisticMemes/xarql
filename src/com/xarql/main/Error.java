package com.xarql.main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Error
 */
@WebServlet("/Error")
public class Error extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HttpServletRequest currentRequest = null;
	HttpServletResponse currentResponse = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Error() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		currentRequest = request;
		currentResponse = response;
		request.setAttribute("code", request.getParameter("code"));
		request.setAttribute("url", request.getParameter("url"));
		request.setAttribute("name", request.getParameter("name"));
		if(attributeEmpty("url") || attributeEmpty("name"))
		{
			request.setAttribute("url", "http://xarql.com/help");
			request.setAttribute("name", "/help");
		}
		if(attributeEmpty("code"))
		{
			request.setAttribute("code", "Unknown");
		}
		request.getRequestDispatcher("/src/error/error.jsp").forward(request, response);
	}
	
	// Check if the attribute has content
	private boolean attributeEmpty(String name)
	{
		if(currentRequest.getAttribute(name) == null || currentRequest.getAttribute(name).toString().equals(""))
			return true;
		else
			return false;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
