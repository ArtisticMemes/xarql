package com.xarql.user.front;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.util.DeveloperOptions;
import com.xarql.util.JSPBuilder;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class AccountPage
 */
@WebServlet ("/user")
public class AccountPage extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountPage()
    {
        super();
    } // AccountPage()

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/user/account", getServletContext());
    } // init()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        if(util.userHasAccount())
        {
            util.useParam("msg");
            util.useParam("fail");
            request.setAttribute("username", util.getAccount().getUsername());
            request.setAttribute("email", util.getAccount().getEmail());
            request.getRequestDispatcher("/src/user/account.jsp").forward(request, response);
        }
        else
            response.sendRedirect(DOMAIN + "/user/log_in");
    } // doGet()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    } // doPost()

} // AccountPage
