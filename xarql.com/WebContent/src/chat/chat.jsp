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
  <title>Chat ~ xarql</title>
  <link rel="stylesheet" type="text/css" id="theme-styles" href="${domain}/src/common/${theme}-common.min.css">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous" defer=""></script>
  <script src="${domain}/src/common/jscookie.js" defer=""></script>
  <script src="${domain}/src/chat/chat.min.js" defer=""></script>
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
    #column {
      height: 100vh;
      max-height: 100vh;
      overflow: auto;
    }
    #text-entry {
      position: -webkit-sticky;
      position: sticky;
      bottom: 1rem;
    }
  </style>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div id="messages">
      </div>
      <div id="updates">
      </div>
      <div class="small-card" id="text-entry">
        <div id="users">
        </div>
        <form id="message-form">
          <input id="message" autocomplete="off" spellcheck="true" type="text" name="message" placeholder="Message" maxlength="128" style="width:100%;">
          <input id="send-button" value="Send" type="button" class="button">
        </form>
      <c:if test="${account_name != 'Unknown'}">
        <p>Currently logged in as <a href="${domain}/user">@${account_name}</a></p>
      </c:if>
      <p>
        <a href="${domain}/help/chat">Help</a>
        <span class="status" style="display:none;"></span>
        <span class="ajax-bar" style="display:none">
          <a id="option-pane-open-button" style="margin-left:1em;">Options</a>
        </span>
      </p>
      </div>
      <div class="large-card" id="option-pane" style="display:none;position:relative;">
        <table>
          <tr><td><p>Size</td><td><span style="letter-spacing:1em">:</span></td><td><a class="spacey-link" id="text-up">↑</a><a class="spacey-link" id="text-dn">↓</a></p></td></tr>
          <tr><td><p>Font</td><td>:</td><td><a class="spacey-link" id="font-light-button">Light</a><a class="spacey-link" id="font-normal-button">Normal</a></p></td></tr>
          <tr><td><p>Theme</td><td>:</td>
          <td>
            <a class="theme-button" id="light-theme-button" data="light">Light</a>
            <a class="theme-button" id="dark-theme-button" data="dark">Dark</a>
            <a class="theme-button" id="rainbow-theme-button" data="rainbow">Rainbow</a>
          </td></tr>
        </table>
        <p><span style="position:absolute;bottom:0.8rem;right:2rem;"><a id="option-pane-close-button">Close</a></span></p>
      </div>
    </div>
  </div>
</body>
</html>
