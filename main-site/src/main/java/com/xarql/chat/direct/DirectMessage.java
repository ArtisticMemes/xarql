package com.xarql.chat.direct;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DirectMessage
{
    public final String recipient;
    public final String sender;
    public final String content;

    public DirectMessage(String recipient, String sender, String content)
    {
        if(recipient == null || sender == null || content == null)
            throw new NullPointerException("DirectMessage may not have null pointers");
        this.recipient = recipient;
        this.sender = sender;
        this.content = content;
    }

    public static DirectMessage process(ResultSet rs) throws SQLException
    {
        return new DirectMessage(rs.getString("recipient"), rs.getString("sender"), rs.getString("content"));
    }

    public String getRecipient()
    {
        return recipient;
    }

    public String getSender()
    {
        return sender;
    }

    public String getContent()
    {
        return content;
    }

    public static DirectMessage copy(DirectMessage msg)
    {
        return new DirectMessage(msg.recipient, msg.sender, msg.content);
    }

    public DirectMessage copy()
    {
        return DirectMessage.copy(this);
    }

}
