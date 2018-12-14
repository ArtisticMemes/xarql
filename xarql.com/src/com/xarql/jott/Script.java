package com.xarql.jott;

import java.util.ArrayList;

public class Script
{
    private static final String[] VALID_COMMANDS      = {
            "rpt", "$"
    };
    private static final char     SCRIPT_START_MARKER = '<';
    private static final char     SCRIPT_END_MARKER   = '>';
    private static final String   PARAM_MARKER        = ";";

    private String   command;
    private String[] params;

    public Script(String[] input) throws IllegalArgumentException
    {
        if(input.length == 0)
            throw new IllegalArgumentException(" failed because it didn't have any arguments.");
        if(input.length == 1)
            throw new IllegalArgumentException(" failed because it only had 1 argument.");
        checkCommand(input);
        checkParams(input);
    } // Script()

    public static void parseScripts(String input) throws IllegalArgumentException
    {
        int scriptIndex = 0;
        try
        {
            ArrayList<Script> scripts = new ArrayList<Script>();
            while(input.indexOf(SCRIPT_START_MARKER) != -1 && input.indexOf(SCRIPT_END_MARKER) != -1)
            {
                scriptIndex++;
                scripts.add(new Script(input.substring(input.indexOf(SCRIPT_START_MARKER) + 1, input.indexOf(SCRIPT_END_MARKER)).split(PARAM_MARKER)));
                input = input.substring(input.indexOf(SCRIPT_END_MARKER) + 1);
            }
        }
        catch(IllegalArgumentException e)
        {
            throw new IllegalArgumentException("Script " + scriptIndex + e.getMessage());
        }

    } // parseScripts()

    public static void checkParams(String[] script)
    {
        for(int i = 0; i < script.length; i++)
        {
            if(script[i] == null || script[i].equals(""))
                throw new IllegalArgumentException(" failed because argument " + (i + 1) + " was empty");
        }
        return;
    } // checkParams()

    public static void checkCommand(String[] script) throws IllegalArgumentException
    {
        for(int i = 0; i < VALID_COMMANDS.length; i++)
        {
            if(script[0].equals(VALID_COMMANDS[i]))
                return;
        }
        throw new IllegalArgumentException(" failed because it didn't have a valid command");
    } // checkCommands()

} // Script
