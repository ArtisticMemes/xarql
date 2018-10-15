/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TrackedHashMap<K, E> // Made to fit caching requirements, not for general use
{
    private ArrayList<K>  tracker   = new ArrayList<K>();
    private HashMap<K, E> container = new HashMap<K, E>();
    private Random        r         = new Random();

    public TrackedHashMap()
    {
        tracker.ensureCapacity(0);
    } // IgnorantHashMap()

    // Stats
    public int size()
    {
        return tracker.size();
    } // size()

    public boolean contains(K key)
    {
        return container.containsKey(key);
    } // contains()

    // Key
    public K key(int index)
    {
        return tracker.get(index);
    } // getKey(int index)

    public K randomKey()
    {
        return tracker.get(r.nextInt(size()));
    } // randomKey()

    public K lastKey()
    {
        return tracker.get(size() - 1);
    } // getLastKey()

    public K firstKey()
    {
        return tracker.get(0);
    } // firstKey()

    public int getIndexOf(K key)
    {
        if(contains(key))
            return tracker.lastIndexOf(key);
        else
            return -1;
    } // getIndexOf()

    // Element
    public E get(K key)
    {
        return container.get(key);
    } // get(K key)

    public E getRandom()
    {
        return container.get(randomKey());
    } // getRandom()

    public ArrayList<E> getAll()
    {
        return getRange(0, size());
    } // getAll()

    public ArrayList<E> getFrom(int startingIndex)
    {
        return getRange(startingIndex, size());
    } // getAllFrom()

    public ArrayList<E> getUntil(int endingIndex)
    {
        return getRange(0, endingIndex);
    } // getAllUntil()

    public ArrayList<E> getRange(int startingIndex, int endingIndex)
    {
        if(startingIndex > size())
            startingIndex = size();
        else if(startingIndex < 0)
            startingIndex = 0;

        if(endingIndex > size())
            endingIndex = size();
        else if(endingIndex < 0)
            endingIndex = 0;

        if(startingIndex > endingIndex)
        {
            startingIndex = 0;
            endingIndex = 0;
        }

        ArrayList<E> al = new ArrayList<E>(endingIndex - startingIndex);
        for(int i = startingIndex; i < endingIndex; i++)
            al.add(get(key(i)));
        return al;
    } // getRange()

    // Adding
    public void add(K key, E element)
    {
        if(!contains(key))
        {
            tracker.add(key);
            container.put(key, element);
        }
    } // add(K key, E element)

    // Removing
    public void remove(K key)
    {
        tracker.remove(key);
        container.remove(key);
    } // remove()

    public void removeLast()
    {
        remove(lastKey());
    } // removeLast()

    public void removeFirst()
    {
        remove(firstKey());
    } // removeFirst()

    public void clear()
    {
        container.clear();
        tracker.clear();
    } // clear()

} // IgnorantHashMap
