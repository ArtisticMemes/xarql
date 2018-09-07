package com.xarql.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TrackedHashMap<K, E> // Made to fit caching requirements, not for general use
{
	private ArrayList<K> tracker = new ArrayList<K>();
    private HashMap<K, E> container = new HashMap<K, E>();
    private Random r = new Random();
    
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
    	return tracker.contains(key);
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
    	return tracker.get(size());
    } // getLastKey()
    
    public K firstKey()
    {
    	return tracker.get(0);
    } // firstKey()
    
    
    // Element
    public E get(K key)
    {
    	return container.get(key);
    } // get(K key)
    
    public E getRandom()
    {
    	return container.get(randomKey());
    } // getRandom()
    
    
    // Adding
    public void add(K key, E element) 
    {
    	tracker.add(key);
    	container.put(key, element);
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

} // IgnorantHashMap
