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
  <title>Log In ~ xarql</title>
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
    #log_in input
    {
      display: block;
      margin-bottom: 0.5rem;
    }
  </style>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div class="large-card">
        <h4>Log In</h4>
        <form id="log_in" action="${domain}/user/log_in/form" method="POST" accept-charset="UTF-8" spellcheck="false">
          <input type="text" name="username" placeholder="Username" required value="${prefill}">
          <input id="password" type="password" name="password" placeholder="Password" required>
          <input type="submit" class="button" value="Log In">
        </form>
        <p>Need to register an account? <a href="${domain}/user/sign_up">Sign Up</a></p>
      </div>
      <c:if test="${not empty fail}">
        <div class="small-card">
          <h5>Error:</h5>
          <p>${fail}</p>
        </div>
      </c:if>
    </div>
  </div>
</body>
</html>
