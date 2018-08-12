package com.xarql.main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.polr.Post;
import com.xarql.polr.PostCreator;
import com.xarql.polr.PostRetriever;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Servlet implementation class Polr
 */
@WebServlet("/Polr")
public class Polr extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest currentRequest = null;
	private HttpServletResponse currentResponse = null;
	
	public static final String DEFAULT_SORT = "subbump";
	public static final String DEFAULT_FLOW = "desc";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Polr() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//System.out.println("incoming request for /polr");
		currentRequest = request;
		currentResponse = response;
		
		request.setAttribute("id", request.getParameter("id"));
		
			// use sort parameter
			String sort;
			request.setAttribute("sort", request.getParameter("sort"));
			if(attributeEmpty("sort"))
				sort = DEFAULT_SORT;
			else
				sort = request.getAttribute("sort").toString();
			
			// use flow parameter
			String flow;
			request.setAttribute("flow", request.getParameter("flow"));
			if(attributeEmpty("flow"))
				flow = DEFAULT_FLOW;
			else
				flow = request.getAttribute("flow").toString();
			
			if(attributeEmpty("id"))
			{
				response.sendRedirect("http://xarql.com/polr?id=0");
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
				PostRetriever ps = new PostRetriever(id, sort, flow);
				request.setAttribute("posts", ps.execute(response));
				request.getRequestDispatcher("/src/polr/polr.jsp").forward(request, response);
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
} // Polr
