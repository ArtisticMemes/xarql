package net.xarql.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.auth.AuthTable;

/**
 * Copy of com.xarql.auth.AuthStatus
 *
 * @see com.xarql.auth.AuthStatus
 */
@WebServlet ("/AuthStatus")
public class AuthStatus extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    public AuthStatus()
    {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if(request.getRequestedSessionId() != null && AuthTable.contains(request.getRequestedSessionId()))
            response.setStatus(200);
        else
            response.sendError(401);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }

}
