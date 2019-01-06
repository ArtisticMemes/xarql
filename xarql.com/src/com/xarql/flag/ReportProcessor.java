package com.xarql.flag;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.auth.IPTracker;
import com.xarql.main.DeveloperOptions;
import com.xarql.polr.PostCreator;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class ReportProcessor
 */
@WebServlet ("/ReportProcessor")
public class ReportProcessor extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public static final String DOMAIN = DeveloperOptions.getDomain();

    private static final String[] REQUIRED_PARAMS = {
            "type", "description", "id"
    };

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportProcessor()
    {
        super();
    } // ReportProcessor()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities.rejectGetMethod(response);
    } // doGet()

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(ServletUtilities.userIsAuth(request) && ServletUtilities.hasParameters(REQUIRED_PARAMS, request, response))
        {
            ServletUtilities.standardSetup(request);

            // Make a report
            Report report = null;
            int id;
            try
            {

                id = Integer.parseInt(request.getParameter("id").toString());
                PostCreator pc = new PostCreator("", "", id);
                if(pc.postExists(id, response))
                    report = new Report(request.getParameter("type"), request.getParameter("description"), id);
                else
                {
                    response.sendError(400);
                    return;
                }
            }
            catch(Exception e)
            {
                response.sendError(400);
                return;
            }

            if(id == 0)
            {
                response.sendError(400);
                return;
            }

            // File the report, and log the filing
            ReportFiler rf = new ReportFiler(response, report);
            if(rf.execute())
            {
                IPTracker.logReport(request, id);
                response.sendRedirect(DOMAIN + "/flag");
                return;
            }
        }
        else
        {
            response.sendError(401);
            return;
        }
    } // doPost()

} // ReportProcessor
