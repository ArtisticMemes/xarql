package net.xarql.serve;

import net.xarql.util.DeveloperOptions;

public class Image
{
    private static final String   DOMAIN      = DeveloperOptions.getDomain();
    private static final String[] VALID_TYPES = {
            "jpg", "png"
    };

    private String id;
    private int    type;

    public Image(String id, int type) throws IllegalArgumentException
    {
        setID(id);
        setType(type);
    } // Image()

    public String getLink()
    {
        return DOMAIN + "/" + getType() + getID();
    } // getLink()

    public String getRawLink()
    {
        return DOMAIN + "/-/static/" + VALID_TYPES[getType()] + "/" + getID() + "/raw." + VALID_TYPES[getType()];
    } // getRawLink()

    private String getID()
    {
        return id;
    } // getID()

    private int getType()
    {
        return type;
    } // getType()

    private void setID(String id)
    {
        this.id = id;
    } // setID()

    private void setType(int type) throws IllegalArgumentException
    {
        if(type < 0 || type >= VALID_TYPES.length)
            throw new IllegalArgumentException("Type was invalid");
        this.type = type;
    } // setType()

} // Image
