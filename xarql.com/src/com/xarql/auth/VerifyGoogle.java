/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.webtoken.JsonWebToken.Payload;

public class VerifyGoogle
{

    public static final String APP_CLIENT_ID = "541616841401-iqluj8gqbu0qvsn3kh7bl93e9mskoff3.apps.googleusercontent.com";
    // public static JacksonFactory jsonFactory = new JacksonFactory();
    public static ApacheHttpTransport transport   = new ApacheHttpTransport();
    public static JacksonFactory      jsonFactory = new JacksonFactory();

    public static String verify(String googleIdToken) throws GeneralSecurityException
    {
        try
        {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(APP_CLIENT_ID)).build();

            // (Receive idTokenString by HTTPS POST)

            GoogleIdToken idToken = verifier.verify(googleIdToken);
            if(idToken != null)
            {
                Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();

                // Get profile information from payload
                /*
                 * String email = payload.getEmail(); boolean emailVerified =
                 * Boolean.valueOf(payload.getEmailVerified()); String name = (String)
                 * payload.get("name"); String pictureUrl = (String) payload.get("picture");
                 * String locale = (String) payload.get("locale"); String familyName = (String)
                 * payload.get("family_name"); String givenName = (String)
                 * payload.get("given_name");
                 */

                return userId;

            }
            else
            {
                throw new GeneralSecurityException("Invalid Google ID Token");
            }
        }
        catch(IOException e)
        {
            throw new GeneralSecurityException("An IOException occured that prevented proper authentication");
        }
    } // public static String verify(String googleIdToken) throws
      // IllegalArgumentException

} // VerifyGoogle
