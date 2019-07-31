package com.xarql.chat.direct;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.main.DeveloperOptions;
import com.xarql.user.AccountExistence;
import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class DMProcessor
 */
@WebServlet ("/chat/dm/send")
public class DMProcessor extends HttpServlet
{
    private static final long   serialVersionUID = 1L;
    private static final String DOMAIN           = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DMProcessor()
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
        ServletUtilities util = new ServletUtilities(request);
        if(util.userHasAccount())
        {
            if(util.hasParam("recipient") && util.hasParam("content"))
            {
                String recipient = util.useParam("recipient");
                if(AccountExistence.check(recipient))
                {
                    new MessageCreator(recipient, util.getAccount().getUsername(), util.useParam("content")).use();
                    response.sendRedirect(DOMAIN + "/chat/dm/view?user=" + recipient);
                }
                else
                    response.sendError(400);
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
