<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html id="html">
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
  <style>
    #tf-link {
      margin-left: 2rem;
    }
  </style>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div id="jott-card">
        <form id="jott-form" action="${domain}/jott/md" method="POST" accept-charset="UTF-8">
          <textarea id="jott-text" autofocus autocomplete="off" spellcheck="true" name="content" cols="64" rows="16" tabindex="0" placeholder="Start typing..." wrap="soft">${input}</textarea>
          <input id="submit" class="button" type="submit" value="Process"/>
          <p style="margin-left:1rem;display:inline;">
            <a id="markdown" href="${domain}/help/markdown" target="_blank">Markdown Guide</a>
            <a href="${domain}/jott" id="tf-link">Use TextFormatter</a>
          </p>
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
    </div>
  </div>
</body>
</html>