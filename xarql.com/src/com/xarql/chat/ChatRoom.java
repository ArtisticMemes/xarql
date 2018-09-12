/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.chat;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.xarql.auth.AuthSession;
import com.xarql.util.TrackedHashMap;

public class ChatRoom {
	private static TrackedHashMap<Integer, Message> messages = new TrackedHashMap<Integer, Message>();
	private static int currentId;
	
	public ChatRoom() 
	{
		init();
	} // ChatRoom()
	
	public static void init()
	{
		MessageRetriever mr = new MessageRetriever(0);
		ArrayList<Message> messagesAsList = mr.execute();
		if(messagesAsList.size() > 0)
		{
			currentId = messagesAsList.get(messagesAsList.size() - 1).getId();
			for(int i = 0; i < messagesAsList.size(); i++)
				messages.add(new Integer(messagesAsList.get(i).getId()), messagesAsList.get(i));
		}
		else
			currentId = 0;
	} // init()
	
	public static void add(String message, AuthSession session)
	{
		if(messages.size() == 0)
			init();
		else
		{
			trim();
			currentId++;
			Message input = new Message(currentId, message, new Timestamp(System.currentTimeMillis()), session);
			messages.add(new Integer(currentId), input);
		}
	} // add()
	
	public static void trim()
	{
		Timestamp sixHoursAgo = new Timestamp(System.currentTimeMillis() - 21600000);
		for(int i = 0; i < messages.size(); i++)
		{
			if(messages.get(messages.key(i)).getDate().before(sixHoursAgo))
			{
				messages.remove(messages.key(i));
				i--; // stay on the same index, it'll be occupied by a new object
			}
		}
	} // trim()
	
	public static boolean contains(int id)
	{
		return messages.contains(new Integer(id));
	} // contains()
	
	public static ArrayList<Message> getList(int lastID)
	{
		if(messages.size() == 0)
			init();
		if(messages.getIndexOf(lastID) == messages.size() - 1)
			return new ArrayList<Message>(0);
		else if(messages.contains(lastID + 1))
		{
			return messages.getFrom(messages.getIndexOf(lastID + 1));
		}
		else
			return messages.getFrom(0);
	} // getList()

} // ChatRoom
