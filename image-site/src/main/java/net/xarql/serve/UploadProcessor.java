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
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.xarql.util.ServletUtilities;
import net.xarql.util.Base62Converter;
import net.xarql.util.DeveloperOptions;
import net.xarql.util.NServletUtilities;

/**
 * Processes media uploads and writes them to the disk
 */
@WebServlet ("/Upload")
@MultipartConfig (fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 5 * 5)
public class UploadProcessor extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static HashMap<FileType, Integer> highestImageID = new HashMap<>();
    private static boolean                    firstRun       = true;

    private static final File   FILE_STORE = new File(DeveloperOptions.getFileStore());
    private static final String DOMAIN     = DeveloperOptions.getDomain();
    private static final String INFO_FILE  = "/" + "info.txt";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadProcessor()
    {
        super();
        ensureInit();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities.rejectGetMethod(response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ensureInit();

        FileType fileType;
        NServletUtilities util = new NServletUtilities(request);
        if(util.userIsAuth())
        {
            if(!FILE_STORE.exists())
                FILE_STORE.mkdirs();

            fileType = null;
            for(Part part : request.getParts())
            {
                fileType = getFileType(part);
                if(fileType == null)
                {
                    response.sendError(415, "Uploaded file was an invalid type.");
                    return;
                }
                final File dir = new File(FILE_STORE + "/" + fileType.getExtension() + "/" + Base62Converter.to(getHighestImageID(fileType) + 1));
                if(!dir.exists())
                    dir.mkdirs();
                part.write(dir.getPath() + "/" + "raw" + fileType.dotExtension());
                setHighestImageID(getHighestImageID(fileType) + 1, fileType);
            }

            response.sendRedirect(DOMAIN + "/" + fileType.ordinal() + Base62Converter.to(getHighestImageID(fileType)));
            util.getAuthSession().kill();
        }
        else
            response.sendError(401);
    }

    // Make sure that accurate image counts were gotten at some point
    private static void ensureInit()
    {
        if(firstRun)
        {
            initialize();
            firstRun = false;
        }
    }

    // Get accurate image counts
    private static void initialize()
    {
        for(FileType extension : FileType.values())
            setHighestImageID(getHighestImageID(false, extension), extension);
    }

    private static FileType getFileType(Part part)
    {
        for(String content : part.getHeader("content-disposition").split(";"))
            if(content.trim().startsWith("filename"))
            {
                String name = content.substring(content.indexOf("=") + 2, content.length() - 1);
                return FileType.determine(name, null);
            }
        return null;
    }

    public static int getHighestImageID(FileType fileType)
    {
        return getHighestImageID(true, fileType);
    }

    private static int getHighestImageID(boolean trustingFile, FileType fileType)
    {
        if(highestImageID.get(fileType) == null || !trustingFile)
        {
            int maxFolderID = 0;
            final File infoFile = new File(FILE_STORE + "/" + fileType.getExtension() + "/info.txt");
            if(infoFile.exists() && trustingFile)
            {
                Scanner scan;
                try
                {
                    scan = new Scanner(infoFile);
                    maxFolderID = Base62Converter.from(scan.nextLine());
                    scan.close();
                }
                catch(FileNotFoundException fnfe)
                {
                    fnfe.printStackTrace();
                }
            }
            else
            {
                final File dir = new File(FILE_STORE + "/" + fileType.getExtension());
                final Pattern pattern = Pattern.compile("\\V");
                try(DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir.getAbsolutePath()), entry -> pattern.matcher(entry.getFileName().toString()).matches()))
                {
                    for(Path path : stream)
                    {
                        int folderID = Base62Converter.from(path.getFileName().toString());
                        if(folderID > maxFolderID)
                            maxFolderID = folderID;
                    }
                }
                catch(IOException e)
                {
                    // do nothing
                }
                try
                {
                    FileWriter fw = new FileWriter(infoFile);
                    String id = Base62Converter.to(maxFolderID);
                    fw.write(id);
                    fw.close();
                }
                catch(IOException e)
                {
                    // do nothing
                }
            }
            return maxFolderID;
        }
        else
            return highestImageID.get(fileType);
    }

    private static void setHighestImageID(int input, FileType fileType)
    {
        highestImageID.put(fileType, input);
        final File infoFile = new File(FILE_STORE + "/" + fileType.getExtension() + INFO_FILE);
        try
        {
            FileWriter fw = new FileWriter(infoFile);
            String id = Base62Converter.to(input);
            fw.write(id);
            fw.close();
        }
        catch(IOException ioe)
        {
            highestImageID.put(fileType, getHighestImageID(false, fileType));
        }
    }

}
