<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>${posts.get(0).getTitle()} ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
      <h1 class="centered">Nothing</h1>
      <h4 class="centered">No Replies Were Made To ID : ${id}</h4>
      <p class="centered"><a href="http://xarql.com/polr?id=${id}" target="_blank">Regular View</a></p>
      </div>
    </div>
  <no-script>
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.css">
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/card/large.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
  </no-script>
</body>
</html>