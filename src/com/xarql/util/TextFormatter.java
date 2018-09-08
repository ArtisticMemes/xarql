/*
MIT License
http://g.xarql.com
Copyright (c) 2018 Bryan Christopher Johnson
*/
package com.xarql.util;

public class TextFormatter {
	
	// Do everything
	public static String full(String input)
	{
		String output = input;
		output = clean(output);
		output = addLinks(output);
		output = addNewlines(output);
		output = addFormat(output, "bold", 'b');
		output = addFormat(output, "code", 'c');
		output = addFormat(output, "italic", 'i');
		output = addFormat(output, "underline", 'u');
		output = addFormat(output, "strike", 's'); // strikethrough
		return output;
	} // full()
	
	// Replace `n` with newlines / <br/> 
	private static String addNewlines(String input)
	  {
	    char trigger = 'n';
	    String output = "";
	    boolean withinFormat = false;
	    for(int i = 0; i < input.length(); i++)
	    {
	      if(input.length() > i + 2 && input.charAt(i) == '`' && input.charAt(i + 1) == trigger && input.charAt(i + 2) == '`')
	      {
	        output += "<br/>";
	        i += 2;
	      }
	      else
	        output += input.charAt(i);
	    }
	    return output;
	  } // addNewlines()
	  
	  // Replace a backtick format marker, such as `b`, with its respective <span></span>
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
	  
	  private static String addLinks(String input)
	  {
		// Enable links with ~
		boolean insideTilde = false;
		String linkedText = "";
		String withinTilde = "";
		for(int i = 0; i < input.length(); i++)
		{
		  if(input.charAt(i) == '~' && insideTilde == false && input.substring(i + 1, input.length()).contains("~"))
		  {
		    insideTilde = true;
		    linkedText += "<a href=\"";
		  }
		  else if(input.charAt(i) == '~' && insideTilde == true)
		  {
		    insideTilde = false;
		    linkedText += withinTilde + "\" target=\"_blank\">" + withinTilde + "</a>";
		    withinTilde = "";
		  }
		  else if(insideTilde)
		  {
		    withinTilde += input.charAt(i);
		  }
		  else
		  {
		    linkedText += input.charAt(i);
		  }
		}
		return linkedText;
	  } // link()
	  
	  // Strip potentially dangerous characters
	  private static String clean(String input)
	  {
		  return input.replace("<", "&#60;").replace(">", "&#62;");
	  } // clean()
} // TextFormatter
