package net.xarql.serve;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.xarql.util.Base62Converter;
import net.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Gallery
 */
@WebServlet ("/Gallery")
public class Gallery extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final int IMAGE_COUNT  = 15;
    private static final int DEFAULT_INIT = 1;
    private static final int MIN_INIT     = 1;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Gallery()
    {
        super();
    } // Gallery()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        util.standardSetup();

        // Try to get the newest images based on the highest image id
        int JPGinit = DEFAULT_INIT;
        int PNGinit = DEFAULT_INIT;
        JPGinit = UploadProcessor.getHighestImageID(FileType.JPG) - IMAGE_COUNT;
        PNGinit = UploadProcessor.getHighestImageID(FileType.PNG) - IMAGE_COUNT;

        // If the user has specified a starting id (initial id), use that instead
        if(request.getParameter("init") != null && !request.getParameter("init").equals(""))
        {
            try
            {
                JPGinit = Integer.parseInt(request.getParameter("init"));
                PNGinit = Integer.parseInt(request.getParameter("init"));
            }
            catch(NumberFormatException nfe)
            {
                JPGinit = DEFAULT_INIT;
                PNGinit = DEFAULT_INIT;
            }
        }

        // Prevent errors where init is too low
        if(JPGinit < MIN_INIT)
        {
            JPGinit = DEFAULT_INIT;
        }
        if(PNGinit < MIN_INIT)
        {
            PNGinit = DEFAULT_INIT;
        }

        // Determine valid IDs of images and add them to a list
        ArrayList<Image> images = new ArrayList<>();
        for(int i = JPGinit; i <= UploadProcessor.getHighestImageID(FileType.JPG) && i <= JPGinit + IMAGE_COUNT; i++)
            images.add(new Image(Base62Converter.to(i), FileType.JPG));

        for(int i = PNGinit; i <= UploadProcessor.getHighestImageID(FileType.PNG) && i <= PNGinit + IMAGE_COUNT; i++)
            images.add(new Image(Base62Converter.to(i), FileType.PNG));

        request.setAttribute("images", images);
        request.getRequestDispatcher("/src/viewer/gallery.jsp").forward(request, response);
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

} // Gallery
