package com.xarql.user.front;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.user.Account;
import com.xarql.user.AccountDeleter;
import com.xarql.user.EmailAttacher;
import com.xarql.user.PasswordChanger;
import com.xarql.util.DeveloperOptions;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class AccountActions
 */
@WebServlet ("/user/act")
public class AccountActions extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountActions()
    {
        super();
    }

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
            String type = request.getParameter("type");
            if(type == null)
                response.sendError(400);

            try
            {
                switch(type)
                {
                    case "log_out" :
                        util.getAuthSession().kill();
                        break;
                    default :
                        throw new IllegalArgumentException("Unrecognized or forbidden action type for HTTP GET method.");
                }
                response.sendRedirect(DOMAIN + "/user");
            }
            catch(Exception e)
            {
                response.sendRedirect(DOMAIN + "/user?fail=" + e.getMessage());
            }
        }
        else
            response.sendError(401);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        if(util.userHasAccount())
        {
            String type = request.getParameter("type");
            if(type == null)
                response.sendError(400);

            try
            {
                switch(type)
                {
                    case "log_out" :
                        util.getAuthSession().kill();
                        break;
                    case "attach_email" :
                        attachEmail(request, util.getAccount());
                        break;
                    case "password_change" :
                        changePassword(request, util.getAccount());
                        break;
                    case "delete" :
                        deleteAccount(request, util.getAccount());
                        util.getAuthSession().kill();
                        break;
                    default :
                        throw new IllegalArgumentException("Unrecognized action type.");
                }
                response.sendRedirect(DOMAIN + "/user");
            }
            catch(Exception e)
            {
                response.sendRedirect(DOMAIN + "/user?fail=" + e.getMessage());
            }
        }
        else
            response.sendError(401);
    }

    private static void changePassword(HttpServletRequest request, Account account) throws Exception
    {
        String password = request.getParameter("password");
        String newPassword = request.getParameter("new_password");
        String retype = request.getParameter("retype");

        if(password == null || newPassword == null || retype == null)
            throw new IllegalArgumentException("A parameter was null");

        if(!newPassword.equals(retype))
            throw new IllegalArgumentException("The password confirmation field didn't match the new password field");

        new Account(account.getUsername(), password); // Check password validity
        new PasswordChanger(account.getUsername(), newPassword).use();
    }

    private static void deleteAccount(HttpServletRequest request, Account account) throws Exception
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username == null || password == null)
            throw new IllegalArgumentException("A parameter was null");

        if(!username.equals(account.getUsername()))
            throw new IllegalArgumentException("The username field didn't match the account's username");

        new Account(account.getUsername(), password); // Check password validity
        new AccountDeleter(account.getUsername()).use(); // Delete account from database
    }

    private static void attachEmail(HttpServletRequest request, Account account) throws Exception
    {
        String email = request.getParameter("email");

        if(email == null)
            throw new IllegalArgumentException("A parameter was null");

        new EmailAttacher(account, email).use();
    }

}
