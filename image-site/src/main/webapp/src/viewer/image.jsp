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
  <link rel="shortcut icon" href="https://xarql.com/logo.png" type="image/x-icon">
  <title>Image Viewer ~ xarql</title>
  <!-- rich embed tags -->
  <meta property="og:title" content="View This Image">
  <meta property="og:image" content="${domain}/-/static/${type}/${id}/raw.${type}">
  <meta property="og:url" content="${domain}/${loc}">
  <meta property="og:site_name" content="xarql">
  <meta name="twitter:site" content="@xarql">
  <meta name="theme-color" content="#f2f2f2">
  <link type="application/json+oembed" href="${domain}/-/src/viewer/embed-options.json">
  <style>
#wrapper,html,body{border:0px;padding:0px;margin:0px;overflow-x:hidden;display:flex;justify-content:center;background-color:#000000;visibility:visible}html,body{width:100vw;height:100vh}html{box-sizing:border-box}*,*:before,*:after{box-sizing:inherit}#wrapper{width:100%;height:100%}a{text-decoration:none;color:#0000ff;max-width:100%;max-height:100%;display:block}a:hover{text-decoration:underline}a:active{position:relative;top:0.05em}a:visited{color:#0000ff}img{max-width:100vw;max-height:100vh;object-fit:scale-down}
  </style>
</head>
<body>
  <div id="wrapper">
    <a href="${domain}/-/static/${type}/${id}/raw.${type}">
      <img src="${domain}/-/static/${type}/${id}/raw.${type}">
    </a>
  </div>
</body>
</html>
