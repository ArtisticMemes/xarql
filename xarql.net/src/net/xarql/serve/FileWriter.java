/*
 * MIT License http://g.xarql.net Copyright (c) 2018 Bryan Christopher Johnson
 */
package net.xarql.serve;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Upload
 */
@WebServlet ("/Upload")
public class FileWriter extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final int    BUFFER_SIZE = Static.BUFFER_SIZE;
    private static final String FILE_STORE  = Static.FILE_STORE;
    // private static final String DOMAIN = DeveloperOptions.DOMAIN;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileWriter()
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
        // Reject GET requests
        response.sendError(400);
        return;
    } // doGet()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Validate request
        // Validate file
        // Access database
        // Write file to disk
        // Get file path from the URI

        // Locate and determine the file's existence
        File file = new File(FILE_STORE, "2/raw.jpg");
        if(file.exists() || file.isDirectory())
        {
            response.sendError(400);
            return;
        }

        // Used for the MIME type of the response
        String contentType = request.getServletContext().getMimeType(file.getName());

        // Unsupported file type
        if(contentType == null)
        {
            response.sendError(500);
            return;
        }

        // Set up response with file specs
        response.reset();
        response.setBufferSize(Static.BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\""); // Tells browser
                                                                                                      // to give user
                                                                                                      // save prompt

        // Allocate space for file/response streams
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try
        {
            // input is file from disk. output is this servlet's response
            input = new BufferedInputStream(request.getInputStream(), BUFFER_SIZE);
            output = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);

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

        // Return 200
        doGet(request, response);
    } // doPost()

} // FileWriter
