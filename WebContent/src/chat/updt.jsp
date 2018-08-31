<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="full">
  <div id="messages">
    <c:forEach begin="0" var="message" items="${messages}">
      <div class="card" style="background-color:#${message.backgroundColor()}">
        <p style="color:#${message.textColor()}">${message.getMessage()} <span class="overline" style="text-align:left;width:100%">${message.timeSince()}</span></p>
      </div>
	</c:forEach>
  </div>
</div>    