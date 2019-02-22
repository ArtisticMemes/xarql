package com.xarql.chat;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.xarql.util.TextFormatter;
import com.xarql.util.TrackedHashMap;

public class WebsocketPackage
{
    // Standard Headers
    public static final String            DIRECT_DISPLAY   = "direct-display";
    public static final String            CLIENT_NAME      = "client-name";
    public static final String            CREATION_DATE    = "creation-date";
    public static final String            TEXT_COLOR       = "text-color";
    public static final String            TYPING           = "typing";
    public static final ArrayList<String> VALID_PARAMETERS = generateValidParameters();

    // Data
    private TrackedHashMap<String, String> headers = new TrackedHashMap<String, String>();
    private String                         content;
    private Timestamp                      creationDate;

    public WebsocketPackage(boolean directDisplay, String content) throws IllegalArgumentException
    {
        setHeader(DIRECT_DISPLAY, directDisplay);
        setContent(content);
        setCreationDate();
    } // WebsocketPackage

    protected void setHeader(String name, Object value) throws IllegalArgumentException
    {
        if(VALID_PARAMETERS.contains(name))
        {
            String insert = value.toString().replace(':', ';');
            if(insert.contains(",") || insert.contains("|"))
                throw new IllegalArgumentException("Parameter value has illegal characters " + insert);
            else
                headers.add(name, insert);
        }
        else
            throw new IllegalArgumentException("Invalid parameter name. Please use a const and not a magic string.");
    } // setHeader()

    public String getHeader(String name)
    {
        return headers.get(name);
    } // getHeader()

    private void setContent(String content)
    {
        this.content = content.trim();
    } // setContent()

    public String getContent()
    {
        return content;
    } // getContent()

    private void setCreationDate()
    {
        creationDate = new Timestamp(System.currentTimeMillis());
        setHeader(CREATION_DATE, creationDate);
    } // setCreationDate()

    public Timestamp getCreationDate()
    {
        return creationDate;
    } // getCreationDate()

    @Override
    public String toString()
    {
        String output = "";
        for(int i = 0; i < headers.size(); i++)
        {
            if(i != 0)
                output += ",";
            output += headers.key(i);
            output += ":";
            output += headers.get(i);
        }
        output += "|";
        output += content;
        return output;
    } // toString()

    private static ArrayList<String> generateValidParameters()
    {
        ArrayList<String> output = new ArrayList<String>(1);

        output.add(DIRECT_DISPLAY);
        output.add(CLIENT_NAME);
        output.add(CREATION_DATE);
        output.add(TEXT_COLOR);
        output.add(TYPING);

        for(String names : output)
            if(!TextFormatter.isAlphaNumeric(names))
                throw new IllegalStateException("A const has non alpha numeric characters");
        return output;
    } // generateValidParameters()

} // WebsocketPackage
