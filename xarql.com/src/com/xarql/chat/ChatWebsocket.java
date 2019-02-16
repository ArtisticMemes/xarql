package com.xarql.chat;

import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.xarql.util.TrackedHashMap;

@ServerEndpoint ("/chat/websocket")
public class ChatWebsocket
{
    private static TrackedHashMap<String, Client> clients  = new TrackedHashMap<String, Client>();
    private static ArrayList<Message>             messages = new ArrayList<Message>();

    @OnOpen
    public void onOpen(Session session)
    {
        Client c = new Client(session);
        clients.add(session.getId(), c);
        for(Message msg : messages)
            c.send(msg);
        broadcast(stat());
    } // onOpen()

    @OnClose
    public void onClose(Session session)
    {
        clients.remove(session.getId());
        broadcast(stat());
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
        e.printStackTrace();
    } // onError()

    private static void broadcast(WebsocketPackage pkg)
    {
        for(int i = 0; i < clients.size(); i++)
        {
            clients.get(i).send(pkg);
        }
    } // broadcast()

    private static WebsocketPackage stat()
    {
        return new WebsocketPackage(false, "connections : " + clients.size());
    } // stat()

    public static int connectionCount()
    {
        return clients.size();
    } // connectionCount()

} // ChatWebsocket
