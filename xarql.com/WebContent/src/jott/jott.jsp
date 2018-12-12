<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html id="html">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
  <title>Jott ~ xarql</title>
  <link rel="stylesheet" type="text/css" id="theme-styles" href="${domain}/src/common/${theme}-common.min.css">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous" defer=""></script>
  <script src="${domain}/src/common/jscookie.js" defer=""></script>
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
      <div id="jott-card">
        <form id="jott-form" action="${domain}/jott" method="POST" accept-charset="UTF-8">
          <textarea id="jott-text" autofocus autocomplete="off" spellcheck="true" name="content" cols="64" rows="16" tabindex="0" placeholder="Start typing..." wrap="soft"></textarea>
          <input id="submit" class="button" type="submit" value="Process"/>
        </form>
      </div>
      <c:if test="${not empty output}">
        <div class="large-card">
          <p>${output}</p>
          <p class="warn">Output</p>
        </div>
        <div class="large-card">
          <xmp>${output}</xmp> <!-- Only ever use xmp for displaying raw html -->
          <p class="warn">Raw HTML</p>
        </div>
      </c:if>
      <div class="small-card">
        <p class="centered">Page Built In ~${build_timer.done()}ms</p>
      </div>
    </div>
  </div>
</body>
</html>