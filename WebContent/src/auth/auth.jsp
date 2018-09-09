<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="google-signin-client_id" id="google-signin-client_id" content="541616841401-iqluj8gqbu0qvsn3kh7bl93e9mskoff3.apps.googleusercontent.com">
  <script src="https://www.google.com/recaptcha/api.js" async="" defer=""></script>
  <title>Authentication ~ xarql</title>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <c:if test="${authorized == true}"><div class="card"><h4 class="centered">You Are Authorized</h4></div></c:if>
      <div class="centered"><div class="g-signin2" data-onsuccess="onSignIn"></div></div>
      <p class="centered" id="google-p"><a href="#" id="google-sign-out">Sign out</a></p>
      <script>
      function signOut() {
    	  var auth2 = gapi.auth2.getAuthInstance();
    	  auth2.signOut().then(function () {
    		  console.log('User signed out.');
    		});
    	}
      </script>
      <br><br>
      <form action="http://xarql.com/auth/recaptcha" method="POST">
         <div style="position:relative;">
         	<div class="g-recaptcha" data-callback="recaptchaCallback" data-sitekey="6Ldv_V8UAAAAAA8oid2KDaOQqTu4kFFHDvhK9Blt"></div>
         	<input id='recaptcha_check_empty' required="" tabindex='-1' style='width:50px; height:0; opacity:0; pointer-events:none; position:absolute; bottom:0;'>
         </div>
         <div class="centered"><input id="submit" type="submit" value="Authorize By Recaptcha"/></div>
      </form>
      <script>
      function recaptchaCallback()
      {
    	  document.getElementById('recaptcha_check_empty').value = 1;
      }
      </script>
    </div>
  </div>
  <noscript id="default-styles">
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.css">
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/card/small.css">
    <script>defaultStylesInjected = true;</script>
  </noscript>
  <noscript id="fonts">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <!-- <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Code+Pro"> -->
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
      var loadDeferredFonts = function() {
    	  var addFontsNode = document.getElementById("fonts");
    	  var replacement = document.createElement("div");
    	  replacement.innerHTML = addFontsNode.textContent;
    	  replacement.id = "fonts";
    	  document.body.appendChild(replacement);
    	  addFontsNode.parentElement.removeChild(addFontsNode);
      };
      var raf = window.requestAnimationFrame || window.mozRequestAnimationFrame ||
          window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
      if (raf) raf(function() { window.setTimeout(loadDeferredStyles, 0); });
      else window.addEventListener('load', loadDeferredStyles);
      if (raf) raf(function() { window.setTimeout(loadDeferredFonts, 0); });
      else window.addEventListener('load', loadDeferredFonts);
      var defaultStylesInjected = false;
  </script>
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous" defer=""></script>
  <script src="http://xarql.com/src/auth/auth.js" defer=""></script>
  <script src="https://apis.google.com/js/platform.js" defer></script>
</body>
</html>