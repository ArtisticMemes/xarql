/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.auth;

import java.sql.Timestamp;

import com.xarql.util.TrackedHashMap;

public class AuthTable
{
    private static TrackedHashMap<String, AuthSession> sessions = new TrackedHashMap<String, AuthSession>();
    private static Timestamp                           lastTrimTime;

    public AuthTable()
    {
        sessions.clear();
    } // -

    public static void add(AuthSession session)
    {
        if(session.verified() && !session.expired())
        {
            if(sessions.contains(session.getTomcatSession()))
                sessions.remove(session.getTomcatSession());
            sessions.add(session.getTomcatSession(), session);
        }

        if(getLastTrimTime().compareTo(new Timestamp(System.currentTimeMillis())) < 10000) // Trim every 10 seconds
        {
            trim();
            setLastTrimTime();
        }
    } //

    public static boolean contains(String tomcatSession)
    {
        if(sessions.contains(tomcatSession) && !sessions.get(tomcatSession).expired())
            return true;
        else
        {
            sessions.remove(tomcatSession);
            return false;
        }
    } //

    public static void trim()
    {
        for(int i = 0; i < sessions.size(); i++)
        {
            if(sessions.get(sessions.key(i)).expired())
            {
                sessions.remove(sessions.key(i));
                i--;
            }
        }
    } //

    public static void remove(String session)
    {
        sessions.remove(session);
    } //

    public static AuthSession get(String session)
    {
        return sessions.get(session);
    } //

    public static int size()
    {
        return sessions.size();
    } //

    private static Timestamp getLastTrimTime()
    {
        if(lastTrimTime == null)
            lastTrimTime = new Timestamp(System.currentTimeMillis());
        return lastTrimTime;
    } //

    private static void setLastTrimTime()
    {
        lastTrimTime = new Timestamp(System.currentTimeMillis());
    } //

} // *
