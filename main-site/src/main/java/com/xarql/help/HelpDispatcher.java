package com.xarql.help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.github.rjeschke.txtmark.Processor;
import com.xarql.util.DeveloperOptions;
import com.xarql.util.JSPBuilder;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class HelpDispatcher
 */
@WebServlet ("/HelpDispatcher")
public class HelpDispatcher extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN        = DeveloperOptions.getDomain();
    private static final String ROOT_PATH     = DOMAIN + "/help/main";
    private static final String INSERT_MARKER = "<!-- INSERT MARKDOWN -->";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelpDispatcher()
    {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/help/help", getServletContext());
        JSPBuilder.build("/help/docs/main", getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        if(pathParts.length == 1)
        {
            response.sendRedirect(ROOT_PATH);
            return;
        }
        else if(pathParts.length == 2)
        {
            String root = getServletContext().getRealPath("/src/help").replace('\\', '/');
            File doc = new File(root + "/docs/" + pathParts[1] + ".jsp");
            String docName = pathParts[1].toLowerCase();
            if(doc.exists() == false)
            {
                File md = new File(root + "/docs/" + pathParts[1] + ".md");
                try
                {
                    String template1 = JSPBuilder.grabFile(root + "/template.html");
                    String template2 = template1.substring(template1.indexOf(INSERT_MARKER));
                    template1 = template1.substring(0, template1.indexOf(INSERT_MARKER));

                    Scanner scan = new Scanner(md);
                    String mdContent = "";
                    while(scan.hasNextLine())
                    {
                        mdContent += scan.nextLine();
                        mdContent += '\n';
                    }
                    scan.close();

                    mdContent = template1 + Processor.process(mdContent) + template2;

                    doc.createNewFile();
                    FileWriter fw = new FileWriter(doc);
                    fw.write(mdContent);
                    fw.close();
                }
                catch(FileNotFoundException fnfe)
                {
                    response.sendError(404);
                    return;
                }
            }

            if(docName.equals("main"))
            {

                File folder = new File(root + "/docs");
                File[] files = folder.listFiles();
                ArrayList<String> fileNames = new ArrayList<>();

                for(File file : files)
                    if(file.isFile())
                    {
                        // add file name excluding extension
                        String name = file.getName().substring(0, file.getName().indexOf('.'));
                        if(!name.equals("main") && !fileNames.contains(name))
                            fileNames.add(name);
                    }
                request.setAttribute("pages", fileNames);
            }

            request.setAttribute("topic", docName);
            request.getRequestDispatcher("/src/help/docs/" + docName + ".jsp").forward(request, response);
            return;
        }
        else if(pathParts.length > 2)
        {
            response.sendRedirect(DOMAIN + "/help/" + pathParts[1]);
            return;
        }
        else
        {
            response.sendRedirect(ROOT_PATH);
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
