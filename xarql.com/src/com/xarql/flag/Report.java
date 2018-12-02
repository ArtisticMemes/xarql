package com.xarql.flag;

public class Report
{
    private String type;
    private String description;
    private int    postID;

    private static final String[] types = {
            "Hate Speech", "Spam"
    };

    public Report(String type, String description, int postID) throws IllegalArgumentException
    {
        setType(type);
        setDescription(description);
        setPostID(postID);
    } // Report(type, description)

    private void setType(String type) throws IllegalArgumentException
    {
        for(String item : types)
        {
            if(type.equals(item))
            {
                this.type = type;
                return;
            }
        }
        throw new IllegalArgumentException("Invalid Report Type");
    } // setType()

    private void setDescription(String description)
    {
        if(description.length() > 512)
            description = description.substring(0, 513);
        this.description = description;
    } // setDescription()

    private void setPostID(int postID)
    {
        this.postID = postID;
    } // setPostID()

    public String getType()
    {
        return type;
    } // getType()

    public String getDescription()
    {
        return description;
    } // getDescription()

    public int getPostID()
    {
        return postID;
    } // getPostID

} // Report
