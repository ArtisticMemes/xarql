/*
 * MIT License http://g.xarql.net Copyright (c) 2018 Bryan Christopher Johnson
 */
package net.xarql.serve;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.xarql.util.DeveloperOptions;
import net.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Viewer
 */
@WebServlet ("/Viewer")
public class Viewer extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public static final String DOMAIN = DeveloperOptions.DOMAIN;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Viewer()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        util.standardSetup();

        String URI = request.getRequestURI();
        String[] pathParts = URI.split("/");

        String id = pathParts[pathParts.length - 1].substring(1);
        String type = pathParts[pathParts.length - 1].substring(0, 1);

        switch(type)
        {
            case "0" :
                type = "jpg";
                break;
            case "1" :
                type = "svg";
                break;
            case "2" :
                type = "png";
                break;
            default : // Should never occur
                type = "";
                break;
        }

        request.setAttribute("id", id);
        request.setAttribute("type", type);
        request.getRequestDispatcher("/src/viewer/image.jsp").forward(request, response);
        return;
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

} // Viewer
