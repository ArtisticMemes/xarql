package com.xarql.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DatabaseInteractor
{
    private String command = null;
    private int    index;

    public DatabaseInteractor(String command)
    {
        index = 0;
        this.command = command;
    } // DatabaseInteractor()

    public DatabaseInteractor()
    {
        this(null);
    } // DatabaseInteractor()

    public boolean execute()
    {
        return makeRequest();
    } // execute()

    protected abstract void setVariables(PreparedStatement statement) throws SQLException;

    protected abstract boolean makeRequest();

    public String getCommand()
    {
        return command;
    } // getCommand()

    protected void setCommand(String command)
    {
        this.command = command;
    } // setCommand()

    public int getIndex()
    {
        return index;
    } // getIndex()

    protected void nextIndex()
    {
        index++;
    } // nextIndex()

} // DatabaseInteractor
