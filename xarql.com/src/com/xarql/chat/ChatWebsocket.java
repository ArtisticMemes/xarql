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
    public void onOpen(Session client)
    {
        clients.add(client);
        broadcast(stat());
    } // onOpen()

    @OnClose
    public void onClose(Session client)
    {
        clients.remove(client);
        broadcast(stat());
    } // onClose()

    @OnMessage
    public void onMessage(Session client, String message)
    {
        Message msg = new Message(message, client);
        broadcast(msg);
    } // onMessage()

    @OnError
    public void onError(Session peer, Throwable e)
    {
        e.printStackTrace();
    } // onError()

    private static void broadcast(WebsocketPackage pkg)
    {
        for(Session client : clients)
        {
            client.getAsyncRemote().sendText(pkg.toString());
            client.getAsyncRemote().setSendTimeout(1000);
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
