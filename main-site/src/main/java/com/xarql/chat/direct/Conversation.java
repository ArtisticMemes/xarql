package com.xarql.chat.direct;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Conversation
{
    public final String         recipient;
    public final String         sender;
    private List<DirectMessage> messages;

    public Conversation(String recipient, String sender, String... contents)
    {
        this.recipient = recipient;
        this.sender = sender;
        messages = new ArrayList<>();
        for(String text : contents)
            messages.add(new DirectMessage(text, recipient, sender));
    }

    public Conversation(String recipient, String sender)
    {
        this(recipient, sender, new String[0]);
    }

    public Conversation(DirectMessage msg)
    {
        this(msg.recipient, msg.sender, msg.content);
    }

    public DirectMessage message(int index)
    {
        return messages.get(index);
    }

    public int size()
    {
        return messages.size();
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

    public static Conversation process(ResultSet rs) throws SQLException
    {
        return new Conversation(DirectMessage.process(rs));
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
