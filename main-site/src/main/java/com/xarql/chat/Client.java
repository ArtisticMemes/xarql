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

    public Client(Session session)
    {
        this.session = session;
        queue = new ArrayList<String>();
        setColor(randomColor());
    } // -

    public Client(Session session, String color)
    {
        this.session = session;
        queue = new ArrayList<String>();
        try
        {
            setColor(color);
        }
        catch(Exception e)
        {
            setColor(randomColor());
        }
    } // -

    public void send(WebsocketPackage pkg) throws IOException
    {
        queue.add(pkg.toString());
        sendQueue();
    } //

    public void sendList(List<? extends WebsocketPackage> packages) throws IOException
    {
        for(WebsocketPackage pkg : packages)
            queue.add(pkg.toString());
        sendQueue();
    } //

    private synchronized void sendQueue() throws IOException
    {
        while(!queue.isEmpty())
        {
            session.getBasicRemote().sendText(queue.get(0));
            queue.remove(0);
        }
    } //

    public String getColor()
    {
        return color;
    } //

    private void setColor(String color)
    {
        if(color != null && color.length() == 6)
            this.color = color;
        else
            throw new NullPointerException("Color shouldn't be null.");
    } //

    private static String randomColor()
    {
        return AuthSession.generateColor().substring(1);
    } //

    public boolean isOpen()
    {
        return session.isOpen();
    } //

    public String getID()
    {
        return session.getId();
    } //

    public Session getSession()
    {
        return session;
    } //

} // *
