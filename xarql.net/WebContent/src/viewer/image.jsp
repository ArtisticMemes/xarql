<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="shortcut icon" href="https://xarql.com/logo.png" type="image/x-icon">
  <title>Image Viewer ~ xarql.net</title>
  <style>
  #wrapper,html,body{border:0px;padding:0px;margin:0px;overflow-x:hidden;min-height:100vh;display:flex;justify-content:center;background-color:#f2f2f2;visibility:visible}::selection{background:#ffffff;color:#000000}::-moz-selection{background:#ffffff;color:#000000}#wrapper{max-width:100%;width:100%}html,body{width:100vw;max-width:100vw}html{box-sizing:border-box}*,*:before,*:after{box-sizing:inherit}#column{display:block;max-width:100%}a{text-decoration:none;color:#0000ff}a:hover{text-decoration:underline}a:active{position:relative;top:0.05em}a:visited{color:#0000ff}.photo{width:100%;margin:auto}.photo img,.photo a{width:inherit;height:inherit}
  </style>
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