<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="${domain}/-/src/viewer/image.min.css">
  <link rel="shortcut icon" href="https://xarql.com/logo.png" type="image/x-icon">
  <title>Image Viewer ~ xarql.net</title>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div class="photo">
        <a href="${domain}/-/static/${id}/raw.${type}">
          <img src="${domain}/-/static/${id}/raw.${type}">
        </a>
      </div>
    </div>
  </div>
</body>
</html>