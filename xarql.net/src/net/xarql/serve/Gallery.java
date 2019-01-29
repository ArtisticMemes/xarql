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
        int init = DEFAULT_INIT;
        if(UploadProcessor.getHighestImageID("jpg") < UploadProcessor.getHighestImageID("png"))
            init = UploadProcessor.getHighestImageID("jpg") - IMAGE_COUNT;
        else
            init = UploadProcessor.getHighestImageID("png") - IMAGE_COUNT;

        // If the user has specified a starting id (initial id), use that instead
        if(request.getParameter("init") != null && !request.getParameter("init").equals(""))
        {
            try
            {
                init = Integer.parseInt(request.getParameter("init"));
            }
            catch(NumberFormatException nfe)
            {
                init = DEFAULT_INIT;
            }
        }
        if(init < MIN_INIT) // Prevent errors where init is too low
            init = DEFAULT_INIT;

        // Determine valid IDs of images and add them to a list
        ArrayList<Image> images = new ArrayList<Image>();
        for(int i = init; i <= UploadProcessor.getHighestImageID("jpg") && i < init + IMAGE_COUNT; i++)
            images.add(new Image(Base62Converter.to(i), 0));

        for(int i = init; i <= UploadProcessor.getHighestImageID("png") && i < init + IMAGE_COUNT; i++)
            images.add(new Image(Base62Converter.to(i), 1));

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
