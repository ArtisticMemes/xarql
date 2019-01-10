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
  <meta name="google-signin-client_id" id="google-signin-client_id" content="541616841401-iqluj8gqbu0qvsn3kh7bl93e9mskoff3.apps.googleusercontent.com">
  <title>Authentication ~ xarql</title>
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
      <c:if test="${authorized == true}"><div class="small-card"><h4 class="centered" id="notice">You Are Authorized</h4></div></c:if>
      <c:if test="${authorized == false}"><div class="small-card"><h4 class="centered" id="notice">You Are Not Authorized</h4></div></c:if>
      <div class="large-card">
        <div class="centered"><div data-theme="dark" class="g-signin2" data-onsuccess="onSignIn"></div></div>
        <p class="centered" id="google-p"><a href="#" id="google-sign-out" onclick="signOut();">Sign out</a></p>
        <br><br>
        <form action="${domain}/auth/recaptcha" method="POST">
          <div class="centered">
           	<div data-theme="${theme}" class="g-recaptcha" data-callback="recaptchaCallback" data-sitekey="${recaptcha_key}"></div>
           	<input id='recaptcha_check_empty' required="" tabindex='-1' style='width:50px; height:0; opacity:0; pointer-events:none; position:absolute; bottom:0;'>
          </div>
        </form>
        <p class="centered" id="status-p"><a href="#" id="check-status" onclick="checkStatus();">Check Status</a></p>
          <div class="link-div">
            <span class="link-span">
              <p class="link"><a href="${domain}/chat">chat</a></p>
              <p class="link"><a href="#" onclick="history.back()">Return</a></p>
              <p class="link"><a href="${domain}/polr">polr</a></p>
            </span>
          </div>
      </div>
      <c:if test="${account_name != 'Unknown'}">
        <div class="small-card">
          <p>Currently logged in as @${account_name}</p>
        </div>
      </c:if>
    </div>
  </div>
  <script src="${domain}/src/auth/auth.min.js" defer=""></script>
  <script src="https://apis.google.com/js/platform.js" async="" defer=""></script>
  <script src="https://www.google.com/recaptcha/api.js" async="" defer=""></script>
</body>
</html>