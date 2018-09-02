package com.xarql.polr;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PolrFind
 */
@WebServlet("/PolrFind")
public class PolrFind extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolrFind() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// use query parameter
		String query;
		request.setAttribute("query", request.getParameter("q"));
		if(request.getAttribute("query") == null)
			query = "";
		else
			query = request.getAttribute("query").toString();
		request.setAttribute("query", query);
		
		// use ajax parameter
		boolean ajax;
		request.setAttribute("ajax", request.getParameter("ajax"));
		if(request.getAttribute("ajax") == null)
			ajax = false;
		else
			ajax = Boolean.parseBoolean(request.getAttribute("ajax").toString());
		
		PostFinder pf = new PostFinder(response, query);
		ArrayList<Post> posts = pf.execute();
		request.setAttribute("posts", posts);
		if(ajax)
			request.getRequestDispatcher("/src/polr/find-ajax.jsp").forward(request, response);
		else
			request.getRequestDispatcher("/src/polr/find.jsp").forward(request, response);
		
	} // doGet()

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	} // doPost()

} // PolrFind
