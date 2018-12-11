package com.xarql.help;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.main.DeveloperOptions;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class HelpDispatcher
 */
@WebServlet ("/HelpDispatcher")
public class HelpDispatcher extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN    = DeveloperOptions.DOMAIN;
    private static final String ROOT_PATH = DOMAIN + "/help/main";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelpDispatcher()
    {
        super();
        // TODO Auto-generated constructor stub
    } // HelpDispatcher()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        util.standardSetup();

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        if(pathParts.length == 1)
        {
            response.sendRedirect(ROOT_PATH);
            return;
        }
        else if(pathParts.length == 2)
        {
            File doc = new File(getServletContext().getRealPath("/src/help/docs/").replace('\\', '/') + pathParts[1] + ".jsp");
            String docName = pathParts[1];
            if(doc.exists())
                request.getRequestDispatcher("/src/help/docs/" + docName + ".jsp").forward(request, response);
            else
                response.sendError(404);
            return;
        }
        else if(pathParts.length > 2)
        {
            response.sendRedirect(DOMAIN + "/help/" + pathParts[1]);
            return;
        }
        else
        {
            response.sendRedirect(ROOT_PATH);
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
        // TODO Auto-generated method stub
        doGet(request, response);
    } // doPost()

} // HelpDispatcher
