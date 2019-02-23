package com.xarql.chat;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.xarql.main.DeveloperOptions;
import com.xarql.util.TextFormatter;
import com.xarql.util.TrackedHashMap;

public class WebsocketPackage
{
    // Standard Headers
    public static final String            TYPE             = "type";
    public static final String            CLIENT_COLOR     = "client-color";
    public static final String            CREATION_DATE    = "creation-date";
    public static final String            TEXT_COLOR       = "text-color";
    public static final ArrayList<String> VALID_PARAMETERS = generateValidParameters();

    private static final String DOMAIN = DeveloperOptions.getDomain();

    // Data
    private TrackedHashMap<String, String> headers = new TrackedHashMap<String, String>();
    private Client                         source;
    private String                         content;
    private Timestamp                      creationDate;

    public WebsocketPackage(String content, Client source)
    {
        setHeader(TYPE, determineType());
        setSource(source);
        setContent(content);
        setCreationDate();
    } // WebsocketPackage(String, Client)

    public WebsocketPackage(String content)
    {
        this(content, null);
    } // Websocket(String)

    public WebsocketPackage(Client source)
    {
        this(null, source);
    } // WebsocketPackage(Client)

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
        if(content == null)
            this.content = "";
        else
            this.content = TextFormatter.full(content.trim().replace("{DOMAIN}", DOMAIN));
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

    private void setSource(Client source)
    {
        if(source != null)
        {
            setHeader(CLIENT_COLOR, source.getColor());
            setHeader(TEXT_COLOR, textColor(source));
            this.source = source;
        }
    } // setSource()

    public Client getSource()
    {
        return source;
    } // getSource()

    private static String textColor(Client client)
    {
        int r = Integer.parseInt(client.getColor().substring(0, 2), 16);
        int g = Integer.parseInt(client.getColor().substring(2, 4), 16);
        int b = Integer.parseInt(client.getColor().substring(4, 6), 16);
        double luma = 0.2126 * r + 0.7152 * g + 0.0722 * b; // Adjust for human eyes
        if(luma > 80)
            return "000";
        else
            return "FFF";
    } // textColor()

    private String determineType()
    {
        String type = super.getClass().getSimpleName();
        for(int i = 0; i < type.length(); i++)
        {
            if(i != 0 && type.charAt(i) != type.toLowerCase().charAt(i))
            {
                type = type.substring(0, i) + "-" + type.substring(i);
                i++;
            }
        }
        type = type.toLowerCase();
        return type;
    } // determineType()

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

        output.add(TYPE);
        output.add(CLIENT_COLOR);
        output.add(CREATION_DATE);
        output.add(TEXT_COLOR);
        output.add(TypingStatus.TYPING);
        output.add(BufferStatus.BUFFER);

        for(String names : output)
            if(!TextFormatter.isAlphaNumeric(names))
                throw new IllegalStateException("A const has non alpha numeric characters");
        return output;
    } // generateValidParameters()

} // WebsocketPackage
