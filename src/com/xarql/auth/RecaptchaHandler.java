package com.xarql.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RecaptchaHandler
 */
@WebServlet("/RecaptchaHandler")
public class RecaptchaHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecaptchaHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.sendRedirect("http://xarql.com/auth");
	} // doGet()

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String recaptcha;
		request.setAttribute("g-recaptcha-response", request.getParameter("g-recaptcha-response"));
		if(request.getAttribute("g-recaptcha-response") == null)
			recaptcha = "";
		else
			recaptcha = request.getAttribute("g-recaptcha-response").toString();
		request.setAttribute("g-recaptcha-response", recaptcha);
		
		String tomcatSession = request.getRequestedSessionId();
		
		new AuthSession(tomcatSession, recaptcha, "recaptcha");
		if(AuthTable.contains(tomcatSession))
		{
			response.setStatus(200);
			response.sendRedirect("http://xarql.com/auth");
		}
		else
			response.sendError(400, "Recaptcha Invalid");
	} // doPost()

} // RecaptchaHandler
