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
  <title>Your Account ~ xarql</title>
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
  <style>
    .user-form .button
    {
      background-color: red;
    }
  </style>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div class="large-card">
        <h4>${username}</h4>
        <p><a href="${domain}/user/view?name=${username}">Public Profile</a></p>
        <p><a href="${domain}">Home</a></p>
      </div>
      <c:if test="${not empty msg}">
        <div class="small-card">
          <p class="centered">${msg}</p>
        </div>
      </c:if>
      <c:if test="${not empty fail}">
        <div class="small-card">
          <h5 class="warn">Error:</h5>
          <p>${fail}</p>
        </div>
      </c:if>
      <div class="large-card">
        <h6>Log Out</h6>
        <p>This will remove your session from the server. Use a recaptcha or log in again to post.</p>
        <form action="${domain}/user/act?type=log_out" method="POST" accept-charset="UTF-8">
          <input type="submit" class="button" value="Log Out">
        </form>
      </div>
      <div class="large-card">
        <h6 class="warn">Change Password</h6>
        <p>Enter your current password and a new password twice to change your password. Your password can't be recovered by xarql.com. <a href="${domain}/help/account">Learn More</a></p>
        <form class="user-form" action="${domain}/user/act?type=password_change" method="POST" accept-charset="UTF-8" spellcheck="false">
          <input type="password" name="password" placeholder="Current Password" minlength="6" maxlength="128" required>
          <input id="password" type="password" name="new_password" placeholder="New Password" minlength="6" maxlength="128" required>
          <input id="password_confirmation" name="retype" type="password" placeholder="Retype Replacement" required>
          <input type="submit" class="button" value="Change">
        </form>
      </div>
      <div class="large-card">
        <h6 class="warn">Delete</h6>
        <p>Enter your password and username to delete your account. Other users may claim your username after you do this. <a href="${domain}/help/account">Learn More</a></p>
        <form class="user-form" action="${domain}/user/act?type=delete" method="POST" accept-charset="UTF-8" spellcheck="false">
          <input type="text" name="username" placeholder="Username" required>
          <input id="password" type="password" name="password" placeholder="Password" required>
          <input type="submit" class="button" value="Delete">
        </form>
      </div>
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
