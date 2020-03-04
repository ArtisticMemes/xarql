package com.xarql.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import javax.servlet.ServletContext;

public class JSPBuilder
{
    public static final String HEAD_MARKER = "<!-- END HEAD -->";

    public static void build(String filePath, ServletContext context)
    {
        try
        {
            String root = context.getRealPath("/src").replace("\\", "/");
            File doc = new File(root + filePath + ".jsp");
            if(doc.exists() == false)
            {
                String content = grabFile(root + filePath + ".html");
                String head = content.substring(0, content.indexOf(HEAD_MARKER));
                content = content.substring(content.indexOf(HEAD_MARKER));

                String templateH1 = grabFile(root + "/common/template_h1.html");
                String templateB1 = grabFile(root + "/common/template_b1.html");
                String templateB2 = grabFile(root + "/common/template_b2.html");

                content = templateH1 + head + templateB1 + content + templateB2;

                doc.createNewFile();
                FileWriter fw = new FileWriter(doc);
                fw.write(content);
                fw.close();
            }
            return;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String grabFile(File file)
    {
        try
        {
            Scanner scan = new Scanner(file);
            String content = "";
            while(scan.hasNextLine())
                content += scan.nextLine();
            scan.close();
            return content;
        }
        catch(FileNotFoundException e)
        {
            return null;
        }
    }

    public static String grabFile(String filePath)
    {
        return grabFile(new File(filePath));
    }

}
