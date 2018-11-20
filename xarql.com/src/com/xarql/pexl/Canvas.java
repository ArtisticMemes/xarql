package com.xarql.pexl;

import java.util.ArrayList;

public class Canvas
{
    public static Canvas  main      = new Canvas(512, 512);
    private char[][]      canvas;
    private static char[] colorBank = {
            'w', 'n', 'm', 'r'
    };

    private int width;  // Width of canvas
    private int height; // Height of canvas

    public Canvas(int x, int y)
    {
        canvas = new char[y][x];
        width = x;
        height = y;
    } // Canvas()

    public void paint(int x, int y, char color) throws IllegalArgumentException
    {
        if(checkColor(color))
        {
            if(x >= 0 && x < width)
            {
                if(y >= 0 && y < height)
                {
                    canvas[y][x] = color;
                }
                else
                    throw new IllegalArgumentException("y out of bounds");
            }
            else
                throw new IllegalArgumentException("x out of bounds");
        }
        else
            throw new IllegalArgumentException("Invalid color");
    } // paint()

    private static boolean checkColor(char color)
    {
        for(char selectedColor : colorBank)
        {
            if(color == selectedColor)
                return true;
        }
        return false;
    } // checkColor()

    public ArrayList<Character> getPexlEncoding()
    {
        ArrayList<Character> output = new ArrayList<Character>(height * width);
        for(int a = 0; a < height; a++)
        {
            for(int b = 0; b < width; b++)
            {
                output.add(canvas[a][b]);
            }
        }
        return output;
    } // getStringEncoding()

} // Canvas
