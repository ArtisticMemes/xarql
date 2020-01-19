package com.xarql.util;

import java.util.ArrayList;

public class Secrets
{
    // DBManager
    public static final String DBUser = "$db_user";
    public static final String DBPass = "$db_pass";

    // VerifyRecaptcha - "nobot" is used because I can't spell
    public static final String RECAPTCHA_KEY    = "$nobot_key";
    public static final String RECAPTCHA_SECRET = "$nobot_secret";

    private static final ArrayList<String> MODERATOR_LIST = new ArrayList<>(1);

    public static ArrayList<String> modList()
    {
        if(MODERATOR_LIST.isEmpty())
            buildModeratorList();
        return MODERATOR_LIST;
    }

    private static void buildModeratorList()
    {
        MODERATOR_LIST.clear();
        MODERATOR_LIST.add("xarql");
        MODERATOR_LIST.add("cliserkad");
    }

}
