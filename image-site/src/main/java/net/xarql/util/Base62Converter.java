/*
 * MIT License http://g.xarql.net Copyright (c) 2018 Bryan Christopher Johnson
 */
package net.xarql.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Base62Converter
{
    public static void main(String[] args)
    {
        System.out.println(to(873));
        System.out.println(from("E5"));
    }

    private static Map<Integer, Character> charValues = new HashMap<>();

    public static int from(String input) throws IllegalArgumentException
    {
        int output = 0;
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
                throw new IllegalArgumentException("Illegal Character : " + input.charAt(i));
            int weight = input.length() - 1 - i;
            output += charValue * pow(62, weight);
        }
        return output;
    }

    private static int pow(int number, int power)
    {
        if(power == 0 || number == 0)
            return 1;
        int output = number;
        while(power > 1)
        {
            output *= number;
            power--;
        }
        return output;
    }

    private static void buildCharacterMap()
    {
        HashMap<Integer, Character> builtValues = new HashMap<>(); // This could be kept in memory
        int i = 0;
        int booster = 48;
        while(i < 62)
        {
            if(i == 10)
                booster = 55;
            else if(i == 36)
                booster = 61;
            builtValues.put(new Integer(i), new Character((char) (i + booster)));
            i++;
        }
        charValues = builtValues;
    }

    public static Map<Integer, Character> getCharValues()
    {
        if(charValues.isEmpty())
            buildCharacterMap();
        return charValues;
    }

    public static String to(int input)
    {
        String output = "";

        ArrayList<Integer> digits = new ArrayList<>();
        do
        {
            digits.add(input % 62);
            input = (input - input % 62) / 62;
        }
        while(input > 0);

        for(int i = digits.size() - 1; i >= 0; i--)
            output += getCharValues().get(digits.get(i));
        return output;
    }
}
