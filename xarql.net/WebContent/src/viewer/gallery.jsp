<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
  <link rel="stylesheet" type="text/css" id="theme-styles" href="https://xarql.com/src/common/light-common.min.css">
  <link rel="shortcut icon" href="https://xarql.com/logo.png" type="image/x-icon">
  <title>Image Gallery ~ xarql</title>
  <style>
#wrapper,html,body{border:0px;padding:0px;margin:0px;overflow-x:hidden;display:flex;justify-content:space-evenly;align-content:flex-start;flex-direction:row;flex-wrap:wrap;background-color:#f2f2f2;visibility:visible}html,body{width:100vw;height:100vh}html{box-sizing:border-box}*,*:before,*:after{box-sizing:inherit}#wrapper{width:100%;height:100%}a{text-decoration:none;color:#0000ff;max-width:100%;max-height:100%;display:block}a:hover{text-decoration:underline}a:active{position:relative;top:0.05em}a:visited{color:#0000ff}.box{margin:0.5rem;flex-shrink:1;flex-basis:30rem;max-width:20vw;max-height:20vw}.box a{width:100%;height:100%}.box img{width:100%;height:100%;object-fit:cover}@media only screen and (min-width: 50rem){.box{min-width:25rem;min-height:25rem}}@media only screen and (max-width: 50rem){.box{min-width:90vw;min-height:90vw}}
  </style>
</head>
<body>
  <div id="wrapper">
    <c:forEach begin="0" var="image" items="${images}">
      <div class="box">
        <a href="${image.getLink()}">
          <img src="${image.getRawLink()}">
        </a>
      </div>
	  </c:forEach>
  </div>
</body>
</html>