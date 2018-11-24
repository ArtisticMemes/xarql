<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
  <title>${code}</title>
  <link rel="stylesheet" type="text/css" href="${domain}/src/common/${theme}-common.min.css">
  <link rel="shortcut icon" href="${domain}/logo.png" type="image/x-icon">
</head>
<body>
<div id="wrapper">
  <div id="column">
    <div class="large-card">
      <h4 class="centered" id="error-title">Error</h5>
      <h1 class="centered" id="error-code">${code}</h1>
      <h5 class="centered">${type}</h4>
      <div class="link-div">
        <span class="link-span">
          <p class="link"><a href="${domain}/help">Help</a></p>
          <p class="link"><a href="#" onclick="history.back()">Return</a></p>
          <p class="link"><a href="${domain}">Home</a></p>
        </span>
      </div>
  </div>
</div>
</div>
</body>
</html>