package com.xarql.polr;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PathReader
 */
@WebServlet("/PathReader")
public class PathReader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_SORT = PostRetriever.DEFAULT_SORT;
	public static final String DEFAULT_FLOW = PostRetriever.DEFAULT_FLOW;
	
	public static final int MIN_PAGE = 0;
	public static final int MAX_PAGE = 9;
	
	public static final int POSTS_PER_PAGE = 10;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PathReader() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		String[] pathParts = pathInfo.split("/");
		
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
		
		// get page
		int page;
		request.setAttribute("page", request.getParameter("page"));
		if(request.getAttribute("page") == null)
			page = MIN_PAGE; // default
		else
		{
			try
			{
				page = Integer.parseInt(request.getAttribute("page").toString());
			}
			catch(NumberFormatException nfe)
			{
				page = MIN_PAGE; // default
			}
		}
		if(page < MIN_PAGE || page > MAX_PAGE)
			page = MIN_PAGE; // default
		request.setAttribute("page", page);
		
		int postSkipCount = page * POSTS_PER_PAGE;
		int postCount = POSTS_PER_PAGE;
			
		
		/*System.out.println(pathParts.length);
		for(int i = 0; i < pathParts.length; i++)
			System.out.println(pathParts[i]);*/
		
		if(pathParts.length == 1)
		{
			response.sendRedirect("http://xarql.com/polr/0");
			return;
		}
		else if(pathParts.length == 2)
		{
			
						
				int id;
				try
				{
					if(pathParts[1].contains("&"))
						id = Integer.parseInt(pathParts[1].substring(0, pathParts[1].indexOf('&')));
					else
						id = Integer.parseInt(pathParts[1]);
				}
				catch (NumberFormatException nfe)
				{
					response.sendError(400);
					return;
				}
				request.setAttribute("id", id);
				PostRetriever ps = new PostRetriever(id, sort, flow, postSkipCount, postCount);
				ArrayList<Post> posts = ps.execute(response);
				request.setAttribute("posts", posts);
				if(posts.size() > 0)
					request.getRequestDispatcher("/src/polr/polr.jsp").forward(request, response);
				else
					response.sendError(404);
			return;
		}
		else if(pathParts.length > 2)
		{
			response.sendRedirect("http://xarql.com/polr/" + pathParts[1]);
			return;
		}
		else
		{
			response.sendRedirect("http://xarql.com/polr/0");
			return;
		}
		
	} // doGet()

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	} // doPost()

} // PathReader
