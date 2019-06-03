package net.xarql.util;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import com.xarql.util.ServletUtilities;

/**
 * ServletUtilities with appopriate variables for image-site
 *
 * @see com.xarql.util.ServletUtilities
 * @author Bryan Johnson
 */
public class NServletUtilities extends ServletUtilities
{
    private static final String  DOMAIN              = DeveloperOptions.getDomain();
    private static final String  GOOGLE_ANALYTICS_ID = DeveloperOptions.getGoogleAnalyticsID();
    private static final String  RECAPTCHA_KEY       = DeveloperOptions.getRecaptchaKey();
    private static final boolean TESTING             = DeveloperOptions.getTesting();

    public NServletUtilities(HttpServletRequest request) throws UnsupportedEncodingException
    {
        super(request);
        standardSetup();
    }

    @Override
    protected void standardSetup() throws UnsupportedEncodingException
    {
        super.standardSetup();
        getRequest().setAttribute("domain", DOMAIN);
        getRequest().setAttribute("google_analytics_id", GOOGLE_ANALYTICS_ID);
        getRequest().setAttribute("recaptcha_key", RECAPTCHA_KEY);
        getRequest().setAttribute("testing", TESTING);
    }
}
