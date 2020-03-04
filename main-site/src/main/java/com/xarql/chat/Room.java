package com.xarql.chat;

import java.util.concurrent.CopyOnWriteArrayList;
import com.xarql.util.TrackedHashMap;

public class Room
{
    private TrackedHashMap<String, Client> clients  = new TrackedHashMap<>();
    private CopyOnWriteArrayList<Message>  messages = new CopyOnWriteArrayList<>();

    public Room()
    {
        setUsers(null);
        setMessages(null);
    }

    public TrackedHashMap<String, Client> getClients()
    {
        return clients;
    }

    public CopyOnWriteArrayList<Message> getMessages()
    {
        return messages;
    }

    private void setUsers(TrackedHashMap<String, Client> clients)
    {
        if(clients == null)
            this.clients = new TrackedHashMap<>();
        else
            this.clients = clients;
    }

    private void setMessages(CopyOnWriteArrayList<Message> messages)
    {
        if(messages == null)
            this.messages = new CopyOnWriteArrayList<>();
        else
            this.messages = messages;
    }

}
