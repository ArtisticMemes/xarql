<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div id="full">
  <div id="messages">
    <c:forEach begin="0" var="message" items="${messages}">
      <div class="small-card" style="background-color:${message.backgroundColor()}">
        <p style="color:${message.textColor()}">${message.getMessage()}</p>
      </div>
	</c:forEach>
  </div>
  <p id="last-id">${lastID}</p>
</div>