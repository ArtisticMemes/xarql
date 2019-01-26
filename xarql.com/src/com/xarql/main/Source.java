/*
 * MIT License http://g.xarql.net Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Source
 */
@WebServlet ("/Source")
public class Source extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final int    INPUT_BUFFER = 65536;                       // 64KB
    private static final String DOMAIN       = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Source()
    {
        super();
        // TODO Auto-generated constructor stub
    } // Source()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Get file path from the URI
        String filePath = request.getRequestURL().substring(DOMAIN.length());
        if(filePath == null || filePath.equals(""))
        {
            response.sendError(404);
            return;
        }

        // Locate and determine the file's existence
        File file = new File(getServletContext().getRealPath("/").replace('\\', '/'), filePath);
        if(!file.exists() || file.isDirectory())
        {
            response.sendError(404);
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
        response.setBufferSize(INPUT_BUFFER);
        response.setContentType(contentType + "; charset=utf-8"); // The source code had better be in UTF-8 !
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Cache-Control", "public, max-age=86400");
        response.setHeader("Keep-Alive", "timeout=10, max=100");
        response.setHeader("Connection", "keep alive");
        response.setHeader("Allow", "GET");

        // Allocate space for file/response streams
        BufferedInputStream input = null;
        ByteArrayOutputStream tmp = null;
        BufferedOutputStream output = null;

        try
        {
            // input is file from disk. output is this servlet's response
            input = new BufferedInputStream(new FileInputStream(file), INPUT_BUFFER);
            tmp = new ByteArrayOutputStream(INPUT_BUFFER);
            // Stream input to tmp. tmp stores the file in memory
            byte[] buffer = new byte[INPUT_BUFFER];
            int length;
            while((length = input.read(buffer)) > 0)
            {
                tmp.write(buffer, 0, length);
            }

            output = new BufferedOutputStream(response.getOutputStream());

            // Stream tmp to output. tmp is the input file while in memory
            output.write(tmp.toByteArray());
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
        ServletUtilities.rejectPostMethod(response);
    } // doPost()

} // Source
