/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */

package com.xarql.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.auth.AuthTable;
import com.xarql.main.DeveloperOptions;

/**
 * Helper class for servlets.
 * 
 * @author Bryan Johnson
 */
public class ServletUtilities
{
    private static final String DOMAIN        = DeveloperOptions.DOMAIN;
    private static final String RECAPTCHA_KEY = DeveloperOptions.getRecaptchaKey();

    private HttpServletRequest request;

    /**
     * Allows for using static methods in an object to reduce typing
     * 
     * @param request The request to send to static methods
     */
    public ServletUtilities(HttpServletRequest request)
    {
        this.request = request;
    } // ServletUtilities()

    /**
     * Tries to get a String from a parameter and add the parameter to the request
     * as an attribute
     * 
     * @param param The parameter from the user
     * @return The parameter's String
     */
    public String useParam(String param)
    {
        request.setAttribute(param, request.getParameter(param));
        return request.getParameter(param);
    } // useParam()

    /**
     * Object based version of standardSetup()
     * 
     * @throws UnsupportedEncodingException
     */
    public void standardSetup() throws UnsupportedEncodingException
    {
        request.setAttribute("domain", DOMAIN);
        request.setAttribute("recaptcha_key", RECAPTCHA_KEY);
        request.setAttribute("auth", userIsAuth(request));
        setTheme(request);
        request.setCharacterEncoding("UTF-8");
    } // standardSetup()

    /**
     * Sets the "domain", "recaptcha_key", and "theme" attributes. Sets the
     * Character Encoding to UTF-8
     * 
     * @param request The request which requires a standard set up.
     * @throws UnsupportedEncodingException Shouldn't happen. Occurs if the server
     *         or client can't use UTF-8. Although that's extremely rare.
     */
    public static void standardSetup(HttpServletRequest request) throws UnsupportedEncodingException
    {
        request.setAttribute("domain", DOMAIN);
        request.setAttribute("recaptcha_key", RECAPTCHA_KEY);
        request.setAttribute("auth", userIsAuth(request));
        setTheme(request);
        request.setCharacterEncoding("UTF-8");
    } // standardSetup(request)

    /**
     * Determines if the user that made a request is a moderator. Checks the
     * AuthTable for the Tomcat session ID cookie's status.
     * 
     * @param request The request from the user.
     * @return <code>true</code> if the user is a moderator, <code>false</code>
     *         otherwise.
     */
    public static boolean userIsMod(HttpServletRequest request)
    {
        if(AuthTable.get(request.getRequestedSessionId()) == null)
            return false;
        else if(AuthTable.get(request.getRequestedSessionId()).isMod())
            return true;
        else
            return false;
    } // userIsMod()

    /**
     * Determines if the user that made a request is an authorized user believed to
     * be human. Checks the AuthTable for the Tomcat session ID cookie's status.
     * 
     * @param request The request from the user.
     * @return <code>true</code> if the user is authorized, <code>false</code>
     *         otherwise.
     */
    public static boolean userIsAuth(HttpServletRequest request)
    {
        if(request.getRequestedSessionId() == null) // NullPointerException protection
            return false;
        else if(AuthTable.contains(request.getRequestedSessionId()))
            return true;
        else
            return false;
    } // userIsAuth()

    /**
     * Adds a "theme" attribute to the request. This is used to chose a style sheet
     * in a .jsp file.
     * 
     * @param request The request which needs a theme to be applied.
     */
    public static void setTheme(HttpServletRequest request)
    {
        // Get theme
        Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
            for(Cookie item : cookies)
            {
                if(item.getName().equals("theme"))
                {
                    request.setAttribute("theme", item.getValue());
                    return;
                }
            }
            request.setAttribute("theme", "light"); // default
        }
        else
            request.setAttribute("theme", "light");
    } // setTheme()

    /**
     * Used to prevent get methods on endpoints meant for submitting content
     * 
     * @param response Response to use for the error
     * @throws IOException
     */
    public static void rejectGetMethod(HttpServletResponse response) throws IOException
    {
        response.sendError(400, "Can't use HTTP GET method for this URI");
    } // rejectGetMethod()

    /**
     * Checks to see if the given parameters are not null and not empty
     * 
     * @param parameters The parameters from the client, often represented in the
     *        URL
     * @param request The request the parameters are in
     * @param response Gives a 400 error
     * @return true if the parameters are usable, false otherwise
     * @throws IOException
     */
    public static boolean hasParameters(String[] parameters, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        for(String param : parameters)
        {
            if(request.getParameter(param) == null || request.getParameter(param).equals(""))
            {
                response.sendError(400);
                return false;
            }
        }
        return true;
    } // hasParameters()

    /**
     * Checks to see if the given parameter is not null and not empty
     * 
     * @param parameter The parameter from the client, often represented in the URL
     * @param request The request the parameter is in
     * @return true if the parameter is usable, false otherwise
     */
    public static boolean hasParameter(String parameter, HttpServletRequest request)
    {
        return !(request.getParameter(parameter) == null || request.getParameter(parameter).equals(""));
    } // hasParamter()

} // ServletUtilities
