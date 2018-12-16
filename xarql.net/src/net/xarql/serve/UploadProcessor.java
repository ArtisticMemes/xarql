/*
 * MIT License http://g.xarql.net Copyright (c) 2018 Bryan Christopher Johnson
 */
package net.xarql.serve;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import net.xarql.util.DeveloperOptions;
import net.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Upload
 */
@WebServlet ("/Upload")
@MultipartConfig (fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadProcessor extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static int highestImageID = getHighestImageID();

    private static final String FILE_STORE = DeveloperOptions.FILE_STORE;
    private static final String DOMAIN     = DeveloperOptions.DOMAIN;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadProcessor()
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
    } // doGet()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        if(util.isAuth())
        {
            File fileStore = new File(FILE_STORE);
            if(!fileStore.exists())
                fileStore.mkdir();

            String exportedFileType = "";
            for(Part part : request.getParts())
            {
                String fileType = getFileType(part);
                if(fileType != null && !fileType.equals(""))
                    exportedFileType = fileType;
                File dir = new File(FILE_STORE + File.separator + (getHighestImageID() + 1));
                if(!dir.exists())
                    dir.mkdirs();
                part.write(FILE_STORE + File.separator + (getHighestImageID() + 1) + File.separator + "raw" + fileType);
                setHighestImageID(getHighestImageID() + 1);
            }

            int typeID;
            if(exportedFileType.equals(".jpg"))
                typeID = 0;
            else if(exportedFileType.equals(".png"))
                typeID = 1;
            else
                typeID = 0;

            response.sendRedirect(DOMAIN + "/" + typeID + getHighestImageID());
        }
        else
            response.sendError(401);
    } // doPost()

    private String getFileType(Part part)
    {
        for(String content : part.getHeader("content-disposition").split(";"))
        {
            if(content.trim().startsWith("filename"))
            {
                String name = content.substring(content.indexOf("=") + 2, content.length() - 1);
                return name.substring(name.indexOf('.'));
            }
        }
        return ".jpg";
    } // getFileType

    private static int getHighestImageID()
    {
        return getHighestImageID(true);
    } // getHighestImageID()

    private static int getHighestImageID(boolean trustingFile)
    {
        if(highestImageID == 0)
        {
            int maxFolderID = 0;
            File infoFile = new File(FILE_STORE + "/info.txt");
            if(infoFile.exists() && trustingFile)
            {
                Scanner scan;
                try
                {
                    scan = new Scanner(infoFile);
                    maxFolderID = Integer.parseInt(scan.nextLine());
                    scan.close();
                }
                catch(FileNotFoundException fnfe)
                {
                    fnfe.printStackTrace();
                }
            }
            else
            {
                File dir = new File(FILE_STORE);
                final Pattern pattern = Pattern.compile("\\d+");
                try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir.getAbsolutePath()), entry -> pattern.matcher(entry.getFileName().toString()).matches()))
                {
                    for(Path path : stream)
                    {
                        int folderID = Integer.parseInt(path.getFileName().toString());
                        if(folderID > maxFolderID)
                        {
                            maxFolderID = folderID;
                        }
                    }
                }

                catch(IOException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    FileWriter fw = new FileWriter(infoFile);
                    String id = Integer.toString(maxFolderID);
                    fw.write(id);
                    fw.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
            return maxFolderID;
        }
        else
            return highestImageID;
    } // getHighestImageID()

    private static void setHighestImageID(int input)
    {
        highestImageID = input;
        File infoFile = new File(FILE_STORE + "/info.txt");
        try
        {
            FileWriter fw = new FileWriter(infoFile);
            String id = Integer.toString(input);
            fw.write(id);
            fw.close();
        }
        catch(IOException ioe)
        {
            highestImageID = getHighestImageID(false);
        }
    } // setHighestImageID()

} // FileWriter
