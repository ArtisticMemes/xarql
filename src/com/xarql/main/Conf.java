package com.xarql.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.chat.ChatReset;
import com.xarql.polr.PolrReset;

/**
 * Servlet implementation class Conf
 */
@WebServlet("/Conf")
public class Conf extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static final String POLR_ROOT_POST_TITLE = "ROOT POST";
	public static final String POLR_ROOT_POST_CONTENT = "Additional information available at <a href=\"http://xarql.com/help\">xarql.com/help</a>";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Conf() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("password", request.getParameter("password"));
		request.setAttribute("g-recaptcha-response", request.getParameter("g-recaptcha-response"));
		String password = "";
		String g_recaptcha_response = "";
		if(request.getAttribute("password") != null && request.getAttribute("g-recaptcha-response") != null)
		{
			password = request.getAttribute("password").toString();
			g_recaptcha_response = request.getAttribute("g-recaptcha-response").toString();
		}
		
		
		if(password.equals(Secrets.ConfPassword) && VerifyRecaptcha.verify(g_recaptcha_response))
		{
			boolean allWorked = ChatReset.execute(response) && PolrReset.execute(response);
			request.setAttribute("success", allWorked);
			if(allWorked)
				request.getRequestDispatcher("/src/conf/conf.jsp").forward(request, response);
			//System.out.println("Performed reset");
			return;
		}
		else
		{
			request.getRequestDispatcher("/src/conf/conf.jsp").forward(request, response);
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

}
