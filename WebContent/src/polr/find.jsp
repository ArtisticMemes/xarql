<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>${query} ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous" defer=""></script>
  <script src="http://xarql.com/src/common/jscookie.js" defer=""></script>
  <script src="http://xarql.com/src/polr/find.js" defer=""></script>
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
    <div class="card" style="x-overflow:hidden;">
      <h4>Search</h4>
      <form action="http://xarql.com/polr/find" method="GET" id="find-form">
         <input type="text" name="q" placeholder="Phrase" maxlength="64" required="" style="width:100%;">
         <br/>
         <input id="submit" type="submit" value="Find"/> <span class="status"></span>
       </form>
    </div>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <div id="results">
      <c:forEach begin="0" var="post" items="${posts}">
        <div class="card">
          <p class="overline">ID : ${post.getId()} ~ <a href="http://xarql.com/polr/${post.getAnswers()}">Replied To : ${post.getAnswers()}</a> ~ Date : ${post.getDisplayDate())}</p>
          <p class="overline">Replies : ${post.getResponses()} ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
          <h6>${post.getTitle()}</h6>
          <p>${post.getContent()}</p>
          <p><a href="http://xarql.com/polr/${post.getId()}">View</a></p>
        </div>
      </c:forEach>
    </div>
  </div>
</div>
<noscript id="default-styles">
  <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.css">
  <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/card/large.css">
  <script>defaultStylesInjected = true;</script>
</noscript>
<noscript id="fonts">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Code+Pro">
</noscript>
<script>
  var loadDeferredStyles = function() {
    var addStylesNode = document.getElementById("default-styles");
    var replacement = document.createElement("div");
    replacement.innerHTML = addStylesNode.textContent;
    replacement.id = "styles";
    document.body.appendChild(replacement);
    addStylesNode.parentElement.removeChild(addStylesNode);
  };
  var loadDeferredFonts = function() {
    var addFontsNode = document.getElementById("fonts");
    var replacement = document.createElement("div");
    replacement.innerHTML = addFontsNode.textContent;
    replacement.id = "fonts";
    document.body.appendChild(replacement);
    addFontsNode.parentElement.removeChild(addFontsNode);
  };
  var raf = window.requestAnimationFrame || window.mozRequestAnimationFrame ||
    window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
  if (raf) raf(function() { window.setTimeout(loadDeferredStyles, 0); });
  else window.addEventListener('load', loadDeferredStyles);
  if (raf) raf(function() { window.setTimeout(loadDeferredFonts, 0); });
  else window.addEventListener('load', loadDeferredFonts);
  var defaultStylesInjected = false;
</script>
</body>
</html>