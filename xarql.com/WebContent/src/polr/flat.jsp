<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Browsing /polr ~ xarql</title>
  <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.min.css">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous" defer=""></script>
  <script src="http://xarql.com/src/common/jscookie.js" defer=""></script>
  <script src="http://xarql.com/src/polr/flat.min.js" defer=""></script>
</head>
<body>
  <div id="wrapper">
    <div id="column">
    <div class="large-card">
	    <h4>Browse</h4>
	    <form id="flat-form" action="http://xarql.com/polr/flat" method="GET" accept-charset="utf-8" style="display:inline;">
	      <table style="display:inline;">
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
</body>
</html>