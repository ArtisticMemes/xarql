package com.xarql.chat;

import java.sql.Timestamp;

import com.xarql.main.DeveloperOptions;
import com.xarql.util.TextFormatter;

public class Message extends WebsocketPackage
{
    // Headers used
    private static final String CLIENT_NAME = WebsocketPackage.CLIENT_NAME;
    private static final String TEXT_COLOR  = WebsocketPackage.TEXT_COLOR;

    private static final String DOMAIN = DeveloperOptions.getDomain();
    // half of an hour
    private static final long MESSAGE_LIFESPAN = 3600000;

    private Client client;

    public Message(String content, Client client) throws IllegalArgumentException
    {
        super(true, TextFormatter.full(content).replace("{DOMAIN}", DOMAIN));
        setClient(client);
        setHeader(CLIENT_NAME, mainColor());
        setHeader(TEXT_COLOR, textColor());
    } // Message()

    private void setClient(Client client)
    {
        this.client = client;
    } // setClient()

    private String mainColor()
    {
        return client.getColor();
    } // getColor()

    private String textColor()
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

    public boolean isExpired()
    {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(getCreationDate().compareTo(now) + MESSAGE_LIFESPAN < 0)
            return true;
        else
            return false;
    } // isExpired()

} // Message
