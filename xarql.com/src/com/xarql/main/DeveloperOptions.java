package com.xarql.main;

import com.xarql.util.Secrets;

public class DeveloperOptions
{
    public static final String   DOMAIN  = "//xarql.com";
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
