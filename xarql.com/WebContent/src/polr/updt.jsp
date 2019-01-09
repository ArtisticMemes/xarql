<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div id="full">
  <div id="main-post-container">
    <div id="main-post">
      <c:forEach begin="0" end="0" var="post" items="${posts}">
        <div class="large-card">
          <p class="overline">ID : <span id="main-post-id">${post.getId()}</span> ~ <a href="${domain}/polr/${post.getAnswers()}" class="view-link" post-id="${post.getAnswers()}">Replied To : ${post.getAnswers()}</a> ~ Date : ${post.getDisplayDate()}</p>
          <p class="overline">Replies : <span id="reply-count">${post.getResponses()}</span> ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
          <h6 id="main-post-title">${post.getTitle()}</h6>
          <p>${post.getContent()}</p>
          <p><a class="report-link" href="${domain}/flag?id=${post.getId()}">Report</a><c:if test="${post.getAuthor() != 'Unknown'}">⤷${post.getAuthor()}</c:if></p>
        </div>
      </c:forEach>
    </div>
  </div>
  <div id="replies-container">
    <div id="replies">
      <c:forEach begin="1" var="post" items="${posts}">
        <div class="large-card">
          <p class="overline">ID : ${post.getId()} ~ Date : ${post.getDisplayDate()}</p>
          <p class="overline">Replies : ${post.getResponses()} ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
          <h6>${post.getTitle()}</h6>
          <p>${post.getContent()}</p>
          <p><a href="${domain}/polr/${post.getId()}" class="view-link" post-id="${post.getId()}">View</a><c:if test="${post.getAuthor() != 'Unknown'}">⤷${post.getAuthor()}</c:if></p>
        </div>
      </c:forEach>
    </div>
  </div>
</div>