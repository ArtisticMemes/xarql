<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html id="html">
<head>
  <!-- Global site tag (gtag.js) - Google Analytics -->
  <script async src="https://www.googletagmanager.com/gtag/js?id=${google_analytics_id}"></script>
  <script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', '${google_analytics_id}');
  </script>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Posts from @${name} ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
  <link rel="stylesheet" type="text/css" id="theme-styles" href="${domain}/src/common/${theme}-common.min.css">
  <link rel="shortcut icon" href="${domain}/logo.png" type="image/x-icon">
  <style id="font-size">
    html, body {
      font-size: ${font_size}
    }
  </style>
  <style id="font-weight">
    p {
      font-weight: ${font_weight}
    }
    h6, .bold {
      font-weight: ${font_weight + 200}
    }
  </style>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div class="large-card">
        <h4>@${name}</h4>
        <form action="${domain}/polr/user" method="GET" id="name-form" accept-charset="UTF-8">
           <input autofocus spellcheck="true" autocomplete="off" type="text" name="name" placeholder="@username" maxlength="128" required="" style="width:100%;" tabindex="0">
           <br/>
           <input id="submit" class="button" type="submit" value="Grab" tabindex="0"/> <span class="status"></span>
         </form>
      </div>
      <c:if test="${posts.size() == 0}">
        <div class="small-card">
          <p>No posts were made by this user</p>
        </div>
      </c:if>
      <div id="posts">
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
              <c:if test="${post.getAuthor() != 'Unknown'}">
                ⤷<a href="${domain}/user/view?name=${post.getAuthor()}">${post.getAuthor()}</a>
              </c:if>
              <c:if test="${post.getWarning() != 'None'}"><a class="reveal-link" data="${post.getId()}">Reveal</a></c:if>
            </p>
    		  </div>
    		</c:forEach>
	    </div>
      <c:if test="${testing}">
        <div class="small-card">
          <p class="centered">Page Built In ~${build_timer.done()}ms</p>
        </div>
      </c:if>
    </div>
  </div>
</body>
</html>