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
  <title>Help Main Page ~ xarql</title>
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
        <h2 class="centered">Home</h2><p class="centered">Welcome to the xarql wiki!</p><p>It's suggested that if you're unfamiliar with the site that you browse through it while reading these pages to gain deeper knowledge about what you're viewing.</p>
      </div>
      <div class="large-card">
        <h3>Topics</h3>
        <p><a href="${domain}/help/polr">/polr</a></p>
        <p><a href="${domain}/help/account">/user</a></p>
        <p><a href="${domain}/help/chat">/chat</a></p>
        <p><a href="${domain}/help/net">.net</a></p>
        <p><a href="${domain}/help/markdown">MarkDown</a></p>
        <p><a href="${domain}/help/textformatter">TextFormatter</a></p>
        <p><a href="${domain}/help/parameters">HTML Parameters</a></p>
      </div>
    </div>
  </div>
</body>
</html>
