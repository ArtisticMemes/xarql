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
	
	public static final String DEFAULT_SORT = "subbump";
	public static final String DEFAULT_FLOW = "desc";
       
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
		
		request.setAttribute("id", request.getParameter("id"));
		
		if(attributeEmpty("id"))
		{
			//System.out.println("Parameters not found");
			response.sendError(400);
			return;
		}
		else
		{
			int id;
			try
			{
				id = Integer.parseInt(request.getAttribute("id").toString());
			}
			catch (NumberFormatException nfe)
			{
				response.sendError(400);
				return;
			}
			PostRetriever ps = new PostRetriever(id);
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
