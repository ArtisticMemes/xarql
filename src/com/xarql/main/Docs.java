package com.xarql.main;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Docs
 */
@WebServlet("/Docs")
public class Docs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private HttpServletRequest currentRequest = null;
	private HttpServletResponse currentResponse = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Docs() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    // Forwards the page to the requested documentation page
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		currentRequest = request;
		currentResponse = response;
		request.setAttribute("for", request.getParameter("for"));
		request.setAttribute("part", request.getParameter("part"));
		if(attributeEmpty("for"))
		{
			request.getRequestDispatcher("/src/docs/docs.jsp").forward(request, response);
		}
		else
		{
			if(attributeEmpty("part"))
			{
				forwardToResource(request.getAttribute("for").toString());
			}
			else
			{
				forwardToResource(request.getAttribute("for").toString(), request.getAttribute("part").toString());
			}
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
	
	private void forwardToResource(String mainFile, String specificFile) throws ServletException, IOException
	{
		String fileLocation = "/src/docs/" + mainFile + "/" + specificFile + ".jsp";
		if(fileExists(fileLocation))
			currentRequest.getRequestDispatcher(fileLocation).forward(currentRequest, currentResponse);
		else
			sendError("404");
	}
	
	// Get introduction to documentation for the project
	private void forwardToResource(String mainFile) throws ServletException, IOException
	{
		forwardToResource(mainFile, mainFile);
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
		currentRequest.getRequestDispatcher("/src/error/error.jsp").forward(currentRequest, currentResponse);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
