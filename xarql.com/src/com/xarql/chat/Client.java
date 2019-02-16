package com.xarql.chat;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import com.xarql.auth.AuthSession;

public class Client
{
    private String            color;
    private Session           session;
    private ArrayList<String> queue;
    private boolean           sending;

    public Client(Session session)
    {
        this.session = session;
        queue = new ArrayList<String>();
        sending = false;
        randomizeColor();
    } // Client()

    public void send(WebsocketPackage pkg)
    {
        queue.add(pkg.toString());
        try
        {
            sendQueue();
        }
        catch(Exception e)
        {
        }
    } // sendText()

    private void sendQueue() throws IOException
    {
        if(sending)
            return;
        else
        {
            sending = true;
            while(!queue.isEmpty())
            {
                session.getBasicRemote().sendText(queue.get(0));
                queue.remove(0);
            }
            sending = false;
        }
    } // sendQueue()

    public String getColor()
    {
        return color;
    } // getColor()

    private void randomizeColor()
    {
        color = AuthSession.generateColor().substring(1);
    } // randomizeColor()

} // Client
