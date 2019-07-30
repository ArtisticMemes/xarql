package com.xarql.chat.direct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.xarql.util.DatabaseQuery;

public class RecentRetriever extends DatabaseQuery<List<Conversation>>
{
    private static final String COMMAND = "SELECT * FROM direct_messages WHERE id IN (SELECT MAX(id) FROM direct_messages WHERE recipient=? GROUP BY sender)";

    private List<Conversation> conversations;
    private final String       recipient;

    public RecentRetriever(String recipient)
    {
        super(COMMAND);
        this.recipient = recipient;
        conversations = new ArrayList<>();
    }

    @Override
    protected void processResult(ResultSet rs) throws SQLException
    {
        conversations.add(Conversation.process(rs));
    }

    @Override
    protected List<Conversation> getData()
    {
        return conversations;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, recipient);
    }

}
