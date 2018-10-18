<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="google-signin-client_id" id="google-signin-client_id" content="541616841401-iqluj8gqbu0qvsn3kh7bl93e9mskoff3.apps.googleusercontent.com">
  <title>Authentication ~ xarql</title>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <c:if test="${authorized == true}"><div class="small-card"><h4 class="centered" id="notice">You Are Authorized</h4></div></c:if>
      <c:if test="${authorized == false}"><div class="small-card"><h4 class="centered" id="notice">You Are Not Authorized</h4></div></c:if>
      <div class="large-card">
        <div class="centered"><div class="g-signin2" data-onsuccess="onSignIn"></div></div>
        <p class="centered" id="google-p"><a href="#" id="google-sign-out" onclick="signOut();">Sign out</a></p>
        <br><br>
        <form action="http://xarql.com/auth/recaptcha" method="POST">
          <div class="centered">
           	<div class="g-recaptcha" data-callback="recaptchaCallback" data-sitekey="6Ldv_V8UAAAAAA8oid2KDaOQqTu4kFFHDvhK9Blt"></div>
           	<input id='recaptcha_check_empty' required="" tabindex='-1' style='width:50px; height:0; opacity:0; pointer-events:none; position:absolute; bottom:0;'>
          </div>
        </form>
        <p class="centered" id="status-p"><a href="#" id="check-status" onclick="checkStatus();">Check Status</a></p>
          <div class="link-div">
            <span class="link-span">
              <p class="link"><a href="http://xarql.com/chat">chat</a></p>
              <p class="link"><a href="#" onclick="history.back()">Return</a></p>
              <p class="link"><a href="http://xarql.com/polr">polr</a></p>
            </span>
          </div>
      </div>
    </div>
  </div>
  <noscript id="default-styles">
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.min.css">
    <script>defaultStylesInjected = true;</script>
  </noscript>
  <script>
      var loadDeferredStyles = function() {
        var addStylesNode = document.getElementById("default-styles");
        var replacement = document.createElement("div");
        replacement.innerHTML = addStylesNode.textContent;
        replacement.id = "styles";
        document.body.appendChild(replacement);
        addStylesNode.parentElement.removeChild(addStylesNode);
      };
      var raf = window.requestAnimationFrame || window.mozRequestAnimationFrame ||
          window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
      if (raf) raf(function() { window.setTimeout(loadDeferredStyles, 0); });
      else window.addEventListener('load', loadDeferredStyles);
      var defaultStylesInjected = false;
  </script>
  <script src="http://xarql.com/src/auth/auth.min.js" defer=""></script>
  <script src="https://apis.google.com/js/platform.js" async="" defer=""></script>
  <script src="https://www.google.com/recaptcha/api.js" async="" defer=""></script>
</body>
</html>