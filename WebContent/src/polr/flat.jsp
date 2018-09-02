<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Browsing /polr ~ xarql</title>
  <script src="http://xarql.com/src/common/jquery/jquery-3.3.1.min.js" defer=""></script>
  <script src="http://xarql.com/src/polr/flat.js" defer=""></script>
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
    <div class="card">
	    <h4>Browse</h4>
	    <form id="flat-form" action="http://xarql.com/polr/flat" method="GET" accept-charset="utf-8">
	      <table>
	        <tr><td>Page</td><td>Sort</td><td>Flow</td></tr>
	        <tr><td><select name="page" id="page-dropdown">
	                <c:forEach begin="0" end="9" var="i">
                    <option value="${i}" <c:if test="${i == page}">selected="selected"</c:if> >${i}</option>
                    </c:forEach>
                  </select></td>
                <td><select name="sort" id="sort-dropdown">
                    <option value="date" <c:if test="${sort.equals('date')}">selected="selected"</c:if> >Date</option>
                    <option value="responses" <c:if test="${sort.equals('responses')}">selected="selected"</c:if> >Responses</option>
                    <option value="subresponses" <c:if test="${sort.equals('subresponses')}">selected="selected"</c:if> >SubResponses</option>
                    <option value="bump" <c:if test="${sort.equals('bump')}">selected="selected"</c:if> >Bump</option>
                    <option value="subbump" <c:if test="${sort.equals('subbump')}">selected="selected"</c:if> >SubBump</option>
                  </select></td>
                <td><select name="flow" id="flow-dropdown">
                    <option value="asc"  <c:if test="${flow.equals('asc')}">selected="selected"</c:if> >Ascending</option>
                    <option value="desc" <c:if test="${flow.equals('desc')}">selected="selected"</c:if> >Descending</option>
                  </select></td>
                <td><input id="submit" type="submit" value="Go"/> <span class="status"></span></td>
            </tr>
	      </table>
	    </form>
	  </div>
	  <div id="results">
        <c:forEach begin="0" var="post" items="${posts}">
          <div class="card">
            <p class="overline">ID : ${post.getId()} ~ Date : ${post.getDate().toString().substring(0,19)}</p>
            <p class="overline">Replies : ${post.getResponses()} ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
            <h6>${post.getTitle()}</h6>
            <p>${post.getContent()}</p>
            <p><a href="http://xarql.com/polr/${post.getId()}">View</a></p>
          </div>
        </c:forEach>
      </div>
      <div id="data" class="card" style="display:none;">
        <p id="page">${page}</p>
        <p id="sort">${sort}</p>
        <p id="flow">${flow}</p>
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