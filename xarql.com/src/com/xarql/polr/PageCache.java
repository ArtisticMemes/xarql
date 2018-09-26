/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.polr;

import java.util.ArrayList;

import com.xarql.util.TrackedHashMap;

public class PageCache
{
    // Cache control
    private static TrackedHashMap<String, PageCache> cache = new TrackedHashMap<String, PageCache>();

    // Each page
    private Post[] posts = new Post[0];
    int id;
    String sort;
    String flow;
    int page;

    // Limits and Defaults
    private static final int MAX_CACHE_SIZE = 128;

    public static void clear()
    {
        cache.clear();
    } // clear()

    public static PageCache getPage(String title)
    {
        if(cache.contains(title))
            return cache.get(title);
        else
            return null;
    } // getPage(String title)

    public static Post[] getPageAsArray(String title)
    {
        if(cache.contains(title))
            return cache.get(title).getPosts();
        else
            return null;
    } // getPageAsArray(String title)

    public static ArrayList<Post> getPageAsList(String title)
    {
        if(cache.contains(title))
            return cache.get(title).getPostsAsList();
        else
            return null;
    } // getPageAsList(String title)

    public PageCache(int id, String sort, String flow, int page, Post[] posts)
    {
        this.posts = posts;
        cache.add(id + "|" + sort + "|" + flow + "|" + page, this);
        trim();
    } // PageCache()

    public PageCache(int id, String sort, String flow, int page, ArrayList<Post> posts)
    {
        this.posts = posts.toArray(this.posts);
        cache.add(id + "|" + sort + "|" + flow + "|" + page, this);
        trim();
    } // PageCache(ArrayList<Post> posts)

    public void trim()
    {
        while(cache.size() > MAX_CACHE_SIZE)
            cache.removeFirst();
    } // maintainSize()

    public ArrayList<Post> getPostsAsList()
    {
        ArrayList<Post> posts = new ArrayList<Post>();
        for(int i = 0; i < this.posts.length; i++)
            posts.add(this.posts[i]);
        return posts;
    } // getPostsAsList()

    public Post[] getPosts()
    {
        return posts;
    } // getPosts()

} // PageCache
