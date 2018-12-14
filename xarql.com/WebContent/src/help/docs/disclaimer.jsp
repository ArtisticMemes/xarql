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
  <title>Disclaimer ~ xarql</title>
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
        <h4>Disclaimer</h4>
        <p>xarql.com does not make any guarantees as to the quality of its user generated content (UGC). As such, you may find content which you consider objectionable or offensive where UGC is displayed. Please discontinue use of the site if you are uncomfortable with this content, as xarql.com may not have the tools or human resources required to moderate UGC. UGC is included on <a href="http://xarql.com/polr" target="_blank">/polr</a> and <a href="http://xarql.com/chat" target="_blank">/chat</a></p>
      </div>
      <div class="small-card">
        <a style="margin-top:0.5em;" class="centered" href="${domain}/help/main"><span class="italic">Main Help Page</span></a>
      </div>
    </div>
  </div>
</body>
</html>
