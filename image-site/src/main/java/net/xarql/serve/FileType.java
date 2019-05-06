package net.xarql.serve;

import java.util.ArrayList;
import java.util.List;

enum FileType
{
    JPG(new String[]{
            "jpg", "jpeg"
    }), PNG("png"), MP3("mp3");

    private String   extension;
    private String[] aliases;

    private FileType(String[] validExtensions)
    {
        this.extension = validExtensions[0];
        this.aliases = validExtensions;
    } // FileTypes(String[])

    private FileType(String extension)
    {
        this(new String[]{
                extension
        });
    } // FileTypes(String)

    public String getExtension()
    {
        return this.extension;
    } // getExtension()

    public String dotExtension()
    {
        return "." + this.extension;
    } // dotExtension()

    public List<String> getAliases()
    {
        ArrayList<String> types = new ArrayList<>();
        for(String str : aliases)
            types.add(str);
        return types;
    } // getAliases()

    public static boolean isValidFileType(String extension)
    {
        if(extension == null)
            return false;
        else
        {
            extension = prepare(extension);
            for(FileType type : FileType.values())
            {
                if(type.getAliases().contains(extension))
                    return true;
            }
        }
        return false;
    } // isValidFileType()

    public static FileType determine(String extension) throws IllegalArgumentException
    {
        extension = prepare(extension);
        if(!isValidFileType(extension))
            throw new IllegalArgumentException("FileType was invalid : " + extension);
        else
            for(FileType type : FileType.values())
            {
                if(type.getAliases().contains(extension))
                    return type;
            }
        return null;
    } // determine(extension)

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
    } // determine(extension, fallback)

    public static FileType determine(int number) throws IllegalArgumentException
    {
        for(FileType type : FileType.values())
        {
            if(number == type.ordinal())
                return type;
        }
        throw new IllegalArgumentException("Index didn't correlate to a valid FileType. Index : " + number);
    }

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

    private static String prepare(String extension)
    {
        extension = extension.toLowerCase().trim();
        if(extension.contains("."))
            return extension.substring(extension.indexOf('.') + 1);
        else
            return extension;
    } // prepare()

} // FileTypes
