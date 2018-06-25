package com.xarql.main;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.polr.Post;
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
	public static Connection con = null;
	
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest currentRequest = null;
	private HttpServletResponse currentResponse = null;
	private String gRecaptchaResponse = "";
       
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
		currentRequest = request;
		currentResponse = response;
		
		request.setAttribute("id", request.getParameter("id"));
		
		if(attributeEmpty("id"))
			response.sendError(400);
		else
		{
			int id = Integer.parseInt(request.getAttribute("id").toString());
			PostRetriever ps = new PostRetriever(id);
			request.setAttribute("posts", ps.execute(response));
			request.getRequestDispatcher("/src/polr/polr.jsp").forward(request, response);
		}
			
	}
	
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
	
	public static String removeHTML(String unsafeInput)
	{
		String safeInput = unsafeInput.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("\'", "&#039;").replace("\\", "&#092;");
		return safeInput;
	}

}
