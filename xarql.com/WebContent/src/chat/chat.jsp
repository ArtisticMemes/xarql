<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
  <title>Chat</title>
  <link rel="stylesheet" type="text/css" href="${domain}/src/common/common.min.css">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous" defer=""></script>
  <script src="${domain}/src/chat/chat.min.js" defer=""></script>
  <link rel="shortcut icon" href="/logo.png" type="image/x-icon">
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <div id="messages">
        <c:forEach begin="0" var="message" items="${messages}">
          <div class="small-card" style="background-color:${message.backgroundColor()}">
            <p style="color:${message.textColor()}">${message.getMessage()} <span class="overline" style="text-align:left;width:100%">${message.timeSince()}</span></p>
		  </div>
	    </c:forEach>
	  </div>
	  <div class="large-card" style="x-overflow:hidden;">
       <form action="${domain}/chat/send" method="POST" id="message-form">
         <input type="text" name="message" placeholder="Message" maxlength="256" style="width:100%;">
         <input class="button" id="submit" type="submit" value="Send"/> <input class="button" type="reset" value="Clear"/>
       </form>
       <c:if test="${not authenticated}">
         <form action="${domain}/auth/recaptcha" method="POST" id="recaptcha-form">
           <div style="position:relative;">
             <div class="g-recaptcha" data-callback="recaptchaCallback" data-sitekey="6Ldv_V8UAAAAAA8oid2KDaOQqTu4kFFHDvhK9Blt"></div>
             <input id='recaptcha_check_empty' required="" tabindex='-1' style='width:50px; height:0; opacity:0; pointer-events:none; position:absolute; bottom:0;'>
           </div>
         </form>
         <script src="${domain}/src/auth/auth.min.js" defer=""></script>
         <script src="https://www.google.com/recaptcha/api.js" async="" defer=""></script>
       </c:if>
       <p><a href="${domain}/help">Help</a> <span class="status"></span></p>
      </div>
      <div id="data" class="card" style="display:none;">
        <p id="last-id">${lastID}</p>
      </div>
    </div>
  </div>
</body>
</html>