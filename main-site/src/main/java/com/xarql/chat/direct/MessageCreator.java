package com.xarql.chat.direct;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.xarql.util.DatabaseUpdate;

public class MessageCreator extends DatabaseUpdate
{
    private static final String COMMAND = "INSERT INTO direct_messages (recipient, sender, content) VALUES (?, ?, ?)";

    private final String recipient;
    private final String sender;
    private final String content;

    public MessageCreator(String recipient, String sender, String content)
    {
        super(COMMAND);
        this.recipient = recipient;
        this.sender = sender;
        this.content = content;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, recipient);
        statement.setString(2, sender);
        statement.setString(3, content);
    }

}
