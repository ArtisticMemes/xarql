package com.xarql.flag;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class ReportPage
 */
@WebServlet ("/ReportPage")
public class ReportPage extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportPage()
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
        ServletUtilities.standardSetup(request);
        if(ServletUtilities.hasParameter("id", request))
        {
            try
            {
                request.setAttribute("id", Integer.parseInt(request.getParameter("id").toString()));
            }
            catch(Exception e)
            {
                request.setAttribute("id", 0);
            }
        }
        else
            request.setAttribute("id", 0);

        if(ServletUtilities.userIsMod(request))
        {
            ReportGrabber rg = new ReportGrabber();
            if(rg.execute())
            {
                ArrayList<Report> reports = rg.getData();
                request.setAttribute("reports", reports);
            }
            else
                return;
        }

        request.getRequestDispatcher("/src/flag/report.jsp").forward(request, response);
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

} // ReportPage
