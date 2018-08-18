<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>${posts.get(0).getTitle()} ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src='https://www.google.com/recaptcha/api.js' async="" defer=""></script>
  <script src="http://xarql.com/src/common/jquery/jquery-3.3.1.min.js" defer=""></script>
  <script src="http://xarql.com/src/polr/polr.js" defer=""></script>
  <style>
@charset "UTF-8";
#wrapper, html, body {
  font-family: 'Roboto';
  display: flex;
  visibility: visible;
  overflow-x: hidden;
  min-height: 100vh;
  margin: 0;
  padding: 0;
  border: 0;
  justify-content: center;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
#wrapper {
  width: 100%;
  max-width: 100%;
}
html, body {
  width: 100vw;
  max-width: 100vw;
}
*, *:before, *:after {
  font-display:swap
  -webkit-box-sizing: inherit;
  box-sizing: inherit;
}
#column {
  max-width: 100%;
}
.card {
  width: 40rem;
  max-width: 100%;
}
  </style>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <div id="main-post">
      <c:forEach begin="0" var="post" items="${posts}" end="0">
		  <div class="card">
		    <p class="overline">ID : <span id="main-post-id">${post.getId()}</span> ~ Replied To : <a href="http://xarql.com/polr?id=${post.getAnswers()}">${post.getAnswers()}</a> ~ Date : ${post.getDate().toString().substring(0,19)}</p>
		    <p class="overline">Replies : <span id="reply-count">${post.getResponses()}</span> ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
		    <h6>${post.getTitle()}</h6>
		    <p>${post.getContent()}</p>
		  </div>
		</c:forEach>
		</div>
      <div class="card" style="x-overflow:hidden;">
       <h4>Create Post</h4>
       <form action="/polr/post" method="POST">
         <input type="text" name="title" placeholder="Title (optional)" maxlength="64" style="width:100%;">
         <br/>
         <textarea name="content" cols="64" rows="8" tabindex="0" placeholder="Content (required)" wrap="soft" maxlength="4096" required style="width:100%;height:8rem;"></textarea>
         <br/>
         Replying To : <input type="number" name="answers" value="${id}" min="0" size="4" required="" style="width:4rem;"/>
         <input id="submit" type="submit" value="Post"/> <input type="reset" value="Clear"/>
         <div style="position:relative;">
         	<div class="g-recaptcha" data-callback="recaptchaCallback" data-sitekey="6Ldv_V8UAAAAAA8oid2KDaOQqTu4kFFHDvhK9Blt"></div> 
         	<input id='recaptcha_check_empty' required="" tabindex='-1' style='width:50px; height:0; opacity:0; pointer-events:none; position:absolute; bottom:0;'>
         </div>
       </form>
       <p><a href="http://xarql.com/help">Help</a> <a class="update-button">Update</a> <span id="status"></span></p>
      </div>
      <script>
      function recaptchaCallback()
      {
    	  document.getElementById('recaptcha_check_empty').value = 1;
      }
      </script>
      <div id="replies">
		<c:forEach begin="1" var="post" items="${posts}">
		  <div class="card">
		    <p class="overline">ID : ${post.getId()} ~ Date : ${post.getDate().toString().substring(0,19)}</p>
		    <p class="overline">Replies : ${post.getResponses()} ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
		    <h6>${post.getTitle()}</h6>
		    <p>${post.getContent()}</p>
		    <p><a href="http://xarql.com/polr?id=${post.getId()}">View</a></p>
		  </div>
		</c:forEach>
	  </div>
    </div>
  </div>
  <no-script>
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.css">
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/card/large.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto+Mono">
  </no-script>
</body>
</html>