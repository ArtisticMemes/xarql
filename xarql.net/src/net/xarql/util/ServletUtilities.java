package net.xarql.util;

import javax.servlet.http.HttpServletRequest;

public class ServletUtilities
{
    private static final String DOMAIN              = DeveloperOptions.DOMAIN;
    private static final String GOOGLE_ANALYTICS_ID = DeveloperOptions.getGoogleAnalyticsID();

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
    } // standardSetup()

} // ServletUtilities
