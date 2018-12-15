package net.xarql.util;

public class DeveloperOptions
{
    public static final String  DOMAIN              = "http://xarql.net";
    public static final boolean TESTING             = false;
    public static final String  GOOGLE_ANALYTICS_ID = "UA-131023139-2";

    public static String getGoogleAnalyticsID()
    {
        if(TESTING)
            return "";
        else
            return GOOGLE_ANALYTICS_ID;
    }
} // DeveloperOptions
