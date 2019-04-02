/*
 * MIT License http://g.xarql.com Copyright (c) 2018 Bryan Christopher Johnson
 */
package com.xarql.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.rjeschke.txtmark.Processor;

public class TextFormatter
{
    // Testing
    public static void main(String[] args)
    {
        String test = "Don't put hashes on these >>> or these <<<. But do on #test< or #tes>ttag";
        System.out.println(full(test));
        System.out.println(getHashtags(test));

        test = "Lemme know if you can #tag people with @hello";
        System.out.println(full(test));
    } // main()

    public static final String URL_REGEX     = "((http)s?(:\\/\\/)([a-z0-9]+\\.)+([a-z]+(\\/)?)|([a-z0-9]+\\.)((com|net|org|io|co)(\\/)?))([a-zA-Z0-9-_]+(\\/)?)*(\\?[a-zA-Z0-9-_]+=[a-zA-Z0-9-_]+)?(&[a-zA-Z0-9-_]+=[a-zA-Z0-9-_+]+)*";
    public static final int    HASHTAG_LIMIT = 5;
    public static final String PHOTO_REGEX   = "\\$[0-1][0-9a-zA-Z]+";
    public static final String HASHTAG_REGEX = "(?>#[a-z0-9-_]+)(?!;)";
    public static final String USER_REGEX    = "(?>@[a-z0-9-_]+)";

    public static String processMarkdown(String input)
    {
        return Processor.process(input);
    } // processMarkdown()

    public static String autoLinks(String input)
    {
        String output = "";
        ArrayList<String> outputParts = new ArrayList<String>();
        Pattern p = Pattern.compile(URL_REGEX); // the pattern to search for
        Matcher m = p.matcher(input);

        int start = 0;
        int prevEnd = 0;
        int end = 0;
        // if we find a match, get the group
        while(m.find())
        {
            String match = m.group();
            start = m.start();
            end = m.end();
            outputParts.add(input.substring(prevEnd, start));
            if(!match.startsWith("https://") && !match.startsWith("http://"))
                match = "//" + match;
            outputParts.add("<a class=\"out-link\" href=\"" + match + "\">" + match + "</a>");
            prevEnd = end;
        }
        outputParts.add(input.substring(end));

        for(String item : outputParts)
            output += item;

        return output;
    } // autoLinks()

    public static String quickPic(String input)
    {
        String output = "";
        ArrayList<String> outputParts = new ArrayList<String>();
        Pattern p = Pattern.compile(PHOTO_REGEX); // the pattern to search for
        Matcher m = p.matcher(input);
        int start = 0;
        int prevEnd = 0;
        int end = 0;
        // if we find a match, get the group
        while(m.find())
        {
            String match = m.group();
            start = m.start();
            end = m.end();
            outputParts.add(input.substring(prevEnd, start));

            outputParts.add("<a class=\"photo-link\" href=\"https://xarql.net/" + match.substring(1) + "\" data=\"" + match.substring(1) + "\">" + match + "</a>");
            prevEnd = end;
        }
        outputParts.add(input.substring(end));

        for(String item : outputParts)
            output += item;

        return output;
    } // quickPic()

    /**
     * Informs a Servlet if it should accept a user's post.
     * 
     * @param input A <code>String</code> from the user.
     * @return true if a censored word is detected, false otherwise.
     */
    public static boolean shouldCensor(String input)
    {
        input = input.toLowerCase();

        // Remove all non-latin characters
        String scannable = "";
        for(int i = 0; i < input.length(); i++)
        {
            if(input.charAt(i) >= 97 && input.charAt(i) <= 122)
                scannable += input.charAt(i);
        }

        // Check for censored words (return true if any are found)
        for(int i = 0; i < Secrets.CENSORED_WORDS.length; i++)
        {
            if(scannable.contains(Secrets.CENSORED_WORDS[i]))
                return true;
        }
        return false;
    } // filter()

    public static String clickableUsers(String input)
    {
        String output = "";
        ArrayList<String> outputParts = new ArrayList<String>();
        Pattern p = Pattern.compile(USER_REGEX); // the pattern to search for
        Matcher m = p.matcher(input);
        int start = 0;
        int prevEnd = 0;
        int end = 0;
        // if we find a match, get the group
        while(m.find())
        {
            String match = m.group();
            start = m.start();
            end = m.end();
            outputParts.add(input.substring(prevEnd, start));

            outputParts.add("<a class=\"user-link\" href=\"{DOMAIN}/user/view?name=" + match.substring(1) + "\" data=\"" + match.substring(1) + "\">" + match + "</a>");
            prevEnd = end;
        }
        outputParts.add(input.substring(end));

        for(String item : outputParts)
            output += item;

        return output;
    } // clickableHashtags

    public static String clickableHashtags(String input)
    {
        String output = "";
        ArrayList<String> outputParts = new ArrayList<String>();
        Pattern p = Pattern.compile(HASHTAG_REGEX); // the pattern to search for
        Matcher m = p.matcher(input);
        int start = 0;
        int prevEnd = 0;
        int end = 0;
        // if we find a match, get the group
        while(m.find())
        {
            String match = m.group();
            start = m.start();
            end = m.end();
            outputParts.add(input.substring(prevEnd, start));

            outputParts.add("<a class=\"hash-link\" href=\"{DOMAIN}/polr/hash?tag=" + match.substring(1) + "\" data=\"" + match.substring(1) + "\">" + match + "</a>");
            prevEnd = end;
        }
        outputParts.add(input.substring(end));

        for(String item : outputParts)
            output += item;

        return output;
    } // clickableHashtags

