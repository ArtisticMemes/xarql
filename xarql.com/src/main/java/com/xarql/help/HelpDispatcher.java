package com.xarql.help;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.rjeschke.txtmark.Processor;
import com.xarql.main.DeveloperOptions;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class HelpDispatcher
 */
@WebServlet ("/HelpDispatcher")
public class HelpDispatcher extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN    = DeveloperOptions.getDomain();
    private static final String ROOT_PATH = DOMAIN + "/help/main";

    private static final String TEMPLATE_1 = HelpPageTemplate.TEMPLATE_1;
    private static final String TEMPLATE_2 = HelpPageTemplate.TEMPLATE_2;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelpDispatcher()
    {
        super();
    } // HelpDispatcher()

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
            File doc = new File(getServletContext().getRealPath("/src/help/docs/").replace('\\', '/') + pathParts[1] + ".jsp");
            String docName = pathParts[1];
            if(doc.exists() == false)
            {
                File md = new File(getServletContext().getRealPath("/src/help/docs/").replace("\\", "/") + pathParts[1] + ".md");
                try
                {
                    Scanner scan = new Scanner(md);
                    String mdContent = "";
                    while(scan.hasNextLine())
                    {
                        mdContent += scan.nextLine();
                        mdContent += '\n';
                    }
                    scan.close();

                    mdContent = TEMPLATE_1 + Processor.process(mdContent) + TEMPLATE_2;

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

} // HelpDispatcher
