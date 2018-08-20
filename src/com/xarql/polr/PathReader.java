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
	
	public static final String DEFAULT_SORT = "subbump";
	public static final String DEFAULT_FLOW = "desc";
       
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
			// use sort parameter
			String sort;
			request.setAttribute("sort", request.getParameter("sort"));
			if(request.getAttribute("sort") == null)
				sort = DEFAULT_SORT;
			else
				sort = request.getAttribute("sort").toString();
						
			// use flow parameter
			String flow;
			request.setAttribute("flow", request.getParameter("flow"));
			if(request.getAttribute("flow") == null)
				flow = DEFAULT_FLOW;
			else
				flow = request.getAttribute("flow").toString();
						
				int id;
				try
				{
					id = Integer.parseInt(pathParts[1]);
				}
				catch (NumberFormatException nfe)
				{
					response.sendError(400);
					return;
				}
				PostRetriever ps = new PostRetriever(id, sort, flow);
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
