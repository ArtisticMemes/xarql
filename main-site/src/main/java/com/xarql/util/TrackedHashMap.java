/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

/**
 * A container that has both a list and a map to use them as one. This was
 * primarily made for caching.
 *
 * @author Bryan Johnson
 * @param <K> Key
 * @param <E> Element
 */
public class TrackedHashMap<K, E> implements Iterable<E>, Cloneable
{
    /**
     * The tracker retains the relationship between keys and indices.
     */
    private ArrayList<K>  tracker   = new ArrayList<>();
    /**
     * The container retains the relationship between keys and elements. Elements
     * are always retrieved using a key
     */
    private HashMap<K, E> container = new HashMap<>();
    private static Random r         = new Random();

    /**
     * Creates a TrackedHashMap() and sets the tracker's capacity to 0.
     */
    public TrackedHashMap()
    {
        tracker.ensureCapacity(0);
    }

    // Stats
    /**
     * Returns the size of this TrackedHashMap. The size is gotten from the tracker.
     * Specifically, <code>tracker.size()</code>
     *
     * @return size
     */
    public int size()
    {
        return tracker.size();
    }

    /**
     * Specifies if this TrackedHashMap contains the given key. The key's existence
     * is determined in the container. Specifically,
     * <code>container.containsKey(key)</code>
     *
     * @param key The key whose existence is in question
     * @return Existence of the key
     */
    public boolean contains(K key)
    {
        return container.containsKey(key);
    }

    // Key
    /**
     * Grabs the key at the given index from the tracker. Specifically,
     * <code>tracker.get(index)</code>
     *
     * @param index The place from which a key will be grabbed
     * @return The key at the given index
     */
    public K key(int index)
    {
        return tracker.get(index);
    }

    /**
     * Grabs a key from a random index in the tracker. Specifically,
     * <code>key(r.nextInt(size()))</code>
     *
     * @return A random key
     */
    public K randomKey()
    {
        return key(r.nextInt(size()));
    }

    /**
     * Grabs the last key that was added to this TrackedHashMap. The key is grabbed
     * from the tracker. Specifically, <code>key(size() - 1)</code>
     *
     * @return The last added key
     */
    public K lastKey()
    {
        return key(size() - 1);
    }

    /**
     * Grabs the first key that was added to this TrackedHashMap. The key is grabbed
     * from the tracker. Specifically, <code>key(0)</code>
     *
     * @return The first key
     */
    public K firstKey()
    {
        return key(0);
    }

    /**
     * Attempts to determine the index of the given key in the tracker. If this
     * TrackedHashMap doesn't contain the key, then -1 is returned.
     *
     * @param key The key whose index is desired
     * @return The index of the key. Or -1 if the key doesn't exist.
     */
    public int getIndexOf(K key)
    {
        if(contains(key))
            return tracker.lastIndexOf(key);
        else
            return -1;
    }

    // Element
    /**
     * Retrieves the element that is mapped to the given key.
     *
     * @param key The known value used to retrieve the unknown object
     * @return The element that is being retrieved
     */
    public E get(K key)
    {
        return container.get(key);
    }

    /**
     * Retrieves the element at the given index. The element is retrieved by first
     * getting the key at the given index from the tracker, then using that key to
     * retrieve the element from the container.
     *
     * @param i Index to retrieve an element from
     * @return The element at the given index
     */
    public E get(int i)
    {
        return container.get(key(i));
    }

    /**
     * Retrieves a random element. First gets a random key from the tracker. Then
     * uses that key to retrieve the associated element.
     *
     * @return A random element.
     */
    public E getRandom()
    {
        return container.get(randomKey());
    }

    /**
     * Retrieves all of the elements in the container and returns them in an
     * ArrayList. Specifically, calls <code>getRange(0, size())</code>
     *
     * @return An ArrayList containing all of the elements
     */
    public ArrayList<E> getAll()
    {
        return getRange(0, size());
    }

