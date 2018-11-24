package com.xarql.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.xarql.auth.AuthTable;
import com.xarql.main.DeveloperOptions;

public class ServletUtilities
{
    private static final String DOMAIN        = DeveloperOptions.DOMAIN;
    private static final String RECAPTCHA_KEY = DeveloperOptions.getRecaptchaKey();

    public static void standardSetup(HttpServletRequest request)
    {
        request.setAttribute("domain", DOMAIN);
        request.setAttribute("recaptcha_key", RECAPTCHA_KEY);
        setTheme(request);
    } // standardSetup()

    public static boolean userIsMod(HttpServletRequest request)
    {
        if(AuthTable.get(request.getRequestedSessionId()).isMod())
            return true;
        else
            return false;
    } // userIsMod()

    public static boolean userIsAuth(HttpServletRequest request)
    {
        if(request.getRequestedSessionId() == null) // NullPointerException protection
            return false;
        else if(AuthTable.contains(request.getRequestedSessionId()))
            return true;
        else
            return false;
    } // userIsAuth()

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
