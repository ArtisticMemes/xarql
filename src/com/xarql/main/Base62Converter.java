/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.main;

import java.util.HashMap;
import java.util.ArrayList;

public class Base62Converter
{
  public static void main(String args[])
  {
    test();
  }
  
  private static void test() // Test 10,000 random values
  {
    // define the range
    long max = 1000000000;
    long min = 0;
    long range = max - min + 1;
    
    // generate random numbers within 1 to 10
    for (int i = 0; i < 10000; i++) 
    {
      long rand = (long)(Math.random() * range) + min;
      if(fromBase62(toBase62(rand)) != rand)
        System.out.println("FAIL");
      if(i % 100 == 0)
        System.out.println("LAST 100 WERE GOOD!");
    }
  }
  
  public static long fromBase62(String input) throws IllegalArgumentException
  {
    long output = 0;
    for(int i = 0; i < input.length(); i++)
    {
      int charValue;
      if(input.charAt(i) >= 48 && input.charAt(i) <= 57)
        charValue = input.charAt(i) - 48;
      else if(input.charAt(i) >= 65 && input.charAt(i) <= 90)
        charValue = input.charAt(i) - 55;
      else if(input.charAt(i) >= 97 && input.charAt(i) <= 122)
        charValue = input.charAt(i) - 61;
      else
        throw new IllegalArgumentException("Illegal Character:" + input.charAt(i));
      int weight = (input.length() - 1) - i;
      output += charValue * pow(62, weight);
    }
    return output;
  } // fromBase62()
  
  private static long pow(int number, int power)
  {
    if(power == 0 || number == 0)
      return 1;
    long output = number;
    while(power > 1)
    {
      output *= number;
      power--;
    }
    return output;
  } // pow
  
  public static String toBase62(long input)
  {
    String output = "";
    HashMap<Integer, Character>  charValues= new HashMap<Integer, Character>(); // This could be kept in memory
    int i = 0;
    int booster = 48;
    while(i < 62)
    {
      if(i == 10)
        booster = 55;
      else if(i == 36)
        booster = 61;
      charValues.put(new Integer(i), new Character((char) (i + booster)));
      i++;
    }
    
    ArrayList<Integer> digits = new ArrayList<Integer>();
    do
    {
      digits.add((int) (input % 62));
      input = (input - (input % 62)) / 62;
    } while(input > 0);
    
    for(int c = digits.size() - 1; c >= 0; c--)
      output += charValues.get(digits.get(c));
    return output;
  } // toBase62()
} // base62Converter
