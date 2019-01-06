/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.util.ServletUtilities;

/**
 * Servlet implementation class Error
 */
@WebServlet ("/Error")
public class Error extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    HttpServletRequest  currentRequest  = null;
    HttpServletResponse currentResponse = null;

    public static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Error()
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

        currentRequest = request;
        currentResponse = response;
        request.setAttribute("code", request.getParameter("code"));
        if(attributeEmpty("code"))
        {
            request.setAttribute("code", "???");
        }
        String code = request.getAttribute("code").toString();
        String type;
        switch(code)
        {
            case "429" :
                type = "Spam";
                break;
            case "410" :
                type = "Gone";
                break;
            case "404" :
                type = "Not Found";
                break;
            case "403" :
                type = "Forbidden";
                break;
            case "401" :
                type = "Unauthenticated";
                break;
            case "400" :
                type = "Bad Request";
                break;
            default :
                type = "Unknown";
        }
        request.setAttribute("type", type);
        request.getRequestDispatcher("/src/error/error.jsp").forward(request, response);
    }

    // Check if the attribute has content
    private boolean attributeEmpty(String name)
    {
        if(currentRequest.getAttribute(name) == null || currentRequest.getAttribute(name).toString().equals(""))
            return true;
        else
            return false;
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
