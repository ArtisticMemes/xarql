<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>polr | ${id}</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.css">
  <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/card/large.css">
  <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
  <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
      <c:forEach begin="0" var="post" items="${posts}" end="0">
		  <div class="card">
		    <p class="overline">ID : ${post.getId()} ~ Replied To : <a href="http://xarql.com/polr?id=${post.getAnswers()}">${post.getAnswers()}</a> ~ Date : ${post.getDate().toString().substring(0,19)}</p>
		    <p class="overline">Replies : ${post.getResponses()} ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
		    <h6>${post.getTitle()}</h6>
		    <p>${post.getContent()}</p>
		    <p><a href="http://xarql.com/polr?id=${post.getId()}">View Replies</a></p>
		  </div>
		</c:forEach>
      <div class="card" style="x-overflow:hidden;">
       <h4>Create Post</h4>
       <form action = "/polr/post" method = "POST">
         <input type = "text" name = "title" placeholder="Title" maxlength="128" style="width:100%;">
         <br />
         <textarea name="content" cols="64" rows="8" tabindex="0" placeholder="Content" wrap="soft" maxlength="4096" required style="width:100%;height:8rem;"></textarea>
         <br />
         Replying To : <input type = "number" name="answers" value="${id}" min="0" size="4" required style="width:4rem;"/>
         <input id="submit" type = "submit" value = "Post" /> <input type="reset" value="Clear" />
         <div style="position:relative;">
         	<div class="g-recaptcha" data-callback="recaptchaCallback" data-sitekey="6Ldv_V8UAAAAAA8oid2KDaOQqTu4kFFHDvhK9Blt"></div> 
         	<input id='recaptcha_check_empty' required tabindex='-1', style='width:50px; height:0; opacity:0; pointer-events:none; position:absolute; bottom:0;'>
         </div>
       </form>
       <p><a href="http://xarql.com/help">Help</a></p>
      </div>
      <script>
      function recaptchaCallback()
      {
    	  document.getElementById('recaptcha_check_empty').value = 1;
      }
      </script>
		<c:forEach begin="1" var="post" items="${posts}">
		  <div class="card">
		    <p class="overline">ID : ${post.getId()} ~ Date : ${post.getDate().toString().substring(0,19)}</p>
		    <p class="overline">Replies : ${post.getResponses()} ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
		    <h6>${post.getTitle()}</h6>
		    <p>${post.getContent()}</p>
		    <p><a href="http://xarql.com/polr?id=${post.getId()}">View Replies</a></p>
		  </div>
		</c:forEach>
    </div>
  </div>
</body>
</html>