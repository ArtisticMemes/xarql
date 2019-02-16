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
      <div class="large-card">
        <form>
          <input id="message" type="text">
          <input onclick="wsSendMessage();" value="Send" type="button" class="button">
          <input onclick="wsCloseConnection();" value="Close" type="button" class="button">
        </form>
        <br>
        <textarea id="echoText" rows="5" cols="30"></textarea>
      <script type="text/javascript">
        var webSocket = new WebSocket("ws://localhost:8080/xarql.com/chat/websocket");
        var echoText = document.getElementById("echoText");
        echoText.value = "";
        var message = document.getElementById("message");
        webSocket.onopen = function(message){ wsOpen(message);};
        webSocket.onmessage = function(message){ wsGetMessage(message);};
        webSocket.onclose = function(message){ wsClose(message);};
        webSocket.onerror = function(message){ wsError(message);};
        function wsSendMessage() {
          webSocket.send(message.value);
          echoText.value += "↑ " + message.value + "\n";
          message.value = "";
        }
        function wsGetMessage(message) {
          echoText.value += "↓ " + message.data + "\n";
        }
        function wsOpen(message) {
          echoText.value += "Connected ... \n";
        }
        function wsCloseConnection() {
          webSocket.close();
        }
        function wsClose(message) {
          echoText.value += "Disconnected ... \n";
          window.setTimeout(location.reload(), 3000);
        }
        function wsError(message) {
          echoText.value += "Error ... \n";
        }
      </script>
      <c:if test="${account_name != 'Unknown'}">
        <p>Currently logged in as <a href="${domain}/user">@${account_name}</a></p>
      </c:if>
      <p>
        <a href="${domain}/help">Help</a>
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
      <div class="small-card">
        <p class="centered"><a href="https://discordapp.com/invite/HjEfxk7">Discord Server</a></p>
        <p class="centered">This chat is still a work in progress. Visit the server above if you need a more stable place to chat with this site's users and admininstrators.</p>
      </div>
      <div id="data" class="card" style="display:none;">
        <p id="last-id">${lastID}</p>
      </div>
      <c:if test="${testing}">
        <div class="small-card">
          <p class="centered">Page Built In ~${build_timer.done()}ms</p>
        </div>
      </c:if>
    </div>
  </div>
</body>
</html>
