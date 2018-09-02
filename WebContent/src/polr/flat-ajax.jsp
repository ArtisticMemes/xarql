<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="results-container">
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
</div>