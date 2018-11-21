<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div id="results-container">
  <div id="results">
    <c:forEach begin="0" var="post" items="${posts}">
      <div class="large-card">
        <p class="overline">ID : ${post.getId()} ~ <a href="${domain}/polr/${post.getAnswers()}">Replied To : ${post.getAnswers()}</a> ~ Date : ${post.getDisplayDate()}</p>
        <p class="overline">Replies : ${post.getResponses()} ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
        <h6>${post.getTitle()}</h6>
        <p>${post.getContent()}</p>
        <p><a href="${domain}/polr/${post.getId()}">View</a></p>
      </div>
    </c:forEach>
  </div>
</div>