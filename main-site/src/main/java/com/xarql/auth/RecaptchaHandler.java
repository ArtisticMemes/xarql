/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.auth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xarql.main.DeveloperOptions;

/**
 * Servlet implementation class RecaptchaHandler
 */
@WebServlet ("/RecaptchaHandler")
public class RecaptchaHandler extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecaptchaHandler()
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
        response.sendRedirect(DOMAIN + "/auth");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String recaptcha;
        if(request.getParameter("data") != null)
            request.setAttribute("g-recaptcha-response", request.getParameter("data"));
        else if(request.getParameter("g-recaptcha-response") != null)
            request.setAttribute("g-recaptcha-response", request.getParameter("g-recaptcha-response"));
        else
            request.setAttribute("g-recaptcha-response", "");
        recaptcha = request.getAttribute("g-recaptcha-response").toString();

        String tomcatSession = request.getRequestedSessionId();

        new AuthSession(tomcatSession, recaptcha, "recaptcha");
        if(AuthTable.contains(tomcatSession))
        {
            response.setStatus(200);
            response.sendRedirect(DOMAIN + "/auth");
        }
        else
            response.sendError(400, "Recaptcha Invalid");
    }

}
