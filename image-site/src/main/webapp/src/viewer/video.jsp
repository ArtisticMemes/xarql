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
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="shortcut icon" href="https://xarql.net/-/src/icon/logo.png" type="image/x-icon">
  <title>Video Watcher ~ xarql</title>
  <style>
#wrapper,html,body{border:0px;padding:0px;margin:0px;overflow-x:hidden;display:flex;justify-content:center;background-color:#000000;visibility:visible}html,body{width:100vw;height:100vh}html{box-sizing:border-box}*,*:before,*:after{box-sizing:inherit}body{font-family:-apple-system, system-ui, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol"}#wrapper{width:100%;height:100%}#column{display:block;max-width:100%}a{text-decoration:none;color:#287bff;max-width:100%;max-height:100%;display:block}a:hover{text-decoration:underline}a:active{position:relative;top:0.05em}a:visited{color:#287bff}video{max-width:100vw;max-height:80vh;width:50em;height:50em}p{color:#000;text-align:center;font-family:inherit}
  </style>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <video controls="true" type="video/${type}" preload="auto" autoplay="true">
        <source src="${domain}/-/static/${type}/${id}/raw.${type}" type="video/${type}">
        <p>Your browser doesn't suppport the video element. Please use a newer broswer</p>
      </video>
      <p><a href="${domain}/-/static/${type}/${id}/raw.${type}">Download</a></p>
    </div>
  </div>
</body>
</html>
