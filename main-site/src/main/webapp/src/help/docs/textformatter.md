### TextFormatter.java
This class has static methods that allow for easily making posts more appealing.
##### Clickable links
Links will be automatically detected by a regex.
##### Newlines
To make text go on to a newline, simply put a newline in the form. If you see that the text is separated, then it's done!
##### Bold, Italic, or Underlined
A marker is a character surrounded by backticks. `b` is for bold, `i` is for italic and `u` is for underlined. Put a marker on either side of the text that you want to be effected. The start marker is made in to `<span class="`_CSS class of marker_`">"` and the end marker is made in to `</span>`.
##### Hashtags
Hashtags are used to categorize your post. A hashtag is made by prefixing an alphanumerical string with `#`. Alphanumeric is defined as lowercase letters a-z, 0-9, dashes and underscores. Hashtags have a maximum length of 24. The hashtag will be made in to a link that has the other newest posts which share that hashtag. (This only applies to `/polr`)