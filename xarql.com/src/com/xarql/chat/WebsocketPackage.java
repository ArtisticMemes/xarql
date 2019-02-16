package com.xarql.chat;

import java.util.ArrayList;

import com.xarql.util.TextFormatter;
import com.xarql.util.TrackedHashMap;

public class WebsocketPackage
{
    // Standard Headers
    private static final String            DIRECT_DISPLAY   = "direct-display";
    private static final ArrayList<String> VALID_PARAMETERS = generateValidParameters();

    // Data
    private TrackedHashMap<String, String> headers = new TrackedHashMap<String, String>();
    private String                         content;

    public WebsocketPackage(boolean directDisplay, String content) throws IllegalArgumentException
    {
        setHeader(DIRECT_DISPLAY, directDisplay);
        setContent(content);
    } // WebsocketPackage

    private void setHeader(String name, boolean value)
    {
        setHeader(name, String.valueOf(value));
    } // setHeader(String, boolean)

    private void setHeader(String name, Object value) throws IllegalArgumentException
    {
        if(VALID_PARAMETERS.contains(name))
        {
            if(TextFormatter.isAlphaNumeric(value.toString()))
                headers.add(name, value.toString());
            else
                throw new IllegalArgumentException("Parameter value has non alpha numeric characters");
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
        this.content = content;
    } // setContent()

    public String getContent()
    {
        return content;
    } // getContent()

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

        for(String names : output)
            if(!TextFormatter.isAlphaNumeric(names))
                throw new IllegalStateException("A const has non alpha numeric characters");
        return output;
    } // generateValidParameters()

} // WebsocketPackage
