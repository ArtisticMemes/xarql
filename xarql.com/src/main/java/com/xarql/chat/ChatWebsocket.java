package com.xarql.chat;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private static final boolean TESTING = DeveloperOptions.getTesting();

    private static TrackedHashMap<String, Client> clients  = new TrackedHashMap<String, Client>();
    private static CopyOnWriteArrayList<Message>  messages = new CopyOnWriteArrayList<Message>();

    public static void main(String[] args)
    {
        // Testing getNameValuePairs()
        String input = "hello:no,type:message,hi:hello|";
        System.out.println("length: " + input.length());
        HashMap<String, String> map = getHeaders(input);
        System.out.println(map.get("hello"));
        System.out.println(map.get("type"));
        System.out.println(map.get("hi"));
    } // main()

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
        c.send(new UsersReport(clients));
        clients.add(session.getId(), c);
        ripple(new UserJoin(c), c);
        refresh();
        c.sendList(messages);
    } // onOpen()

    @OnClose
    public void onClose(Session session)
    {
        Client c = clients.get(session.getId());
        ripple(new UserExit(c), c);
        clients.remove(session.getId());
    } // onClose()

    @OnMessage
    public void onMessage(Session session, String message)
    {
        WebsocketPackage pkg = parseMessage(session, message);
        if(pkg.getClass().getSimpleName().equals("Message"))
        {
            Message msg = (Message) pkg;
            if(!msg.getContent().trim().equals(""))
            {
                messages.add(msg);
                broadcast(msg);
            }
        }
        else
            ripple(pkg, clients.get(session.getId()));
    } // onMessage()

    @OnError
    public void onError(Session session, Throwable e)
    {
        if(TESTING)
            e.printStackTrace();
        clients.get(session.getId()).send(new ErrorReport(e));
        onClose(session);
    } // onError()

    private static void broadcast(WebsocketPackage pkg)
    {
        for(Client c : clients)
            c.send(pkg);
    } // broadcast()

    private static void ripple(WebsocketPackage pkg, Client client)
    {
        for(Client c : clients)
        {
            if(!c.equals(client))
                c.send(pkg);
        }
    } // ripple()

    public static int connectionCount()
    {
        return clients.size();
    } // connectionCount()

    private synchronized static void refresh()
    {
        // Remove old messages
        for(Message msg : messages)
            if(msg.isExpired())
                messages.remove(msg);

        // Remove closed sessions / dead clients
        for(Client c : clients)
            if(!c.isOpen())
            {
                ripple(new UserExit(c), c);
                clients.remove(c.getID());
            }
    } // refresh()

    private WebsocketPackage parseMessage(Session session, String message)
    {
        HashMap<String, String> headers = getHeaders(message);
        String content = getContent(message);
        String type = headers.get("type");
        if(type.equals("message"))
        {
            return new Message(getContent(message), clients.get(session.getId()));
        }
        else if(type.equals("typing"))
        {
            return new TypingStatus(booleanize(headers.get("typing")), clients.get(session.getId()));
        }
        else if(type.equals("buffer"))
        {
            return new BufferStatus(booleanize(headers.get("buffer")), clients.get(session.getId()));
        }
        else
            return new ErrorReport(null);
    } // parseMessage()

    private static boolean booleanize(String value)
    {
        if(value == null)
            return false;
        else
        {
            value = value.trim().toLowerCase();
            if(value.equals("true"))
                return true;
            else
                return false;
        }
    } // booleanize()

    private static HashMap<String, String> getHeaders(String input)
    {
        HashMap<String, String> map = new HashMap<String, String>();
        int i = 0;
        boolean a = true; // Represents being at name in name:value pair
        String name = "";
        String value = "";
        while(input.charAt(i) != '|')
        {
            if(a)
            {
                name = "";
                value = "";
                while(input.charAt(i) != ':')
                {
                    name += input.charAt(i);
                    i++;
                }
                i++;
                a = false;
            }
            else
            {
                while(input.charAt(i) != ',' && input.charAt(i) != '|')
                {
                    value += input.charAt(i);
                    i++;
                }
                if(input.charAt(i) == ',')
                    i++;
                map.put(name, value);
                a = true;
            }
        }
        return map;
    } // getNameValuePairs

    private static String getContent(String input)
    {
        return input.substring(input.indexOf('|') + 1);
    } // getContent()

} // ChatWebsocket
