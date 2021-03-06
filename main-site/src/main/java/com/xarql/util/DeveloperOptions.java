package com.xarql.util;

import com.xarql.rsc.Secrets;

/**
 * Basic setup options to make development easier
 *
 * @author Bryan Johnson
 */
public class DeveloperOptions
{
    private static final String  DOMAIN  = "https://xarql.com";
    private static final String  CONTEXT = "";
    private static final boolean TESTING = false;
    /*
     * TESTING must equal true if you're trying to test something due to the
     * Recaptcha API's requirement for the Recaptcha key to be associated with the
     * domain name on which the Recaptcha appears. If you're not testing, make a
     * file called Secrets.java with two variables called RECAPTCHA_KEY and
     * RECAPTCHA_SECRET and put it in the com.xarql.util package.
     */

    private static final String TEST_RECAPTCHA_KEY    = "6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI";
    private static final String TEST_RECAPTCHA_SECRET = "6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe";

    // Assumed; may need to be changed
    private static final String TEST_DOMAIN  = "http://localhost:8080/main-site";
    private static final String TEST_CONTEXT = "/main-site";

    public static final String GOOGLE_ANALYTICS_ID = "UA-131023139-1";

    public static String getGoogleAnalyticsID()
    {
        if(TESTING)
            return "";
        else
            return GOOGLE_ANALYTICS_ID;
    }

    public static String getRecaptchaKey()
    {
        if(TESTING)
            return TEST_RECAPTCHA_KEY;
        else
            return Secrets.RECAPTCHA_KEY;
    }

    public static String getRecaptchaSecret()
    {
        if(TESTING)
            return TEST_RECAPTCHA_SECRET;
        else
            return Secrets.RECAPTCHA_SECRET;
    }

    public static String getDomain()
    {
        if(TESTING)
            return TEST_DOMAIN;
        else
            return DOMAIN;
    }

    public static String getContext()
    {
        if(TESTING)
            return TEST_CONTEXT;
        else
            return CONTEXT;
    }

    public static boolean getTesting()
    {
        return TESTING;
    }

}
