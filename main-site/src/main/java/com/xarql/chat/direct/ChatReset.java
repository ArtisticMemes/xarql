package com.xarql.chat.direct;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.xarql.util.DatabaseUpdate;

public class ChatReset extends DatabaseUpdate
{
    private static final String COMMAND = "CREATE TABLE direct_messages(id int PRIMARY KEY AUTO_INCREMENT, content text, recipient text, sender text, status tinyint UNSIGNED DEFAULT 0, date timestamp NOT NULL DEFAULT NOW(), FULLTEXT(content)) CHARACTER SET utf8 COLLATE utf8_general_ci";

    public static void main(String[] args)
    {
        new ChatReset().use();
    }

    public ChatReset()
    {
        super(COMMAND);
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        // do nothing
    }

}
