/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.auth.AuthTable;
import com.xarql.chat.ChatReset;
import com.xarql.polr.PolrReset;
import com.xarql.util.Secrets;

/**
 * Servlet implementation class Conf
 */
@WebServlet ("/Conf")
public class Conf extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public static final String POLR_ROOT_POST_TITLE   = "ROOT POST";
    public static final String POLR_ROOT_POST_CONTENT = "Additional information available at <a href=\"https://xarql.com/help\">xarql.com/help</a>";

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Conf()
    {
        super();
    } // Conf()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("domain", DOMAIN);

        String tomcatSession = request.getRequestedSessionId();
        if(tomcatSession != null)
        {
            String associatedGoogleID;
            try
            {
                associatedGoogleID = AuthTable.get(tomcatSession).getGoogleId();
            }
            catch(Exception e)
            {
                response.sendError(401);
                return;
            }
            if(Secrets.modList().contains(associatedGoogleID))
            {
                if(request.getParameter("reset") != null && request.getParameter("reset").equals("yes"))
                {
                    boolean allWorked = ChatReset.execute(response) && PolrReset.execute(response);
                    request.setAttribute("success", allWorked);
                    if(allWorked)
                        request.getRequestDispatcher("/src/conf/conf.jsp").forward(request, response);
                    return;
                }
                else
                {
                    request.getRequestDispatcher("/src/conf/conf.jsp").forward(request, response);
                    return;
                }
            }
        }
        else
        {
            response.sendError(401);
            return;
        }
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

} // Conf
