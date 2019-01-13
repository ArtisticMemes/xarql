<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
  <title>Browsing ~ xarql</title>
  <link rel="stylesheet" type="text/css" href="${domain}/src/common/${theme}-common.min.css">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous" defer=""></script>
  <script src="${domain}/src/common/jscookie.js" defer=""></script>
  <script src="${domain}/src/polr/flat.min.js" defer=""></script>
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
	    <h4>Browse</h4>
	    <form id="flat-form" action="${domain}/polr/flat" method="GET" accept-charset="utf-8" style="display:inline;">
	      <table style="display:inline;">
	        <tr><td>Page</td><td>Sort</td><td>Flow</td></tr>
	        <tr><td><select name="page" id="page-dropdown">
	                <c:forEach begin="0" end="4" var="i">
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
            </tr>
	      </table>
        <input id="submit" class="button" type="submit" value="Sort"/> <span class="status"></span>
	    </form>
	  </div>
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
            <c:if test="${post.getAuthor() != 'Unknown'}">
              ⤷<a href="${domain}/user/view?name=${post.getAuthor()}">${post.getAuthor()}</a>
            </c:if>
            <c:if test="${post.getWarning() != 'None'}"><a class="reveal-link" data="${post.getId()}">Reveal</a></c:if>
          </p>
  		  </div>
  		</c:forEach>
      </div>
      <div id="data" class="card" style="display:none;">
        <p id="page">${page}</p>
        <p id="sort">${sort}</p>
        <p id="flow">${flow}</p>
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