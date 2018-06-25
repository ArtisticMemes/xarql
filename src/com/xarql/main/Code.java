package com.xarql.main;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Code
 */
@WebServlet("/Code")
public class Code extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HttpServletRequest currentRequest = null;
	private HttpServletResponse currentResponse = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Code() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // Forwards the page to the requested code file
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		currentRequest = request;
		currentResponse = response;
		request.setAttribute("file", request.getParameter("file"));
		if(attributeEmpty("file"))
		{
			sendError("400");
		}
		else if(request.getAttribute("file").toString().contains("/") == false)
		{
			forwardToResource("code/" + request.getAttribute("file").toString());
		}
		else
		{
			forwardToResource(request.getAttribute("file").toString());
		}
	}
	
	private boolean fileExists(String fileLocation)
	{
		File file = new File(currentRequest.getServletContext().getRealPath(fileLocation));
		if(file.exists())
		    return true;
		else
			return false;
	}
	
	private void forwardToResource(String fileName) throws ServletException, IOException
	{
		String fileLocation = "/src/" + fileName;
		if(fileExists(fileLocation))
			currentRequest.getRequestDispatcher(fileLocation).forward(currentRequest, currentResponse);
		else
			sendError("404");
	}

	// Check if the attribute has content
	private boolean attributeEmpty(String name)
	{
		if(currentRequest.getAttribute(name) == null || currentRequest.getAttribute(name).toString().equals(""))
			return true;
		else
			return false;
	}
		
	// Redirect to an error page
	private void sendError(String codeNumber) throws ServletException, IOException
	{
		currentRequest.setAttribute("code", codeNumber);
		currentRequest.setAttribute("url", "http://xarql.com/docs?for=code");
		currentRequest.setAttribute("name", "Documentation for /code");
		currentRequest.getRequestDispatcher("/src/error/error.jsp").forward(currentRequest, currentResponse);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
