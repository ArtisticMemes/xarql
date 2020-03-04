package com.xarql.polr.edit;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.auth.IPTracker;
import com.xarql.util.DeveloperOptions;
import com.xarql.util.JSPBuilder;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class PolrEdit
 */
@WebServlet ("/polr/edit")
public class PolrEdit extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolrEdit()
    {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/polr/edit", getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        if(util.userIsMod())
        {
            request.getRequestDispatcher("/src/polr/edit.jsp").forward(request, response);
            return;
        }
        else
        {
            response.sendError(401);
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
        ServletUtilities util = new ServletUtilities(request);
        if(util.userIsMod())
        {
            String type = request.getParameter("type");
            if(type == null || !(type.equals("remove") || type.equals("restore") || type.equals("replace") || type.equals("censor")))
            {
                response.sendError(400);
                return;
            }

            int id;
            try
            {
                id = Integer.parseInt(request.getParameter("id"));
            }
            catch(NumberFormatException nfe)
            {
                response.sendError(400);
                return;
            }
            if(id == 0 && !type.equals("replace"))
            {
                response.sendError(403);
                return;
            }

            if(type.equals("remove"))
            {
                PostRemover pr = new PostRemover(id, response);
                if(pr.execute())
                {
                    IPTracker.logPolrEditRemove(request, id);
                    response.sendRedirect(DOMAIN + "/polr/edit");
                }
                return;
            }
            else if(type.equals("restore"))
            {
                PostRestorer pr = new PostRestorer(id, response);
                if(pr.execute())
                {
                    IPTracker.logPolrEditRestore(request, id);
                    response.sendRedirect(DOMAIN + "/polr/edit");
                }
                return;
            }
            else if(type.equals("replace"))
            {
                if(request.getParameter("content") == null || request.getParameter("content").equals("") || request.getParameter("title") == null || request.getParameter("title").equals(""))
                {
                    response.sendError(400);
                    return;
                }
                else
                {
                    PostEditor pe = new PostEditor(id, request.getParameter("title"), request.getParameter("content"), response);
                    if(pe.execute())
                    {
                        IPTracker.logPolrEditReplace(request, id);
                        response.sendRedirect(DOMAIN + "/polr/edit");
                    }
                    return;
                }
            }
            else if(type.equals("censor"))
                if(request.getParameter("warning") == null || request.getParameter("warning").equals(""))
                {
                    response.sendError(400);
                    return;
                }
                else
                {
                    PostCensor pc = new PostCensor(id, request.getParameter("warning"));
                    if(pc.execute())
                    {
                        IPTracker.logPolrEditCensor(request, id);
                        response.sendRedirect(DOMAIN + "/polr/edit");
                    }
                    return;
                }
        }
        else
        {
            response.sendError(401);
            return;
        }
    }

}
