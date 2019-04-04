/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */

package com.xarql.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xarql.auth.AuthSession;
import com.xarql.auth.AuthTable;
import com.xarql.main.DeveloperOptions;
import com.xarql.polr.Post;
import com.xarql.user.Account;

/**
 * Helper class for servlets.
 * 
 * @author Bryan Johnson
 */
public class ServletUtilities
{
    private static final String  DOMAIN              = DeveloperOptions.getDomain();
    private static final String  GOOGLE_ANALYTICS_ID = DeveloperOptions.getGoogleAnalyticsID();
    private static final String  RECAPTCHA_KEY       = DeveloperOptions.getRecaptchaKey();
    private static final boolean TESTING             = DeveloperOptions.getTesting();

    private HttpServletRequest request;

    private static final int    NORMAL_FONT_WEIGHT = 400;
    private static final int    LIGHT_FONT_WEIGHT  = 200;
    private static final String DEFAULT_FONT_SIZE  = "1rem";
    private static final String DEFAULT_THEME      = "light";

    private static final int     DEFAULT_INT     = 0;
    private static final boolean DEFAULT_BOOLEAN = false;

    /**
     * Allows for using static methods in an object to reduce typing
     * 
     * @param request The request to send to static methods
     */
    public ServletUtilities(HttpServletRequest request) throws UnsupportedEncodingException
    {
        this.request = request;
        standardSetup();
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

    public String useParam(String param, String fallback)
    {
        if(hasParam(param))
            return useParam(param);
        else
        {
            request.setAttribute(param, fallback);
            return fallback;
        }
    } // useParam()

    public int useInt(String name)
    {
        return useInt(name, DEFAULT_INT);
    } // useInt()

    public int useInt(String name, int fallback)
    {
        int output;
        try
        {
            output = Integer.parseInt(request.getParameter(name));
        }
        catch(NumberFormatException | NullPointerException e)
        {
            output = fallback;
        }
        request.setAttribute(name, output);
        return output;
    } // useInt()

    public boolean useBoolean(String name)
    {
        return useBoolean(name, DEFAULT_BOOLEAN);
    } // useBoolean()

    public boolean useBoolean(String name, boolean fallback)
    {
        boolean output;
        if(hasParam(name))
            output = Boolean.parseBoolean(request.getParameter(name));
        else
            output = fallback;
        request.setAttribute(name, output);
        return output;
    } // useBoolean()

    /**
     * Sets several attributes to their universal defaults, others to cookies and
     * others to session details. Sets the Character Encoding to UTF-8.
     * 
     * @param request The request which requires a standard set up.
     * @throws UnsupportedEncodingException Shouldn't happen. Occurs if the server
     *         or client can't use UTF-8. Although that's extremely rare.
     */
    private void standardSetup() throws UnsupportedEncodingException
    {
        request.setAttribute("domain", DOMAIN);
        request.setAttribute("google_analytics_id", GOOGLE_ANALYTICS_ID);
        request.setAttribute("recaptcha_key", RECAPTCHA_KEY);
        request.setAttribute("auth", userIsAuth());
        setTheme();
        setFontWeight();
        setFontSize();
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("testing", TESTING);
        if(userHasAccount())
            request.setAttribute("account_name", getAccount().getUsername());
        else
            request.setAttribute("account_name", Post.DEFAULT_AUTHOR);
    } // standardSetup(request)

    private void setFontWeight()
    {
        // TODO: make this use setAttributeByCookie()
        // setAttributeByCookie(request, "font-weight", NORMAL_FONT_WEIGHT);
        int fontWeight = 0;
        Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
            for(Cookie item : cookies)
            {
                if(item.getName().equals("font-weight"))
                {
                    if(item.getValue().equals("normal"))
                        fontWeight = NORMAL_FONT_WEIGHT;
                    else if(item.getValue().equals("light"))
                        fontWeight = LIGHT_FONT_WEIGHT;
                    request.setAttribute("font_weight", fontWeight);
                    return;
                }
            }
            request.setAttribute("font_weight", NORMAL_FONT_WEIGHT); // default
        }
        else
            request.setAttribute("font_weight", NORMAL_FONT_WEIGHT);
    } // setFontWeight

    public void setFontSize()
    {
        setAttributeByCookie("font-size", DEFAULT_FONT_SIZE);
    } // setFontSize()

