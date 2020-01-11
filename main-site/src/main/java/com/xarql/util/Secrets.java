package com.xarql.util;

import java.util.ArrayList;

public class Secrets
{
    // DBManager
    public static final String DBUser = null;
    public static final String DBPass = null;

    // VerifyRecaptcha
    public static final String RECAPTCHA_KEY    = null;
    public static final String RECAPTCHA_SECRET = null;

    // TextFormatter
    @Deprecated
    public static final String[] CENSORED_WORDS = { ""
    };

    public static final String MOD_SIGNATURE = "UwU";

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
