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
     * The ID of the media. Represented as a base 62 alphanumeric number. Excludes
     * the first character used for type indication.
     */
    private final String   id;
    /**
     * The file extension of the media.
     */
    private final FileType type;

    /**
     * Creates a new media with the supplied id and type.
     *
     * @param id Represents the media's number. Exclude the first character; that is
     *        only for type indication.
     * @param type The extension for the media.
     */
    public Media(String id, FileType type)
    {
        this.id = id;
        this.type = type;
    }

    /**
     * Gives the short link to this media which opens in an media viewer.
     *
     * @return The short link
     */
    public String getLink()
    {
        return DOMAIN + "/" + type.ordinal() + id;
    }

    /**
     * Gives the long link to this media which provides the actual file on its own.
     * The file will be named raw.
     *
     * @return The raw link
     */
    public String getRawLink()
    {
        return DOMAIN + "/-/static/" + type.getExtension() + "/" + id + "/raw." + type.getExtension();
    }

}
