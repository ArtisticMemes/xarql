package com.xarql.chat.direct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.xarql.util.DatabaseQuery;

public class MessageRetriever extends DatabaseQuery<Conversation>
{
    private static final String COMMAND = "SELECT * FROM direct_messages WHERE (recipient=? AND sender=?) OR (recipient=? AND sender=?) ORDER BY date LIMIT 25";

    private Conversation convo;
    private final String recipient;
    private final String sender;

    public MessageRetriever(String recipient, String sender)
    {
        super(COMMAND);
        this.recipient = recipient;
        this.sender = sender;
    }

    @Override
    protected void processResult(ResultSet rs) throws SQLException
    {
        if(convo == null)
            convo = new Conversation(DirectMessage.process(rs));
        else
            convo.add(DirectMessage.process(rs));
    }

    @Override
    protected Conversation getData()
    {
        return convo;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, recipient);
        statement.setString(2, sender);
        statement.setString(3, sender);
        statement.setString(4, recipient);
    }

}
