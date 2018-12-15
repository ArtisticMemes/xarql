<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <!-- Global site tag (gtag.js) - Google Analytics -->
  <script async src="https://www.googletagmanager.com/gtag/js?id=${google_analytics_id}"></script>
  <script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', 'UA-131023139-2');
  </script>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" id="theme-styles" href="https://xarql.com/src/common/light-common.min.css">
  <link rel="shortcut icon" href="https://xarql.com/logo.png" type="image/x-icon">
  <title>Upload An Image ~ xarql</title>
</head>
<body>
<div id="wrapper">
  <div id="column">
    <div class="large-card">
      <h4>Upload An Image</h4>
      <form id="file-form" action="${domain}/-/upload_endpoint" method="POST" enctype="multipart/form-data">
        <label>Image : <input type="file" required accept="image/jpeg,image/x-png" multiple="false" class="button" name="file"></label>
        <input type="submit" value="Sumbit" id="submit" class="button">
      </form>
      <p><a href="https://xarql.com/help/net" target="_blank">About</a></p>
    </div>
  </div>
</div>
</body>
</html>