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
  <title>xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="google-site-verification" content="d25C1s7NgA2f8t1_semFUqrm7bvPjKZhgSBUonISMrA" />
  <meta id="domain" value="${domain}">
  <link rel="stylesheet" type="text/css" href="${domain}/src/common/${theme}-common.min.css">
  <link rel="shortcut icon" href="${domain}/logo.png" type="image/x-icon">
  <link rel="apple-touch-icon" href="${domain}/logo-circle.png">
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
        <h3 id="xarql" class="centered">XARQL</h3>
        <p id="moto" class="centered">zärˈkəl</p>
        <div id="breaker"></div>
        <p class="overline">Pages</p>
        <p><a href="${domain}/polr">polr</a></p>
        <p><a href="${domain}/chat">chat</a></p>
        <p><a href="${domain}/user">user</a></p>
        <p><a href="${domain}/help">help</a></p>
      </div>
      <div class="small-card">
        <h6>Wondering What's New?</h6>
        <p>Follow <a href="https://twitter.com/xarql">xarql's official Twitter</a> (@xarql)</p>
      </div>
      <div class="small-card">
        <h6>Upload An Image</h6>
        <p><a href="${domain}/help/net">Guide</a> 🠖 <a href="https://xarql.net/-/upload">xarql.net</a></p>
      </div>
      <div class="large-card">
        <h4>Statistics</h4>
        <table>
          <tr><td>Total Sessions</td><td style="padding-left:1rem;">${total_sessions}</td></tr>
          <tr><td>Auth Sessions</td><td style="padding-left:1rem;">${auth_sessions}</td></tr>
          <tr><td>Live Chats</td><td style="padding-left:1rem;">${live_chats}</td></tr>
        </table>
      </div>
    </div>
  </div>
</body>
</html>
