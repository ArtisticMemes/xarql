<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Chat</title>
  <style>
#wrapper, html, body {
  font-family: 'Roboto';
  display: flex;
  visibility: visible;
  overflow-x: hidden;
  min-height: 100vh;
  margin: 0;
  padding: 0;
  border: 0;
  justify-content: center;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
#wrapper {
  width: 100%;
  max-width: 100%;
}
html, body {
  width: 100vw;
  max-width: 100vw;
}
*, *:before, *:after {
  font-display:swap
  -webkit-box-sizing: inherit;
  box-sizing: inherit;
}
#column {
  max-width: 100%;
}
.card {
  width: 40rem;
  max-width: 100%;
}
  </style>
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
  <no-script>
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.css">
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/card/small.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto+Mono">
  </no-script>
</body>
</html>