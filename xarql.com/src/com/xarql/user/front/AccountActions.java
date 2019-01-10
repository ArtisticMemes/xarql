package com.xarql.user.front;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.main.DeveloperOptions;
import com.xarql.user.Account;
import com.xarql.user.AccountDeleter;
import com.xarql.user.PasswordChanger;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class AccountActions
 */
@WebServlet ("/AccountActions")
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
        // TODO Auto-generated constructor stub
    } // AccountActions()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities.rejectGetMethod(response);
    } // doGet()

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
                if(type.equals("log_out"))
                    util.getAuthSession().kill();
                else if(type.equals("password_change"))
                    changePassword(request, util.getAccount());
                else if(type.equals("delete"))
                {
                    deleteAccount(request, util.getAccount());
                    util.getAuthSession().kill();
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
    } // doPost()

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
        PasswordChanger pc = new PasswordChanger(account.getUsername(), newPassword); // Change password in database
    } // changePassword()

    private static void deleteAccount(HttpServletRequest request, Account account) throws Exception
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if(username == null || password == null)
            throw new IllegalArgumentException("A parameter was null");

        if(!username.equals(account.getUsername()))
            throw new IllegalArgumentException("The username field didn't match the account's username");

        new Account(account.getUsername(), password); // Check password validity
        AccountDeleter ad = new AccountDeleter(account.getUsername()); // Delete account from database
    } // deleteAccount()

} // AccountActions
