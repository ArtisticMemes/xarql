package com.xarql.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

public abstract class DatabaseInteractor
{
    protected String              command  = null;
    protected int                 commandIndex;
    protected HttpServletResponse response = null;

    public DatabaseInteractor(String command, HttpServletResponse response)
    {
        commandIndex = 0;
        this.command = command;
        this.response = response;
    } // DatabaseInteractor()

    public abstract boolean execute();

    protected abstract void setVariables(PreparedStatement statement) throws SQLException;

    protected abstract boolean makeRequest();

    public String getCommand()
    {
        return command;
    } // getCommand()

} // DatabaseInteractor
