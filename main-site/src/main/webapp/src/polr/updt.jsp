<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div id="full">
  <div id="main-post-container">
    <div id="main-post">
        <c:forEach begin="0" var="post" items="${posts}" end="0">
            <div class="large-card">
                <p class="overline">ID: <span id="main-post-id">${post.getId()}</span> ~ <a href="${domain}/polr/${post.getAnswers()}" class="view-link" post-id="${post.getAnswers()}">Replied To: ${post.getAnswers()}</a>~ ${post.getDisplayDate()}</p>
                <p class="overline">${post.replyStats()}</p>
                <h6>${post.getTitle()}</h6>
                <p>${post.getContent()}</p>
                <c:if test="${post.isExpired()}">
                    <p class="warn">This post has expired. You can not reply to it.</p>
                </c:if>
                <p>
                    <a class="report-link" href="${domain}/flag?id=${post.getId()}">Report</a>
                    <c:if test="${post.getAuthor() != 'Unknown'}">
                        ⤷<a href="${domain}/user/view?name=${post.getAuthor()}">${post.getAuthor()}</a>
                    </c:if>
                </p>
            </div>
        </c:forEach>
    </div>
  </div>
  <div id="replies-container">
    <div id="replies">
        <c:forEach begin="1" var="post" items="${posts}">
            <div class="large-card">
                <p class="overline">${post.replyStats()}</p>
                <c:if test="${post.isExpired()}">
                    <p class="overline"><span class="warn">Expired</span></p>
                </c:if>
                <div id="post-inner-${post.getId()}" <c:if test="${post.getWarning() != 'None'}">style="display:none;"</c:if>>
                    <h6>${post.getTitle()}</h6>
                    <p>${post.getContent()}</p>
                </div>
                <c:if test="${post.getWarning() != 'None'}">
                    <h6 class="warn" id="post-warning-${post.getId()}">${post.getWarning()} Content</h6>
                </c:if>
                <p>
                    <a href="${domain}/polr/${post.getId()}" class="view-link" post-id="${post.getId()}">View</a>
                    <c:if test="${post.getAuthor() != 'Unknown'}">
                        ⤷<a href="${domain}/user/view?name=${post.getAuthor()}">${post.getAuthor()}</a>
                    </c:if>
                    <c:if test="${post.getWarning() != 'None'}"><a class="reveal-link" data="${post.getId()}">Reveal</a></c:if>
                </p>
            </div>
        </c:forEach>
    </div>
</div>
