/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GoogleHandler
 */
@WebServlet("/GoogleHandler")
public class GoogleHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoogleHandler() {
        super();
        // TODO Auto-generated constructor stub
    } // GoogleHandler()

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("http://xarql.com/auth");
	} // doGet()

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idToken;
		request.setAttribute("id_token", request.getParameter("id_token"));
		if(request.getAttribute("id_token") == null)
			idToken = "";
		else
			idToken = request.getAttribute("id_token").toString();
		request.setAttribute("id_token", idToken);
		
		String tomcatSession = request.getRequestedSessionId();
		
		new AuthSession(tomcatSession, idToken);
		if(AuthTable.contains(tomcatSession))
			response.setStatus(200);
		else
			response.sendError(400);
	} // doPost()
	
} // GoogleHandler
