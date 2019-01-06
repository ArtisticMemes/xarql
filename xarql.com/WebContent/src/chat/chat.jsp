<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <div id="messages">
        <c:forEach begin="0" var="message" items="${messages}">
          <div class="small-card" style="background-color:${message.backgroundColor()}">
            <p style="color:${message.textColor()}">${message.getMessage()}</p>
		  </div>
	    </c:forEach>
	  </div>
	  <div class="large-card" style="x-overflow:hidden;">
       <form action="${domain}/chat/send" method="POST" id="message-form" accept-charset="UTF-8">
         <input autofocus spellcheck="true" autocomplete="off" type="text" name="message" placeholder="Message" maxlength="256" style="width:100%;">
         <input class="button" id="submit" type="submit" value="Send"/>
       </form>
       <c:if test="${not authenticated}">
         <form action="${domain}/auth/recaptcha" method="POST" id="recaptcha-form">
           <div style="position:relative;">
             <div data-theme="${theme}" class="g-recaptcha" data-callback="recaptchaCallback" data-sitekey="${recaptcha_key}"></div>
             <input id='recaptcha_check_empty' required="" tabindex='-1' style='width:50px; height:0; opacity:0; pointer-events:none; position:absolute; bottom:0;'>
           </div>
         </form>
         <script src="${domain}/src/auth/auth.min.js" defer=""></script>
         <script src="https://www.google.com/recaptcha/api.js" async="" defer=""></script>
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
          <tr><td><p>Size</td><td><span style="letter-spacing:1rem">:</span></td><td><a id="text-up">↑</a><span style="letter-spacing:1rem"> </span><a id="text-dn">↓</a></p></td></tr>
          <tr><td><p>Font</td><td><span style="letter-spacing:1rem">:</span></td><td><a id="font-light-button">Light</a><a id="font-normal-button">Normal</a></p></td></tr>
          <tr><td><p>Theme</td><td><span style="letter-spacing:1rem">:</span></td>
          <td>
            <a class="theme-button" id="light-theme-button" data="light">Light</a>
            <a class="theme-button" id="dark-theme-button" data="dark">Dark</a>
            <a class="theme-button" id="purple-theme-button" data="purple">Purple</a>
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