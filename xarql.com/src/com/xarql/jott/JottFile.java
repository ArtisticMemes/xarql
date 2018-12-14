package com.xarql.jott;

public class JottFile
{
    private static final String SCRIPT_START_MARKER = "<";
    private static final String SCRIPT_END_MARKER   = ">";
    private static final String PARAM_MARKER        = ",";

    private String content;

    public JottFile(String input)
    {
        setContent(input);
        // parseScripts(input);
    } // JottFile()

    private String process(String input)
    {
        return "";
    } // process()

    private void setContent(String content)
    {
        this.content = content;
    } // setContent()

    public String getContent()
    {
        return content;
    } // getContent()

} // JottFile
