<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
  <title>Find : ${query} ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
  <link rel="stylesheet" type="text/css" href="${domain}/src/common/${theme}-common.min.css">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous" defer=""></script>
  <script src="${domain}/src/common/jscookie.js" defer=""></script>
  <script src="${domain}/src/polr/find.min.js" defer=""></script>
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
    <div class="large-card" style="x-overflow:hidden;">
      <h4>Search</h4>
      <form action="${domain}/polr/find" method="GET" id="find-form" accept-charset="UTF-8">
         <input autofocus spellcheck="true" autocomplete="off" type="text" name="q" placeholder="Phrase" maxlength="128" required="" style="width:100%;">
         <br/>
         <input id="submit" class="button" type="submit" value="Find"/> <span class="status"></span>
       </form>
    </div>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <div id="results">
      <c:forEach begin="0" var="post" items="${posts}">
  		  <div class="large-card">
  		    <p class="overline">ID : ${post.getId()} ~ Date : ${post.getDisplayDate()}</p>
  		    <p class="overline">Replies : ${post.getResponses()} ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
          <c:if test="${post.isExpired()}"><p class="overline"><span class="warn">Expired</span></p></c:if>
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
