package com.xarql.polr.edit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.auth.AuthTable;
import com.xarql.util.Secrets;

/**
 * Servlet implementation class PolrEdit
 */
@WebServlet ("/PolrEdit")
public class PolrEdit extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PolrEdit()
    {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String tomcatSession = request.getRequestedSessionId();
        if(tomcatSession != null)
        {
            String associatedGoogleID;
            try
            {
                associatedGoogleID = AuthTable.get(tomcatSession).getGoogleId();
            }
            catch(Exception e)
            {
                response.sendError(401);
                return;
            }
            if(Secrets.modList().contains(associatedGoogleID))
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
        else
        {
            response.sendError(401);
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
        String tomcatSession = request.getRequestedSessionId();
        if(tomcatSession != null)
        {
            String associatedGoogleID;
            try
            {
                associatedGoogleID = AuthTable.get(tomcatSession).getGoogleId();
            }
            catch(Exception e)
            {
                response.sendError(401);
                return;
            }
            if(Secrets.modList().contains(associatedGoogleID))
            {
                String type = request.getParameter("type");
                if(type == null || !(type.equals("remove") || type.equals("restore")))
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
                if(id == 0)
                {
                    response.sendError(403);
                    return;
                }

                if(type.equals("remove"))
                {
                    PostRemover pr = new PostRemover(id, response);
                    if(pr.execute())
                        response.sendRedirect("http://xarql.com/polr/edit");
                    return;
                }
                else if(type.equals("restore"))
                {
                    PostRestorer pr = new PostRestorer(id, response);
                    if(pr.execute())
                        response.sendRedirect("http://xarql.com/polr/edit");
                    return;
                }
                else
                    return;
            }
        }
        else
        {
            response.sendError(401);
            return;
        }
    } // doPost()

} // PolrEdit
