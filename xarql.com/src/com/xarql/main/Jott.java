package com.xarql.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.util.ServletUtilities;
import com.xarql.util.TextFormatter;

/**
 * Servlet implementation class Jott
 */
@WebServlet ("/Jott")
public class Jott extends HttpServlet
{
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Jott()
    {
        super();
    } // Jott()

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ServletUtilities util = new ServletUtilities(request);
        util.standardSetup();

        try
        {
            String input = request.getParameter("content");
            request.setAttribute("input", input);
            request.setAttribute("output", TextFormatter.full(input));
        }
        catch(NullPointerException npe)
        {
            request.setAttribute("input", "");
            request.setAttribute("output", "");
        }

        request.getRequestDispatcher("/src/jott/jott.jsp").forward(request, response);
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

} // Jott
