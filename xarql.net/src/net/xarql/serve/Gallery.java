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

        ArrayList<Image> images = new ArrayList<Image>();
        for(int i = 1; i <= UploadProcessor.getHighestImageID("jpg") && i < 16; i++)
            images.add(new Image(Base62Converter.to(i), 0));

        for(int i = 1; i <= UploadProcessor.getHighestImageID("png") && i < 16; i++)
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
