/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.auth;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;
import com.xarql.util.DeveloperOptions;

public class VerifyRecaptcha
{
    private static final String SECRET = DeveloperOptions.getRecaptchaSecret();

    public static boolean verify(String response)
    {
        try
        {
            /*
             * fix for Exception in thread "main" javax.net.ssl.SSLHandshakeException:
             * sun.security.validator.ValidatorException: PKIX path building failed:
             * sun.security.provider.certpath.SunCertPathBuilderException: unable to find
             * valid certification path to requested target
             */
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager()
                    {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers()
                        {
                            return null;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType)
                        {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType)
                        {
                        }

                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier()
            {
                @Override
                public boolean verify(String hostname, SSLSession session)
                {
                    return true;
                }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            /*
             * end of the fix
             */

            String url = "https://www.google.com/recaptcha/api/siteverify?" + "secret=" + SECRET + "&response=" + response;
            InputStream res = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));

            StringBuilder sb = new StringBuilder();
            int cp;
            while((cp = rd.read()) != -1)
            {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            res.close();

            JSONObject json = new JSONObject(jsonText);
            return json.getBoolean("success");
        }
        catch(Exception e)
        {
            return false;
        }
    } //
} // *
