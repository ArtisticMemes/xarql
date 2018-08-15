<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Chat</title>
  <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.css">
  <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/card/small.css">
  <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
  <!-- <script src='https://www.google.com/recaptcha/api.js'></script> -->
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <c:forEach begin="0" var="message" items="${messages}">
        <div class="card" style="background-color:#${message.backgroundColor()}">
          <p style="color:#${message.textColor()}">${message.getMessage()} <span class="overline" style="text-align:left;width:100%">${message.timeSince()}</span></p>
		</div>
	  </c:forEach>
	  <div class="card" style="x-overflow:hidden;">
       <form action="/chat/send" method="POST">
         <input type="text" name="message" placeholder="Message" maxlength="256" style="width:100%;">
         <input id="submit" type="submit" value="Send" /> <input type="reset" value="Clear" />
       </form>
       <p><a href="http://xarql.com/help">Help</a></p>
      </div>
    </div>
  </div>
</body>
</html>