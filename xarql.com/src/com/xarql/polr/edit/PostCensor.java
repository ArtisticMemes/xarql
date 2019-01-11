package com.xarql.polr.edit;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import com.xarql.polr.PageCache;
import com.xarql.util.DatabaseUpdate;

public class PostCensor extends DatabaseUpdate
{
    private static final String[] WARNING_TYPES   = {
            "Violent", "Sexual", "Offensive", "None"
    };
    private static final String   DEFAULT_WARNING = "None";

    private int    id;
    private String warning;

    public PostCensor(int id, String warning, HttpServletResponse response)
    {
        super("UPDATE polr SET warning=? WHERE id=?", response);
        this.id = id;
        setWarning(warning);
    } // PostRemover()

    private void setWarning(String warning)
    {
        for(int i = 0; i < WARNING_TYPES.length; i++)
        {
            if(warning.equals(WARNING_TYPES[i]))
            {
                this.warning = warning;
                return;
            }
        }
        this.warning = DEFAULT_WARNING;
    } // setWarning()

    @Override
    protected void setVariables(PreparedStatement statement) throws SQLException
    {
        statement.setString(1, warning);
        statement.setInt(2, id);
    } // setVariables()

    @Override
    public boolean execute()
    {
        boolean output = super.makeRequest();
        PageCache.clear();
        return output;
    } // execute()

} // PostCensor
