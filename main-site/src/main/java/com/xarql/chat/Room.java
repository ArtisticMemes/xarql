package com.xarql.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.xarql.util.TrackedHashMap;

public class Room
{
    private TrackedHashMap<String, Client> clients  = new TrackedHashMap<>();
    private List<Message>                  messages = new CopyOnWriteArrayList<>();

    public Room()
    {
        setUsers(null);
        setMessages(null);
    }

    public TrackedHashMap<String, Client> getClients()
    {
        return clients;
    }

    public List<Message> getMessages()
    {
        return messages;
    }

    private void setUsers(TrackedHashMap<String, Client> clients)
    {
        if(clients == null)
            this.clients = new TrackedHashMap<String, Client>();
        else
            this.clients = clients;
    }

    private void setMessages(List<Message> messages)
    {
        if(messages == null)
            this.messages = new ArrayList<Message>();
        else
            this.messages = messages;
    }

}
