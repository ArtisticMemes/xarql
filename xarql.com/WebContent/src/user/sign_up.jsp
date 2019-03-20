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
  <title>Sign Up ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        <h4>Sign Up</h4>
        <form class="user-form" action="${domain}/user/sign_up/form" method="POST" accept-charset="UTF-8" spellcheck="false">
          <input type="text" name="username" placeholder="Username" minlength="1" maxlength="128" required value="${prefill}">
          <input id="password" type="password" name="password" placeholder="Password" minlength="6" maxlength="128" required>
          <input id="password_confirmation" type="password" name="retype" placeholder="Retype Password" required>
          <input type="submit" class="button" value="Sign Up">
        </form>
        <p>Want to log in to an account? <a href="${domain}/user/log_in">Log In</a></p>
      </div>
      <c:if test="${not empty fail}">
        <div class="small-card">
          <h5 class="warn">Error:</h5>
          <p>${fail}</p>
        </div>
      </c:if>
      <c:if test="${account_name != 'Unknown'}">
        <div class="small-card">
          <p>Currently logged in as @${account_name}</p>
        </div>
      </c:if>
    </div>
  </div>
  <script>
  var password = document.getElementById("password"), confirm_password = document.getElementById("password_confirmation");

  function validatePassword() {
    if(password.value != confirm_password.value) {
      confirm_password.setCustomValidity("Passwords Don't Match");
    }
    else {
      confirm_password.setCustomValidity('');
    }
  }

  password.onchange = validatePassword;
  confirm_password.onkeyup = validatePassword;
  </script>
</body>
</html>
