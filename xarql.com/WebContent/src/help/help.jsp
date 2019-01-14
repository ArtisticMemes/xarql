<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <!-- Global site tag (gtag.js) - Google Analytics -->
  <script async src="https://www.googletagmanager.com/gtag/js?id=${google_analytics_id}"></script>
  <script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', '${google_analytics_id}');
  </script>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Help ~ xarql</title>
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
       <!-- Inlcude .html here as hyperlink -->
        <h3 class="centered">Help</h3>
        <p>First, Select A Host</p>
        <p><a href="https://github.com/ArtisticMemes/xarql/wiki">GitHub</a></p>
        <p><a href="${domain}/help/main">xarql.com</a></p>
      </div>
      <div class="small-card"><p class="centered"><span class="italic">.txt Files Are Below | <a href="https://github.com/ArtisticMemes/xarql">View On GitHub</a></span></p></div>
      <!-- Include .txt here as text -->
      <div class="large-card">
        <h4>Disclaimer</h4>
        <p>xarql.com does not make any guarantees as to the quality of its user generated content (UGC). As such, you may find content which you consider objectionable or offensive where UGC is displayed. Please discontinue use of the site if you are uncomfortable with this content, as xarql.com may not have the tools or human resources required to moderate UGC. UGC is included on <a href="http://xarql.com/polr" target="_blank">/polr</a> and <a href="http://xarql.com/chat" target="_blank">/chat</a></p>
        <p><a href="${domain}/help/disclaimer">Specific Link</a></p>
      </div>
      <div class="large-card">
        <h4>License</h4>
        <p>MIT License</p>
        <p>http://g.xarql.com</p>
        <p>Copyright (c) 2018 Bryan Christopher Johnson</p>
        <p>Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:</p>
        <p>The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.</p>
        <p>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.</p>
        <p><a href="${domain}/help/license">Specific Link</a></p>
      </div>
      <div class="large-card">
        <h4>Authors</h4>
        <p>All legally recognized names of all contributors to this repository as of December 11, 2018 are listed below.</p>
        <ul>
          <li>Bryan Christopher Johnson</li>
          <li>Chowdhury Md. Sami Al Muntahi</li>
        </ul>
        <p>"contributors" is defined as all persons who satisfy the following criteria:</p>
        <ol>
          <li>Submitted source code to this repository, which is an original work made by the submitter for this repository.</li>
          <li>Gave sumbission(s) that were accepted to the master branch.</li>
          <li>Have recorded in written form at the time of this document's publication their desire to have their submission(s) make them designated and be recognized as a contributor.</li>
        </ol>
        <p><a href="${domain}/help/authors">Specific Link</a></p>
      </div>
      <div class="small-card">
        <a style="margin-top:0.5em;" class="centered" href="${domain}/help/main"><span class="italic">Main Help Page</span></a>
      </div>
    </div>
  </div>
</body>
</html>
