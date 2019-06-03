package net.xarql.serve;

import net.xarql.util.DeveloperOptions;

/**
 * Represents a media location on the site
 *
 * @author Bryan Johnson
 */
public final class Media
{
    /**
     * Current root URL
     */
    private static final String DOMAIN = DeveloperOptions.getDomain();

    /**
     * The ID of the image. Represented as a base 62 alphanumeric number. Excludes
     * the first character used for type indication.
     */
    private final String   id;
    /**
     * The file extension of the image.
     */
    private final FileType type;

    /**
     * Creates a new image with the supplied id and type.
     *
     * @param id Represents the image's number. Exclude the first character; that is
     *        only for type indication.
     * @param type The extension for the image. 0 for jpg. 1 for png.
     */
    public Media(String id, FileType type)
    {
        this.id = id;
        this.type = type;
    }

    /**
     * Gives the short link to this image which opens in an image viewer.
     *
     * @return The short link
     */
    public String getLink()
    {
        return DOMAIN + "/" + type.ordinal() + id;
    }

    /**
     * Gives the long link to this image which provides the actual file on its own.
     * The file will be named raw.
     *
     * @return The raw link
     */
    public String getRawLink()
    {
        return DOMAIN + "/-/static/" + type.getExtension() + "/" + id + "/raw." + type.getExtension();
    }

}
