package com.xarql.chat.direct;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.util.JSPBuilder;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class DMViewer
 */
@WebServlet ("/chat/dm/view")
public class DMViewer extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DMViewer()
    {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/chat/dm-view", getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        if(util.userHasAccount())
        {
            if(util.hasParam("user"))
            {
                String sender = util.useParam("user");
                Conversation convo = new MessageRetriever(util.getAccount().getUsername(), sender).use();
                request.setAttribute("convo", convo);
                if(convo == null || convo.isEmpty())
                    response.sendError(204);
                else
                    request.getRequestDispatcher("/src/chat/dm-view.jsp").forward(request, response);
            }
            else
                response.sendError(400);
        }
        else
            response.sendError(401);
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
