package net.xarql.serve;

import java.util.ArrayList;
import java.util.List;

/**
 * All permitted file types on xarql.net
 */
enum FileType
{
    JPG("jpg", "jpeg"), PNG("png"), MP3("mp3"), WEBP("webp"), WEBM("webm"), MP4("mp4");

    /**
     * The primary extension text. Such as <code>jpg</code>
     */
    private final String   extension;
    /**
     * All possible extensions for the format. Such as <code>jpg</code> and
     * <code>jpeg</code>
     */
    private final String[] aliases;

    /**
     * @param extensions All possible extensions
     */
    private FileType(String... extensions)
    {
        extension = extensions[0];
        aliases = extensions;
    }

    /**
     * @return extension
     */
    public String getExtension()
    {
        return extension;
    }

    /**
     * @return "." + extension
     */
    public String dotExtension()
    {
        return "." + extension;
    }

    /**
     * Provides a List of aliases built from the aliases array. The List is an
     * ArrayList internally.
     *
     * @return aliases as List
     */
    public List<String> getAliases()
    {
        ArrayList<String> types = new ArrayList<>();
        for(String str : aliases)
            types.add(str);
        return types;
    }

    /**
     * Determines if the given String contains the extension to a valid FileType.
     *
     * @param extension Some file type extension
     * @return Validity of given extension
     */
    public static boolean isValidFileType(String extension)
    {
        if(extension == null)
            return false;
        else
        {
            extension = prepare(extension);
            for(FileType type : FileType.values())
                if(type.getAliases().contains(extension))
                    return true;
        }
        return false;
    }

    /**
     * Provides the FileType enum associated with the given extension.
     *
     * @param extension Some file type extension
     * @return A FileType that represents the given extension
     * @throws IllegalArgumentException When no FileType is associated with the
     *         given extension
     */
    public static FileType determine(String extension) throws IllegalArgumentException
    {
        extension = prepare(extension);
        if(!isValidFileType(extension))
            throw new IllegalArgumentException("FileType was invalid : " + extension);
        else
            for(FileType type : FileType.values())
                if(type.getAliases().contains(extension))
                    return type;
        return null;
    }

    /**
     * Provides the FileType enum associated with the given extension, but if no
     * enum has an association with the given extension then the fallback is
     * returned.
     *
     * @see FileType#determine
     * @param extension Some file type extension
     * @param fallback A "default" FileType
     * @return A FileType that represents the given extension, or the fallback if no
     *         FileType matched the given extension
     */
    public static FileType determine(String extension, FileType fallback)
    {
        try
        {
            return determine(extension);
        }
        catch(IllegalArgumentException e)
        {
            return fallback;
        }
    }

    /**
     * Provides the FileType enum associated with the given index.
     *
     * @param number index of FileType
     * @return FileType at given index
     * @throws IllegalArgumentException When the index is out of bounds
     */
    public static FileType determine(int number) throws IllegalArgumentException
    {
        for(FileType type : FileType.values())
            if(number == type.ordinal())
                return type;
        throw new IllegalArgumentException("Index didn't correlate to a valid FileType. Index : " + number);
    }

    /**
     * Provides the FileType enum associated with the given index, but if the index
     * if out of bounds then the fallback is returned.
     *
     * @see FileType#determine(int)
     * @param number index of FileType
     * @param fallback A "default" FileType
     * @return FileType at given index, or the fallback if the number was out of
     *         bounds
     */
    public static FileType determine(int number, FileType fallback)
    {
        try
        {
            return determine(number);
        }
        catch(IllegalArgumentException e)
        {
            return fallback;
        }
    }

    /**
     * Provides the FileType enum associated with the given index.
     *
     * @see FileType#determine(int)
     * @param number The index of the FileType, as a String
     * @return The FileType of the given index
     * @throws IllegalArgumentException If the String didn't evaluate to an Integer
     */
    public static FileType parseInt(String number) throws IllegalArgumentException
    {
        try
        {
            return determine(Integer.parseInt(number));
        }
        catch(NumberFormatException e)
        {
            throw new IllegalArgumentException("String representing a FileType index was incorrect. String : " + number);
        }
    }

    /**
     * Provides the FileType enum associated with the given index, but if the index
     * if out of bounds then the fallback is returned.
     *
     * @see FileType#parseInt(String)
     * @see FileType#determine(int)
     * @param number The index of the FileType, as a String
     * @param fallback A "default" FileType
     * @return The FileType of the given index, or the fallback if the String didn't
     *         evaluate to a number or the index was out of bounds
     */
    public static FileType parseInt(String number, FileType fallback)
    {
        try
        {
            return parseInt(number);
        }
        catch(IllegalArgumentException e)
        {
            return fallback;
        }
    }

    /**
     * Prepares a String that represents a file extension, such as <code>.txt</code>
     * for use by methods that determine the FileType.
     *
     * @param extension A raw String that should represent a FileType
     * @return A processed String; extension only
     */
    private static String prepare(String extension)
    {
        extension = extension.toLowerCase().trim();
        if(extension.contains("."))
            return extension.substring(extension.indexOf('.') + 1);
        else
            return extension;
    }

}
