package com.xarql.chat.direct;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.util.JSPBuilder;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Conversations
 */
@WebServlet ("/chat/dm")
public class ConversationsDisplay extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String EMPTY_INBOX_MESSAGE = "Hello! Welcome to the direct message system on xarql.com, here you can send users (like me) messages. You've recieved this message to test out the interface. Click my username to start talking with me.";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConversationsDisplay()
    {
        super();
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
        JSPBuilder.build("/chat/direct-display", getServletContext());
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
            List<Conversation> convos = new RecentRetriever(util.getAccount().getUsername()).use();
            if(convos.size() == 0)
            {
                System.out.println("convos is empty");
                new MessageCreator(util.getAccount().getUsername(), "test", EMPTY_INBOX_MESSAGE).use();
                convos = new RecentRetriever(util.getAccount().getUsername()).use();
            }
            request.setAttribute("convos", convos);
            request.getRequestDispatcher("/src/chat/direct-display.jsp").forward(request, response);

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
