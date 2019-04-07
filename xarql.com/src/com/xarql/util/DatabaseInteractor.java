package com.xarql.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Used as a template for classes that interact with the database.
 * 
 * @author Bryan Johnson
 */
public abstract class DatabaseInteractor
{
    /**
     * The current command selected to be executed.
     */
    private String command = null;
    /**
     * The index associated with the command. Will most likely be 0 and remain 0
     * during operation of child classes.
     */
    private int    index;

    /**
     * Sets command variable to the provided command and the index to 0.
     * 
     * @param command A String containing an SQL query
     */
    public DatabaseInteractor(String command)
    {
        index = 0;
        this.command = command;
    } // DatabaseInteractor()

    /**
     * Sets the command variable to null and the index to 0.
     */
    public DatabaseInteractor()
    {
        this(null);
    } // DatabaseInteractor()

    /**
     * A least complex implementation of the common execute method. Practically,
     * alters makeRequest() to be public.
     * 
     * @return A boolean denoting success or failure from makeRequest()
     * @see DatabaseInteractor#makeRequest()
     */
    public boolean execute()
    {
        return makeRequest();
    } // execute()

    /**
     * This allows children to inject the statement in makeRequest() with variables.
     * 
     * @param statement An SQL statement
     * @throws SQLException If the statement can't be modified in the way specified
     */
    protected abstract void setVariables(PreparedStatement statement) throws SQLException;

    /**
     * Makes a request to the database.
     * 
     * @return A boolean denoting success or failure.
     */
    protected abstract boolean makeRequest();

    public String getCommand()
    {
        return command;
    } // getCommand()

    /**
     * Set's this objects current comamnd
     * 
     * @param command A String with an SQL query
     */
    protected void setCommand(String command)
    {
        this.command = command;
    } // setCommand()

    /**
     * Provides access to the object's command index
     * 
     * @return The current command index
     */
    public int getIndex()
    {
        return index;
    } // getIndex()

    /**
     * Increments the command index. Called in makeRequest()
     */
    protected void nextIndex()
    {
        index++;
    } // nextIndex()

} // DatabaseInteractor
