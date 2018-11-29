package com.xarql.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
        setTheme(request);
        request.setCharacterEncoding("UTF-8");
    } // standardSetup()

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
        if(AuthTable.get(request.getRequestedSessionId()).isMod())
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

} // ServletUtilities
