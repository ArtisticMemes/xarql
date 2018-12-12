<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Help (TextFormatter.java) ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
  <link rel="stylesheet" type="text/css" href="${domain}/src/common/${theme}-common.min.css">
  <link rel="shortcut icon" href="${domain}/logo.png" type="image/x-icon">
  <style id="font-size">
    html, body {
      font-size: ${font_size}
    }
  </style>
  <style id="font-weight">
    p {
      font-weight: ${font_weight}
    }
    h6, .bold {
      font-weight: ${font_weight + 200}
    }
  </style>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div class="large-card">
        <h3>TextFormatter.java</h3><p>This class has static methods that allow for easily making posts more appealing.</p><h5>Clickable links</h5><p>Links will be automatically detected by a regex. </p><h5>Newlines</h5><p>To make text go on to a newline, simply put a newline in the form. If you see that the text is separated, then it's done!</p><h5>Bold, Italic, or Underlined</h5><p>A marker is a character surrounded by backticks. <code>b</code> is for bold, <code>i</code> is for italic and <code>u</code> is for underlined. Put a marker on either side of the text that you want to be effected. The start marker is made in to <code>&lt;span class="</code><em>CSS class of marker</em><code>"&gt;"</code> and the end marker is made in to <code>&lt;/span&gt;</code>.</p><h5>Hashtags</h5><p>Hashtags are used to categorize your post. A hashtag is made by prefixing an alphanumerical string with <code>#</code>. Alphanumeric is defined as lowercase letters a-z, 0-9, dashes and underscores. Hashtags have a maximum length of 24. The hashtag will be made in to a link that has the other newest posts which share that hashtag. (This only applies to <code>/polr</code>)</p>
      </div>
      <div class="small-card">
        <a style="margin-top:0.5em;" class="centered" href="${domain}/help/main"><span class="italic">Main Help Page</span></a>
      </div>
    </div>
  </div>
</body>
</html>