    public void setAttributeByCookie(String name, Object fallback)
    {
        String insertableName = name.replace('-', '_');
        // Sort through all of the cookies
        Cookie[] cookies = request.getCookies();
        if(cookies != null)
        {
            for(Cookie item : cookies)
            {
                if(item.getName().equals(name))
                {
                    request.setAttribute(insertableName, item.getValue());
                    return;
                }
            }
            request.setAttribute(insertableName, fallback); // default
        }
        else
            request.setAttribute(insertableName, fallback);
    } // setAttributeByCookie()

    /**
     * Determines if the user that made a request is a moderator. Checks the
     * AuthTable for the Tomcat session ID cookie's status.
     * 
     * @param request The request from the user.
     * @return <code>true</code> if the user is a moderator, <code>false</code>
     *         otherwise.
     */
    public boolean userIsMod()
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
    public boolean userIsAuth()
    {
        if(request.getRequestedSessionId() == null) // NullPointerException protection
            return false;
        else if(AuthTable.contains(request.getRequestedSessionId()))
            return true;
        else
            return false;
    } // userIsAuth()

    public AuthSession getAuthSession()
    {
        if(request.getRequestedSessionId() == null) // NullPointerException protection
            return null;
        else if(AuthTable.contains(request.getRequestedSessionId()))
            return AuthTable.get(request.getRequestedSessionId());
        else
            return null;
    } // getAuthSession()

    public boolean userHasAccount()
    {
        if(request.getRequestedSessionId() == null) // NullPointerException protection
            return false;
        else if(AuthTable.get(request.getRequestedSessionId()) != null && AuthTable.get(request.getRequestedSessionId()).getAccount() != null)
            return true;
        else
            return false;
    } // userHasAccount(HttpServletRequest)

    public Account getAccount()
    {
        return AuthTable.get(request.getRequestedSessionId()).getAccount();
    } // getAccount(HttpServletRequest)

    /**
     * Adds a "theme" attribute to the request. This is used to chose a style sheet
     * in a .jsp file.
     * 
     * @param request The request which needs a theme to be applied.
     */
    public void setTheme()
    {
        setAttributeByCookie("theme", DEFAULT_THEME);
    } // setTheme()

    /**
     * Used to prevent get methods on endpoints meant for submitting content
     * 
     * @param response Response to use for the error
     * @throws IOException Something went wrong with http
     */
    public static void rejectGetMethod(HttpServletResponse response) throws IOException
    {
        response.sendError(405, "Can't use HTTP GET method for this URI");
    } // rejectGetMethod()

    public static void rejectPostMethod(HttpServletResponse response) throws IOException
    {
        response.sendError(405, "Can't use HTTP POST method for this URI");
    } // rejectPostMethod()

    /**
     * Checks to see if all of the given parameters are not null and not empty
     * 
     * @param parameters The parameters from the client, often represented in the
     *        URL
     * @param request The request the parameters are in
     * @param response Gives a 400 error
     * @return true if the parameters are usable, false otherwise
     * @throws IOException Something went wrong with http
     */
    public boolean hasParams(List<String> parameters)
    {
        for(String param : parameters)
        {
            if(request.getParameter(param) == null || request.getParameter(param).equals(""))
            {
                return false;
            }
        }
        return true;
    } // hasParams()

    public boolean hasParams(String[] parameters)
    {
        List<String> tmp = new ArrayList<String>();
        for(String param : parameters)
            tmp.add(param);
        return hasParams(tmp);
    } // hasParams()

    /**
     * Checks to see if the given parameter is not null and not empty
     * 
     * @param parameter The parameter from the client, often represented in the URL
     * @param request The request the parameter is in
     * @return true if the parameter is usable, false otherwise
     */
    public boolean hasParam(String parameter)
    {
        return !(request.getParameter(parameter) == null || request.getParameter(parameter).equals(""));
    } // hasParam()

    public int requireInt(String parameter) throws NoSuchElementException
    {
        if(hasParam(parameter))
        {
            try
            {
                return Integer.parseInt(request.getAttribute(parameter).toString());
            }
            catch(NumberFormatException nfe)
            {
                throw new NoSuchElementException("The desired parameter was not an int");
            }
        }
        else
            throw new NoSuchElementException("The request didn't include that parameter");
    } // requireInt()

} // ServletUtilities
