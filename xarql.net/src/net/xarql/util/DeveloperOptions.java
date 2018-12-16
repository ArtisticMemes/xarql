package net.xarql.util;

public class DeveloperOptions
{
    // Without get() methods
    public static final String  DOMAIN     = "http://xarql.net";
    public static final boolean TESTING    = false;
    public static final String  FILE_STORE = "/var/lib/tomcat8/webapps/file-store";
    // /var/lib/tomcat8/webapps/file-store

    // With get() methods
    private static final String GOOGLE_ANALYTICS_ID   = "UA-131023139-2";
    private static final String TEST_RECAPTCHA_KEY    = "6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI";
    private static final String TEST_RECAPTCHA_SECRET = "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe";

    public static String getGoogleAnalyticsID()
    {
        if(TESTING)
            return "";
        else
            return GOOGLE_ANALYTICS_ID;
    } // getGoogleAnalyticsID()

    public static String getRecaptchaKey()
    {
        if(TESTING)
            return TEST_RECAPTCHA_KEY;
        else
            return Secrets.RECAPTCHA_KEY;
    } // getRecaptchaKey()

    public static String getRecaptchaSecret()
    {
        if(TESTING)
            return TEST_RECAPTCHA_SECRET;
        else
            return Secrets.RECAPTCHA_SECRET;
    } // getRecaptchaSecret()
} // DeveloperOptions
