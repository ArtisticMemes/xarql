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

import net.xarql.util.Base62Converter;
import net.xarql.util.DeveloperOptions;
import net.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Viewer
 */
@WebServlet ("/Viewer")
public class Viewer extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Viewer()
    {
        super();
    } // Viewer()

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

        if(pathParts.length == 0)
            response.sendRedirect(DOMAIN + "/-/upload");
        else
        {

            String id = pathParts[pathParts.length - 1].substring(1);
            String type = pathParts[pathParts.length - 1].substring(0, 1);
            request.setAttribute("loc", type + id); // locator

            FileType extension;
            try
            {
                extension = FileType.parseInt(type);
            }
            catch(IllegalArgumentException e)
            {
                response.sendError(400, e.getMessage());
                return;
            }

            if(id == null || id.length() == 0)
            {
                response.sendError(400, "ID of media was not provided");
                return;
            }
            else
            {
                try
                {
                    Base62Converter.from(id);
                }
                catch(IllegalArgumentException e)
                {
                    response.sendError(400, e.getMessage());
                    return;
                }
            }

            request.setAttribute("id", id);
            request.setAttribute("type", extension.getExtension());
            response.setHeader("Cache-Control", "public, max-age=86400");
            request.getRequestDispatcher("/src/viewer/image.jsp").forward(request, response);
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

} // Viewer
