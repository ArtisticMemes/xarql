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
  <meta id="domain" value="${domain}">
  <link rel="stylesheet" type="text/css" id="theme-styles" href="https://xarql.com/src/common/light-common.min.css">
  <link rel="shortcut icon" href="https://xarql.com/logo.png" type="image/x-icon">
  <title>Upload An Image ~ xarql</title>
</head>
<body>
<div id="wrapper">
  <div id="column">
    <div class="large-card">
      <h4>Upload An Image</h4>
      <form id="file-form" action="${domain}/-/upload_endpoint" method="POST" enctype="multipart/form-data">
        <input type="file" required accept="image/jpeg,image/x-png,audio/x-mpeg,.png,.jpg,.jpeg,.mp3" multiple="false" name="file" id="file-browser">
        <input type="submit" value="Submit" id="submit" class="button">
      </form>
      <c:if test="${not authenticated}">
         <form action="${domain}/-/auth/recaptcha" method="POST" id="recaptcha-form">
           <div style="position:relative;">
             <div data-theme="light" class="g-recaptcha" data-callback="recaptchaCallback" data-sitekey="${recaptcha_key}"></div>
             <input id='recaptcha_check_empty' required="" tabindex='-1' style='width:50px; height:0; opacity:0; pointer-events:none; position:absolute; bottom:0;'>
           </div>
         </form>
         <script src="${domain}/-/src/auth/recaptcha.min.js" defer=""></script>
         <script src="https://www.google.com/recaptcha/api.js" async="" defer=""></script>
       </c:if>
      <p><a class="spacey-link" href="https://xarql.com/help/net">Help</a> <a class="spacey-link" href="${domain}/-/gallery">Gallery</a><a class="spacey-link" href="https://xarql.com">xarql.com</a></p>
    </div>
  </div>
</div>
</body>
</html>
