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
  <title>${code} Error ~ xarql</title>
</head>
<body>
<div id="wrapper">
  <div id="column">
    <div class="large-card" style="width:30rem;">
      <h4 class="centered" id="error-title">Error</h5>
      <h1 class="centered" id="error-code">${code}</h1>
      <h5 class="centered">${type}</h4>
      <p class="centered"><c:out value="${requestScope['javax.servlet.error.message']}"/></p>
      <div class="link-div">
        <span class="link-span">
          <p class="link"><a href="https://xarql.com/help">Help</a></p>
          <p class="link"><a href="#" onclick="history.back()">Return</a></p>
          <p class="link"><a href="https://xarql.com">Home</a></p>
        </span>
      </div>
    </div>
  </div>
</div>
</body>
</html>
