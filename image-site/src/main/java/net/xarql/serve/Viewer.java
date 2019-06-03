/*
 * MIT License http://g.xarql.net Copyright (c) 2018 Bryan Christopher Johnson
 */
package net.xarql.serve;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.xarql.util.Base62Converter;
import net.xarql.util.DeveloperOptions;
import net.xarql.util.NServletUtilities;

/**
 * Servlet implementation class Viewer
 */
@WebServlet ("/Viewer")
public class Viewer extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String   DOMAIN = DeveloperOptions.getDomain();
    private static final String[] BASE   = DeveloperOptions.getBase();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Viewer()
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
        new NServletUtilities(request);

        String URI = request.getRequestURI();
        String[] pathParts = URI.split("/");
        if(pathParts.length > 0 && pathParts[0].equals(""))
        {
            // Shift array to the left by 1
            for(int i = 0; i < pathParts.length - 1; i++)
                pathParts[i] = pathParts[i + 1];
            // Remove last index
            pathParts = Arrays.copyOf(pathParts, pathParts.length - 1);
        }

        if(pathParts.length - BASE.length == 0)
            response.sendRedirect(DOMAIN + "/-/upload");
        else if(pathParts.length - BASE.length == 1)
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
                try
                {
                    Base62Converter.from(id);
                }
                catch(IllegalArgumentException e)
                {
                    response.sendError(400, e.getMessage());
                    return;
                }

            request.setAttribute("id", id);
            request.setAttribute("type", extension.getExtension());
            response.setHeader("Cache-Control", "public, max-age=86400");
            if(extension == FileType.JPG || extension == FileType.PNG || extension == FileType.WEBP)
                request.getRequestDispatcher("/src/viewer/image.jsp").forward(request, response);
            else if(extension == FileType.MP3)
                request.getRequestDispatcher("/src/viewer/audio.jsp").forward(request, response);
            else if(extension == FileType.WEBM || extension == FileType.MP4)
                request.getRequestDispatcher("/src/viewer/video.jsp").forward(request, response);
            else
                response.sendError(500, "Missing branches for some FileType enumeration in Viewer.java");
        }
        else
        {
            response.sendError(400, "Too many path elements.");
            return;
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

}