    /**
     * Retrieves all of the elements in the container, starting at the given index.
     * The element at the given index will be included.
     *
     * @param startingIndex The first index to retrieve from
     * @return An ArrayList with elements
     */
    public ArrayList<E> getFrom(int startingIndex)
    {
        return getRange(startingIndex, size());
    }

    /**
     * Retrieves all of the elements in the container, ending at the given index.
     * The element at the given index will not be included.
     *
     * @param endingIndex The index to stop retrieving at
     * @return An ArrayList with elements
     */
    public ArrayList<E> getUntil(int endingIndex)
    {
        return getRange(0, endingIndex);
    }

    /**
     * Returns all of the elements in the container, starting at startingIndex and
     * stopping at endingIndex. The element at startingIndex will be included. The
     * element at endingIndex will not be included. Has a loop that grabs the key at
     * an index, then uses that key to grab an element. Verification: If
     * startingIndex or endingIndex is greater than the size of the container, that
     * index will be set to the container's size. If either is below 0, that index
     * will be set to 0. If startingIndex is greater than endingIndex, then they
     * will both be set to 0.
     *
     * @param startingIndex The first index to retrieve from
     * @param endingIndex The index to stop retrieving at
     * @return An ArrayList with elements
     */
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

        ArrayList<E> al = new ArrayList<>(endingIndex - startingIndex);
        for(int i = startingIndex; i < endingIndex; i++)
            al.add(get(key(i)));
        return al;
    }

    // Adding
    /**
     * Adds the key to the tracker. Adds the key and the element to the container.
     *
     * @param key A key
     * @param element An element
     */
    public void add(K key, E element)
    {
        if(!contains(key))
        {
            tracker.add(key);
            container.put(key, element);
        }
    }

    // Removing
    /**
     * Removes the key and associated element from this TrackedHashMap.
     *
     * @param key The key to initiate removal with
     */
    public void remove(K key)
    {
        tracker.remove(key);
        container.remove(key);
    }

    /**
     * Removes the last added key and associated element. Specifically,
     * <code>remove(lastKey());</code>
     */
    public void removeLast()
    {
        remove(lastKey());
    }

    /**
     * Removes the first added key and associated element. Specifically,
     * <code>remove(firstKey());</code>
     */
    public void removeFirst()
    {
        remove(firstKey());
    }

    /**
     * Removes the key and associated element at the given index. Specifically,
     * <code>remove(key(index));</code>
     *
     * @param index The place at which the key and element should be removed
     */
    public void removeAt(int index)
    {
        remove(key(index));
    }

    /**
     * Empties this TrackedHashMap of all data.
     */
    public void clear()
    {
        container.clear();
        tracker.clear();
    }

    @Override
    public TrackedHashMap<K, E> clone()
    {
        TrackedHashMap<K, E> clone = new TrackedHashMap<>();
        for(K key : tracker)
            clone.add(key, get(key));
        return clone;
    }

    @Override
    public Iterator<E> iterator()
    {
        Iterator<E> i = new Iterator<E>()
        {
            private int currentIndex = 0;

            @Override
            public boolean hasNext()
            {
                return currentIndex < size();
            }

            @Override
            public E next()
            {
                currentIndex++;
                return get(currentIndex - 1);
            }

            @Override
            public void remove()
            {
                removeAt(currentIndex);
            }
        };
        return i;
    }

    /**
     * Determines equity between this TrackedHashMap and another one with similar
     * key and element types. Checks if each key and element are equal across maps
     * and are in sequence.
     *
     * @param input A TrackedHashMap with like key and element types
     * @return Equity of this and the input
     */
    public boolean equals(TrackedHashMap<K, E> input)
    {
        if(size() != input.size())
            return false;

        for(int i = 0; i < input.size(); i++)
        {
            if(!key(i).equals(input.key(i)))
                return false;
            if(!get(i).equals(input.get(i)))
                return false;
        }
        return true;
    }

}
