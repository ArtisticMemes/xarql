package com.xarql.chat.direct;

import java.util.ArrayList;
import java.util.List;

public class Conversation
{
    public final String         recipient;
    public final String         sender;
    private List<DirectMessage> messages;

    public Conversation(DirectMessage msg)
    {
        recipient = msg.recipient;
        sender = msg.sender;
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

    public void add(String content)
    {
        messages.add(new DirectMessage(recipient, sender, content));
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

    public String getRecipient()
    {
        return recipient;
    }

    public String getSender()
    {
        return sender;
    }

}
