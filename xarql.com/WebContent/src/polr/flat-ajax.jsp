<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div id="results-container">
  <div id="results">
    <c:forEach begin="0" var="post" items="${posts}">
		  <div class="large-card">
		    <p class="overline">ID : ${post.getId()} ~ Date : ${post.getDisplayDate()}</p>
		    <p class="overline">Replies : ${post.getResponses()} ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
        <div id="post-inner-${post.getId()}"<c:if test="${post.getWarning() != 'None'}">style="display:none;"</c:if>>
          <h6>${post.getTitle()}</h6>
          <p>${post.getContent()}</p>
        </div>
        <c:if test="${post.getWarning() != 'None'}">
          <h6 class="warn" id="post-warning-${post.getId()}">${post.getWarning()} Content</h6>
        </c:if>
		    <p>
          <a href="${domain}/polr/${post.getId()}" class="view-link" post-id="${post.getId()}">View</a>
          <c:if test="${post.getAuthor() != 'Unknown'}">⤷${post.getAuthor()}</c:if>
          <c:if test="${post.getWarning() != 'None'}"><a class="reveal-link" data="${post.getId()}">Reveal</a></c:if>
        </p>
		  </div>
		</c:forEach>
  </div>
</div>