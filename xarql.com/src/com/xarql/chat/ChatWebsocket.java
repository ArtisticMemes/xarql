package com.xarql.chat;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.xarql.main.DeveloperOptions;
import com.xarql.util.TrackedHashMap;

@ServerEndpoint ("/chat/websocket/{id}")
public class ChatWebsocket
{
    private static final long    MESSAGE_LIFESPAN = 3600000;
    private static final long    CHECK_INTERVAL   = 60000;
    private static final boolean TESTING          = DeveloperOptions.getTesting();

    private static TrackedHashMap<String, Client> clients   = new TrackedHashMap<String, Client>();
    private static ArrayList<Message>             messages  = new ArrayList<Message>();
    private static Timestamp                      lastCheck = new Timestamp(System.currentTimeMillis());

    @OnOpen
    public void onOpen(@PathParam ("id") String id, Session session)
    {
        Client c;
        try
        {
            c = new Client(session, id);
        }
        catch(Exception e)
        {
            c = new Client(session);
        }
        clients.add(session.getId(), c);
        c.send(new UsersReport(clients));
        broadcast(new UserJoin(c.getColor()));
        removeOldMessages();
        c.send(messages);
    } // onOpen()

    @OnClose
    public void onClose(Session session)
    {
        broadcast(new UserExit(clients.get(session.getId()).getColor()));
        clients.remove(session.getId());
    } // onClose()

    @OnMessage
    public void onMessage(Session session, String message)
    {
        Message msg = new Message(message, clients.get(session.getId()));
        messages.add(msg);
        broadcast(msg);
    } // onMessage()

    @OnError
    public void onError(Session session, Throwable e)
    {
        if(TESTING)
            e.printStackTrace();
        clients.get(session.getId()).send(new ErrorReport(e));
    } // onError()

    private static void broadcast(WebsocketPackage pkg)
    {
        for(int i = 0; i < clients.size(); i++)
        {
            clients.get(i).send(pkg);
        }
    } // broadcast()

    public static int connectionCount()
    {
        return clients.size();
    } // connectionCount()

    private static void removeOldMessages()
    {
        // See if the last check was
        if(lastCheck.compareTo(new Timestamp(System.currentTimeMillis() - CHECK_INTERVAL)) < 0)
        {
            Timestamp expiryTime = new Timestamp(System.currentTimeMillis() - MESSAGE_LIFESPAN);
            for(Message msg : messages)
                if(msg.getCreationDate().compareTo(expiryTime) < 0)
                    messages.remove(msg);
            lastCheck = new Timestamp(System.currentTimeMillis());
        }
    } // removeOldMessages()

} // ChatWebsocket
