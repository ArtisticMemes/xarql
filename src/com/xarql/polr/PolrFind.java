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
	
	private static final String DEFAULT_SORT = PostRetriever.DEFAULT_SORT;
	private static final String DEFAULT_FLOW = PostRetriever.DEFAULT_FLOW;
       
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
		// use sort parameter
		String sort;
		request.setAttribute("sort", request.getParameter("sort"));
		if(request.getAttribute("sort") == null)
			sort = DEFAULT_SORT;
		else
			sort = request.getAttribute("sort").toString();
		request.setAttribute("sort", sort);
												
		// use flow parameter
		String flow;
		request.setAttribute("flow", request.getParameter("flow"));
		if(request.getAttribute("flow") == null)
			flow = DEFAULT_FLOW;
		else
			flow = request.getAttribute("flow").toString();
		request.setAttribute("flow", flow);
		
		// use query parameter
		String query;
		request.setAttribute("query", request.getParameter("q"));
		if(request.getAttribute("query") == null)
			query = "";
		else
			query = request.getAttribute("query").toString();
		request.setAttribute("query", query);
		
		PostFinder pf = new PostFinder(response, query, sort, flow);
		ArrayList<Post> posts = pf.execute();
		request.setAttribute("posts", posts);
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