    public static ArrayList<String> getHashtags(String input)
    {
        ArrayList<String> tags = new ArrayList<String>(); // tags to be returned
        Pattern p = Pattern.compile(HASHTAG_REGEX); // the pattern to search for
        Matcher m = p.matcher(input);
        // if we find a match, add it to tags
        while(m.find())
        {
            String match = m.group();
            tags.add(match.substring(1));
        }
        return tags;
    } // getHashtags()

    public static boolean isAlphaNumeric(char input)
    {
        int num = input;
        if(num >= 97 && num <= 122) // a --> z range
            return true;
        else if(num >= 48 && num <= 57) // 0 --> 9 range
            return true;
        else if(input == '_' || input == '-') // dashes and underscores are ok
            return true;
        else
            return false;
    } // isAlphaNumeric(char)

    public static boolean isAlphaNumeric(String input)
    {
        for(int i = 0; i < input.length(); i++)
        {
            if(!isAlphaNumeric(input.charAt(i)))
                return false;
        }
        return true;
    } // isAlphaNumeric(String)

    public static boolean isStandardSet(char input)
    {
        int num = input;
        return num >= 33 && num <= 126;
    } // isStandardSet(char)

    public static boolean isStandardSet(String input)
    {
        for(int i = 0; i < input.length(); i++)
        {
            if(!isStandardSet(input.charAt(i)))
                return false;
        }
        return true;
    } // isStandardSet(String)

    /**
     * Prepares raw <code>Strings</code> from the user for displaying on a web page
     * 
     * @param input Main <code>String</code> from user
     * @return A fully formatted <code>String</code> that is ready to appear as a
     *         post.
     */
    public static String full(String input)
    {
        String output = input.trim();
        output = clean(output);
        output = swapEscapeForHTML(output, '\n', "<br>", 2);
        output = autoLinks(output);
        output = clickableHashtags(output);
        output = clickableUsers(output);
        output = quickPic(output);
        output = addFormat(output, "bold", 'b');
        output = addFormat(output, "code", 'c');
        output = addFormat(output, "italic", 'i');
        output = addFormat(output, "underline", 'u');
        output = addFormat(output, "strike", 's'); // strikethrough
        return output;
    } // full()

    /**
     * Allows special characters from forms to be rendered as HTML.
     * 
     * @param input Main <code>String</code> from user
     * @param target Escape character to replace with HTML code
     * @param replacement HTML code that will replace <code>target</code>
     * @param consecutiveLimit Maximum times the target may appear consecutively and
     *        be replaced. Excess appearances are removed.
     * @return A <code>String</code> which contains ready-made HTML instead of
     *         inconsequential Java escape characters.
     */
    public static String swapEscapeForHTML(String input, char target, String replacement, int consecutiveLimit)
    {
        String output = "";
        StringBuffer text = new StringBuffer(input);
        int location = (new String(text)).indexOf(target);
        while(location > 0)
        {
            text.replace(location, location + 1, replacement);
            location = (new String(text)).indexOf(target);
        }
        output = new String(text);
        output = removeRepeats(output, replacement, consecutiveLimit);
        return output;
    } // swapEscapeForHTML()

    /**
     * Removes consecutive repeats of a <code>String</code> after the amount of
     * repeats surpasses a limit.
     * 
     * @param input A String from the user, which may have repeats.
     * @param target The <code>String</code> whose repetitions should be limited.
     * @param limit Amount of times the <code>target</code> is allowed to repeat
     * @return A <code>String</code> with a limited amount of a repeated target.
     */
    public static String removeRepeats(String input, String target, int limit)
    {
        String output = "";
        int amount = 0;
        while(input.length() > 0)
        {
            if(input.indexOf(target) == 0 && amount < limit)
            {
                output += input.substring(0, target.length());
                input = input.substring(target.length() - 1);
                amount++;
            }
            else if(input.indexOf(target) == 0)
            {
                amount++;
                input = input.substring(target.length() - 1);
            }
            else
            {
                output += input.charAt(0);
                amount = 0;
            }
            input = input.substring(1);
        }
        return output;
    } // removeRepeats()

    /**
     * Replace a backtick format marker, such as <code>`b`</code>, with its
     * respective span class
     * 
     * @param input A <code>String</code> from the user with formatting markers.
     * @param formatClass The CSS class of the formatting type.
     * @param trigger The character that will be used inside of the backticks to
     *        form the marker.
     * @return A <code>String</code> whose markers have been replaced with spans.
     */
    private static String addFormat(String input, String formatClass, char trigger)
    {
        String output = "";
        boolean withinFormat = false;
        for(int i = 0; i < input.length(); i++)
        {
            if(input.charAt(i) == '`')
            {
                if(withinFormat)
                {
                    if(input.length() > i + 2 && input.charAt(i + 1) == trigger && input.charAt(i + 2) == '`')
                    {
                        output += "</span>"; // </span>
                        withinFormat = false;
                        i += 2;
                    }
                    else
                        output += input.charAt(i);
                }
                else
                {
                    if(input.length() > i + 2 && input.charAt(i + 1) == trigger && input.charAt(i + 2) == '`')
                    {
                        output += "<span class=\"" + formatClass + "\">"; // <span class="formatClass">
                        i += 2;
                        withinFormat = true;
                    }
                    else
                        output += input.charAt(i);
                }
            }
            else
            {
                output += input.charAt(i);
                if(i == input.length() - 1 && withinFormat)
                    output += "</span>";
            }
        }
        return output;
    } // addFormat()

    /**
     * Strip potentially dangerous characters to prevent HTML injection.
     * 
     * @param input A <code>String</code> from the user.
     * @return A <code>String</code> without angle brackets representations.
     */
    private static String clean(String input)
    {
        return input.replace("<", "&#60;").replace(">", "&#62;");
    } // clean()
} // TextFormatter
