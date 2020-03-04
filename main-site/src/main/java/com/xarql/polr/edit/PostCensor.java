package com.xarql.polr.edit;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.xarql.polr.PageCache;
import com.xarql.util.DatabaseUpdate;

public class PostCensor extends DatabaseUpdate
{
    private static final String[] WARNING_TYPES   = { "Violent", "Sexual", "Offensive", "None"
    };
    private static final String   DEFAULT_WARNING = "None";

    private static final String COMMAND = "UPDATE polr SET warning=? WHERE id=?";

    private int    id;
    private String warning;

    public PostCensor(int id, String warning)
    {
        super(COMMAND);
        this.id = id;
        setWarning(warning);
    }

    private void setWarning(String warning)
    {
        for(String element : WARNING_TYPES)
            if(warning.equals(element))
            {
                this.warning = warning;
                return;
            }
        this.warning = DEFAULT_WARNING;
    }

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, warning);
        statement.setInt(2, id);
    }

    @Override
    public boolean execute()
    {
        boolean output = makeRequest();
        PageCache.clear();
        return output;
    }

}
