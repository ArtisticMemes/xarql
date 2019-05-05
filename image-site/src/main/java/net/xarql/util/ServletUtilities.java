package net.xarql.util;

import javax.servlet.http.HttpServletRequest;

import net.xarql.auth.AuthTable;

public class ServletUtilities
{
    private static final String DOMAIN              = DeveloperOptions.getDomain();
    private static final String GOOGLE_ANALYTICS_ID = DeveloperOptions.getGoogleAnalyticsID();
    private static final String RECAPTCHA_KEY       = DeveloperOptions.getRecaptchaKey();

    private final HttpServletRequest request;

    public ServletUtilities(HttpServletRequest request)
    {
        this.request = request;
    } // ServletUtilities()

    public void standardSetup()
    {
        standardSetup(request);
    } // standardSetup()

    public static void standardSetup(HttpServletRequest request)
    {
        request.setAttribute("domain", DOMAIN);
        request.setAttribute("google_analytics_id", GOOGLE_ANALYTICS_ID);
        request.setAttribute("recaptcha_key", RECAPTCHA_KEY);
        request.setAttribute("authenticated", isAuth(request));
    } // standardSetup()

    public boolean isAuth()
    {
        return isAuth(request);
    } // isAuth()

    public static boolean isAuth(HttpServletRequest request)
    {
        String tomcatID = request.getRequestedSessionId();
        if(tomcatID == null || tomcatID.equals(""))
            return false;
        else if(AuthTable.contains(tomcatID))
            return true;
        else
            return false;
    } // isAuth()

    public void revokeAuth()
    {
        revokeAuth(request);
    } // revokeAuth()

    public static void revokeAuth(HttpServletRequest request)
    {
        String tomcatID = request.getRequestedSessionId();
        if(tomcatID != null && AuthTable.contains(tomcatID))
            AuthTable.remove(tomcatID);
    } // revokeAuth()

} // ServletUtilities
