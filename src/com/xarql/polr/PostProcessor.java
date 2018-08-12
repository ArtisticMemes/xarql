package com.xarql.polr;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PolrPost
 */
@WebServlet(description = "Processes post requests", urlPatterns = { "/polr/post" })
public class PostProcessor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostProcessor() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(400);
		return;
	} // doGet()

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.sendRedirect("http://xarql.com/polr"); <-- Used to test servlet config
		
		request.setAttribute("title", request.getParameter("title"));
		request.setAttribute("content", request.getParameter("content"));
		request.setAttribute("answers", request.getParameter("answers"));
		request.setAttribute("g-recaptcha-response", request.getParameter("g-recaptcha-response"));
		
		// null pointer exception prevention
		if(request.getAttribute("title") == null || request.getAttribute("content") == null || request.getAttribute("answers") == null || request.getAttribute("g-recaptcha-response") == null)
		{
			response.sendError(400);
			return;
		}
		
		String title = request.getAttribute("title").toString();
		String content = request.getAttribute("content").toString();
		// Get an int from the answers string in the request
		int answers;
		try
		{
			answers = Integer.parseInt(request.getAttribute("answers").toString());
		}
		catch (NumberFormatException nfe)
		{
			response.sendError(400);
			return;
		}
		String g_recaptcha_response = request.getAttribute("g-recaptcha-response").toString();
		
		PostCreator pc = new PostCreator(title, content, answers);
		if(pc.execute(response, g_recaptcha_response))
			response.sendRedirect("http://xarql.com/polr?id=" + pc.getAnswers());
		
		
		return;
	}

}
