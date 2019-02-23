package com.xarql.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

import com.xarql.auth.AuthSession;

public class Client
{
    private String            color;
    private Session           session;
    private ArrayList<String> queue;
    private boolean           sending;
    private boolean           typing;

    public Client(Session session)
    {
        this.session = session;
        queue = new ArrayList<String>();
        sending = false;
        setColor(randomColor());
    } // Client()

    public Client(Session session, String color)
    {
        this.session = session;
        queue = new ArrayList<String>();
        sending = false;
        try
        {
            setColor(color);
        }
        catch(Exception e)
        {
            setColor(randomColor());
        }
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
    } // send()

    public void sendList(List<? extends WebsocketPackage> packages)
    {
        for(WebsocketPackage pkg : packages)
            queue.add(pkg.toString());
        try
        {
            sendQueue();
        }
        catch(Exception e)
        {
        }
    } // send()

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

    private void setColor(String color)
    {
        if(color != null && color.length() == 6)
            this.color = color;
        else
            throw new NullPointerException("Color shouldn't be null.");
    } // setColor()

    private static String randomColor()
    {
        return AuthSession.generateColor().substring(1);
    } // randomizeColor()

    public boolean isOpen()
    {
        return session.isOpen();
    } // isOpen()

    public String getID()
    {
        return session.getId();
    } // getID()

} // Client
