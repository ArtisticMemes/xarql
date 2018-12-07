<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html id="html">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>${posts.get(0).getTitleText()} ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
  <link rel="stylesheet" type="text/css" id="theme-styles" href="${domain}/src/common/${theme}-common.min.css">
  <link rel="shortcut icon" href="${domain}/logo.png" type="image/x-icon">
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div class="large-card">
        <h4>Categorize</h4>
        <form action="${domain}/polr/hash" method="GET" id="tag-form">
           <input type="text" name="tag" placeholder="Hash Tag" maxlength="24" required="" style="width:100%;" tabindex="0">
           <br/>
           <input id="submit" class="button" type="submit" value="Find" tabindex="0"/> <span class="status"></span>
         </form>
      </div>
      <div id="posts">
		    <c:forEach var="post" items="${posts}">
		      <div class="large-card">
		        <p class="overline">ID : ${post.getId()} ~ Date : ${post.getDisplayDate()}</p>
		        <p class="overline">Replies : ${post.getResponses()} ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
		        <h6>${post.getTitle()}</h6>
		        <p>${post.getContent()}</p>
		        <p><a href="${domain}/polr/${post.getId()}" class="view-link" post-id="${post.getId()}">View</a></p>
		      </div>
		    </c:forEach>
	    </div>
    </div>
  </div>
</body>
</html>