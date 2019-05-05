/*
 * MIT License http://g.xarql.net Copyright (c) 2018 Bryan Christopher Johnson
 */

/*
 * DISCLAIMER This code was based on code by Bauke Scholtz The original can be
 * found here : http://balusc.omnifaces.org/2007/07/fileservlet.html
 */
package net.xarql.serve;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.xarql.util.DeveloperOptions;

/**
 * Servlet implementation class StaticProvider
 */
@WebServlet ("/StaticProvider")
public class Static extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public static final int     BUFFER_SIZE        = 5120;                           // 5KB
    private static final String FILE_STORE         = DeveloperOptions.getFileStore();
    private static final String NOT_FOUND_FILENAME = "not_found";

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Static()
    {
        super();
    } // Static()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Get file path from the URL
        String filePath = request.getRequestURL().substring(DOMAIN.length() + 10);
        if(filePath == null || filePath.equals(""))
        {
            response.sendError(404);
            return;
        }

        // Locate and determine the file's existence
        File file = new File(FILE_STORE, filePath);

        // Used for the MIME type of the response
        String contentType = request.getServletContext().getMimeType(file.getName());

        if(!file.exists() || file.isDirectory() || contentType == null)
        {
            contentType = request.getServletContext().getMimeType(filePath);
            if(contentType != null && contentType.equals("image/png"))
            {
                file = new File(getServletContext().getRealPath("/src").replace('\\', '/'), "/icon/not_found.png");
                contentType = "image/png";
            }
            else
            {
                file = new File(getServletContext().getRealPath("/src").replace('\\', '/'), "/icon/not_found.jpg");
                contentType = "image/jpeg";
            }
        }

        // Set up response with file specs
        response.reset();
        response.setBufferSize(BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Cache-Control", "public, max-age=86400");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        // Allocate space for file/response streams
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try
        {
            // input is file from disk. output is this servlet's response
            input = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), BUFFER_SIZE);

            // Stream input to output
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while((length = input.read(buffer)) > 0)
            {
                output.write(buffer, 0, length);
            }
        }
        finally
        {
            // Close the streams if they're still live
            if(input != null)
            {
                try
                {
                    input.close();
                }
                catch(IOException io)
                {
                    io.printStackTrace();
                }
            }
            if(output != null)
            {
                try
                {
                    output.close();
                }
                catch(IOException io)
                {
                    io.printStackTrace();
                }
            }
        }
    } // doGet()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Reject POST requests
        response.sendError(400);
        return;
    } // doPost()

} // Static()
