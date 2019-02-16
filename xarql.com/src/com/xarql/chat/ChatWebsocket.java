package com.xarql.chat;

import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint ("/chat/websocket")
public class ChatWebsocket
{
    private static ArrayList<Session> clients = new ArrayList<Session>();

    @OnOpen
    public void onOpen(Session peer)
    {
        clients.add(peer);
        broadcast(stat());
    } // onOpen()

    @OnClose
    public void onClose(Session peer)
    {
        clients.remove(peer);
        broadcast(stat());
    } // onClose()

    @OnMessage
    public String onMessage(Session peer, String message)
    {
        broadcast(message);
        return "Message sent";
    } // onMessage()

    @OnError
    public void onError(Session peer, Throwable e)
    {
        e.printStackTrace();
    } // onError()

    private static void broadcast(String message)
    {
        for(Session client : clients)
        {
            client.getAsyncRemote().sendText(message);
            client.getAsyncRemote().setSendTimeout(1000);
        }
    } // broadcast()

    private static String stat()
    {
        return "Connection count - " + connectionCount();
    } // stat()

    public static int connectionCount()
    {
        return clients.size();
    } // connectionCount()

} // ChatWebsocket
