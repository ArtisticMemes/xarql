<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div id="full">
  <div id="main-post-container">
    <div id="main-post">
      <c:forEach begin="0" end="0" var="post" items="${posts}">
        <div class="card">
          <p class="overline">ID : <span id="main-post-id">${post.getId()}</span> ~ <a href="http://xarql.com/polr?id=${post.getAnswers()}">Replied To : ${post.getAnswers()}</a> ~ Date : ${post.getDate().toString().substring(0,19)}</p>
          <p class="overline">Replies : <span id="reply-count">${post.getResponses()}</span> ~ SubReplies : ${post.getSubresponses()} ~ Bump : ${post.timeSinceBump()} ~ SubBump : ${post.timeSinceSubbump()}</p>
          <h6 id="main-post-title">${post.getTitle()}</h6>
          <p>${post.getContent()}</p>
        </div>
      </c:forEach>
    </div>
  </div>
  <div id="replies-container">
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