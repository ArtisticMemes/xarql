<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>${query} ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.min.css">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous" defer=""></script>
  <script src="http://xarql.com/src/common/jscookie.js" defer=""></script>
  <script src="http://xarql.com/src/polr/find.min.js" defer=""></script>
  <link rel="shortcut icon" href="http://xarql.com/logo.png" type="image/x-icon">
</head>
<body>
<div id="wrapper">
  <div id="column">
    <div class="large-card" style="x-overflow:hidden;">
      <h4>Search</h4>
      <form action="http://xarql.com/polr/find" method="GET" id="find-form">
         <input type="text" name="q" placeholder="Phrase" maxlength="64" required="" style="width:100%;">
         <br/>
         <input id="submit" class="button" type="submit" value="Find"/> <span class="status"></span>
       </form>
    </div>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <div id="results">
      <c:forEach begin="0" var="post" items="${posts}">
        <div class="large-card">
          <p class="overline">ID : ${post.getId()} ~ <a href="http://xarql.com/polr/${post.getAnswers()}">Replied To : ${post.getAnswers()}</a> ~ Date : ${post.getDisplayDate()}</p>
          <p class="overline">Replies : ${post.getResponses()} ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
          <h6>${post.getTitle()}</h6>
          <p>${post.getContent()}</p>
          <p><a href="http://xarql.com/polr/${post.getId()}">View</a></p>
        </div>
      </c:forEach>
    </div>
  </div>
</div>
</body>
</html>