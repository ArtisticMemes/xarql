/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.polr;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Updt
 */
@WebServlet("/Updt")
public class Updt extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest currentRequest = null;
	private HttpServletResponse currentResponse = null;
	
	public static final String DEFAULT_SORT = PostRetriever.DEFAULT_SORT;
	public static final String DEFAULT_FLOW = PostRetriever.DEFAULT_FLOW;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Updt() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		currentRequest = request;
		currentResponse = response;
		
		// Get sort and flow, not needed, will go to defaults
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
		
		
		
		// Get id and page numbers, these must be included in ajax request
		request.setAttribute("id", request.getParameter("id"));
		request.setAttribute("page", request.getParameter("page"));
		
		if(attributeEmpty("id") || attributeEmpty("page"))
		{
			//System.out.println("Parameters not found");
			response.sendError(400);
			return;
		}
		else
		{
			int id;
			int page;
			try
			{
				id = Integer.parseInt(request.getAttribute("id").toString());
				page = Integer.parseInt(request.getAttribute("page").toString());
			}
			catch (NumberFormatException nfe)
			{
				response.sendError(400);
				return;
			}
			if(page < PathReader.MIN_PAGE || page > PathReader.MAX_PAGE)
				page = PathReader.MIN_PAGE; // default
			int postSkipCount = page * PathReader.POSTS_PER_PAGE;
			int postCount = PathReader.POSTS_PER_PAGE;
			PostRetriever ps = new PostRetriever(id, sort, flow, postSkipCount, postCount);
			ArrayList<Post> posts = ps.execute(response);
			//System.out.println(posts.size());
			//System.out.println("ps.execute() ran well");
			request.setAttribute("posts", posts);
			if(posts.size() > 0)
				request.getRequestDispatcher("/src/polr/updt.jsp").forward(request, response);
			else
				response.sendError(404);
			return;
		}
	} // doGet()
	
	private boolean attributeEmpty(String name)
	{
		if(currentRequest.getAttribute(name) == null || currentRequest.getAttribute(name).toString().equals(""))
			return true;
		else
			return false;
	} // attributeEmpty(String name)

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	} // doPost()

} // Updt
