package com.xarql.chat.direct;

import java.util.ArrayList;
import java.util.List;

public class Conversation
{
    private final String        user1;
    private final String        user2;
    private List<DirectMessage> messages;

    public Conversation(DirectMessage msg)
    {
        // user1 being recipient or sender is arbitrary, but user2 must be the same
        user1 = msg.recipient;
        user2 = msg.sender;
        messages = new ArrayList<>();
        messages.add(msg);
    }

    public DirectMessage message(int index)
    {
        return messages.get(index);
    }

    public int size()
    {
        return messages.size();
    }

    public void add(DirectMessage dm)
    {
        messages.add(dm);
    }

    /**
     * Copies all the messages in to a new list and returns the copied list to
     * protect the original.
     *
     * @return copy of messages
     */
    public List<DirectMessage> getMessages()
    {
        List<DirectMessage> copy = new ArrayList<>();
        for(DirectMessage msg : messages)
            copy.add(msg.copy());
        return copy;
    }

    public boolean isEmpty()
    {
        return messages.isEmpty();
    }

    public String getOtherUser(String knownUser)
    {
        if(user1.equals(knownUser))
            return user2;
        else
            return user1;
    }

    public boolean containsUser(String user)
    {
        return user1.equals(user) || user2.equals(user);
    }

}
