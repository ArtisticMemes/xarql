<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html id="html">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>pexl ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.min.css">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous" defer=""></script>
  <script src="http://xarql.com/src/common/jscookie.js" defer=""></script>
  <script src="http://xarql.com/src/pexl/pexl.js" defer=""></script>
  <link rel="shortcut icon" href="http://xarql.com/logo.png" type="image/x-icon">
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div class="large-card">
        <c:forEach begin="0" var="pexl" items="${pexl-string}">
          <c:if test="${pexl == ';'}"><br></c:if>
          <c:if test="${pexl == 'r'}"><div class="pexl" style="background-color:red;height=1px;width=1px;"></div></c:if>
        </c:forEach>
      </div>
    </div>
  </div>
</body>
</html>