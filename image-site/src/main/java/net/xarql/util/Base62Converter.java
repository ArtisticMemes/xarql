/*
 * MIT License http://g.xarql.net Copyright (c) 2018 Bryan Christopher Johnson
 */
package net.xarql.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Converts base 10 numbers to base 62 and vice versa. Example: 873 = E5
 *
 * @author Bryan Johnson
 */
public class Base62Converter
{
    public static void main(String[] args)
    {
        System.out.println(to(873));
        System.out.println(from("E5"));
    }

    private static Map<Integer, Character> charValues = new HashMap<>();

    /**
     * Converts a base 62 number (represented as a String) to a base 10 number
     * (represented as an int). Example: E5 -> 873
     *
     * @param input String representing a base 62 number
     * @return An int equal to the input
     * @throws IllegalArgumentException When an illegal character is in the input
     */
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

    /**
     * Custom "to the power of" method for determining exponential values. For
     * whatever reason Math.pow() didn't work properly for this use case. The output
     * might suffer from an integer overflow if the arguments are too large.
     *
     * @param number The base number
     * @param power The exponent
     * @return number^power
     */
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

    /**
     * Constructs the Map the holds the values for each valid char in a base 62
     * number String
     */
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

    /**
     * @return built charValues
     */
    public static Map<Integer, Character> getCharValues()
    {
        if(charValues.isEmpty())
            buildCharacterMap();
        return charValues;
    }

    /**
     * Converts a base 10 number (represented as an int) to a base 62 number
     * (represented as an String). Example: 873 -> E5
     *
     * @param input any int
     * @return A String equal to the input
     */
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
