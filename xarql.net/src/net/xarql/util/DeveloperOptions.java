package net.xarql.util;

public class DeveloperOptions
{
    public static final String  DOMAIN              = "http://xarql.net";
    public static final boolean TESTING             = false;
    public static final String  GOOGLE_ANALYTICS_ID = "UA-131023139-2";
    public static final String  FILE_STORE          = "/var/lib/tomcat8/webapps/file-store";

    // /var/lib/tomcat8/webapps/file-store

    public static String getGoogleAnalyticsID()
    {
        if(TESTING)
            return "";
        else
            return GOOGLE_ANALYTICS_ID;
    }
} // DeveloperOptions
