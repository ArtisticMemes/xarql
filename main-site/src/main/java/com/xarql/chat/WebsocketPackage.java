package com.xarql.chat;

import java.sql.Timestamp;
import com.xarql.util.DeveloperOptions;
import com.xarql.util.TextFormatter;
import com.xarql.util.TrackedHashMap;

public class WebsocketPackage
{
    private static final String DOMAIN = DeveloperOptions.getDomain();

    // Data
    private TrackedHashMap<Headers, String> headers = new TrackedHashMap<>();
    private Client                          source;
    private String                          content;
    private Timestamp                       creationDate;

    public WebsocketPackage(String content, Client source)
    {
        setType();
        setSource(source);
        setContent(content);
        setCreationDate();
    }

    public WebsocketPackage(String content)
    {
        this(content, null);
    }

    public WebsocketPackage(Client source)
    {
        this(null, source);
    }

    protected void setHeader(Headers name, Object value) throws IllegalArgumentException
    {
        String insert = value.toString().replace(':', ';');
        if(insert.contains(",") || insert.contains("|"))
            throw new IllegalArgumentException("Parameter value has illegal characters " + insert);
        else
            headers.add(name, insert);
    }

    public String getHeader(Headers name)
    {
        return headers.get(name);
    }

    private void setContent(String content)
    {
        if(content == null)
            this.content = "";
        else
            this.content = TextFormatter.full(content.trim().replace("{DOMAIN}", DOMAIN));
    }

    public String getContent()
    {
        return content;
    }

    private void setCreationDate()
    {
        creationDate = new Timestamp(System.currentTimeMillis());
        setHeader(Headers.CREATION_DATE, creationDate);
    }

    public Timestamp getCreationDate()
    {
        return creationDate;
    }

    private void setSource(Client source)
    {
        if(source != null)
        {
            setHeader(Headers.CLIENT_COLOR, source.getColor());
            setHeader(Headers.TEXT_COLOR, textColor(source));
            this.source = source;
        }
    }

    public Client getSource()
    {
        return source;
    }

    private void setType()
    {
        setHeader(Headers.TYPE, determineType());
    }

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
    }

    private String determineType()
    {
        String type = super.getClass().getSimpleName();
        for(int i = 0; i < type.length(); i++)
            if(i != 0 && type.charAt(i) != type.toLowerCase().charAt(i))
            {
                type = type.substring(0, i) + "_" + type.substring(i);
                i++;
            }
        type = type.toLowerCase();
        return type;
    }

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
    }

}
