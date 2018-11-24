<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
  <title>Moderate Posts ~ xarql</title>
  <link rel="stylesheet" type="text/css" href="${domain}/src/common/${theme}-common.min.css">
  <link rel="shortcut icon" href="${domain}/logo.png" type="image/x-icon">
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div class="large-card">
        <form action="${domain}/polr/edit?type=remove" method="POST" id="post-form">
          <br/>
          ID : <input type="number" name="id" value="${id}" min="1" size="9" required="" style="width:4rem;"/>
          <input id="submit" class="button" type="submit" value="Remove"/>
        </form>
        <form action="${domain}/polr/edit?type=restore" method="POST" id="post-form">
          <br/>
          ID : <input type="number" name="id" value="${id}" min="1" size="9" required="" style="width:4rem;"/>
          <input id="submit" class="button" type="submit" value="Restore"/>
        </form>
      </div>
    </div>
  </div>
</body>
</html>