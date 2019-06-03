package net.xarql.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import com.xarql.util.Secrets;

/**
 * Basic setup options to make development easier
 *
 * @author Bryan Johnson
 */
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

    public static String getFileStore()
    {
        if(TESTING)
            return NetSecrets.LOCALIZED_FILE_STORE;
        else
            return FILE_STORE;
    }

    public static boolean getTesting()
    {
        return TESTING;
    }

    public static String[] getBase()
    {
        try
        {
            if(getDomain().equals(DOMAIN))
                return new String[]{ };
            else
            {
                String path = new URI(getDomain()).getPath();
                String[] output = path.split("/");
                if(output[0].equals(""))
                {
                    // Shift array to the left by 1
                    for(int i = 0; i < output.length - 1; i++)
                        output[i] = output[i + 1];
                    // Remove last index
                    output = Arrays.copyOf(output, output.length - 1);
                }
                return output;
            }
        }
        catch(URISyntaxException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
