package net.xarql.util;

public class DeveloperOptions
{
    public static final boolean TESTING = true;

    // Paths
    private static final String DOMAIN      = "https://xarql.net";
    private static final String TEST_DOMAIN = "http://localhost:8080/image-site";
    private static final String FILE_STORE  = "/var/lib/tomcat8/webapps/file-store";
    // Data
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

    public static String getDomain()
    {
        if(TESTING)
            return TEST_DOMAIN;
        else
            return DOMAIN;
    } // getDomain()

    public static String getFileStore()
    {
        if(TESTING)
            return Secrets.LOCALIZED_FILE_STORE;
        else
            return FILE_STORE;
    } // getFileStore()

} // DeveloperOptions
