package com.xarql.chat;

import com.xarql.util.TrackedHashMap;

public class UsersReport extends WebsocketPackage
{

    public UsersReport(TrackedHashMap<String, Client> clients) throws IllegalArgumentException
    {
        super(genReport(clients));
    } // -

    private static String genReport(TrackedHashMap<String, Client> clients)
    {
        String output = "clients:";
        for(int i = 0; i < clients.size(); i++)
        {
            output += clients.get(i).getColor();
            if(i != clients.size() - 1)
                output += ",";
        }
        return output;
    } //

} // *
