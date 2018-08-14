<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>conf</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.css">
  <link href="https://fonts.googleapis.com/css?family=Roboto" rel="stylesheet">
  <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div class="card">
      <form action="/xarql/conf" method="POST">
         <input type="text" name="password" placeholder="password" style="width:100%;">
         <input id="submit" type="submit" value="Submit"/> <input type="reset" value="Clear"/>
         <div style="position:relative;">
         	<div class="g-recaptcha" data-callback="recaptchaCallback" data-sitekey="6Ldv_V8UAAAAAA8oid2KDaOQqTu4kFFHDvhK9Blt"></div> 
         	<input id='recaptcha_check_empty' tabindex='-1', style='width:50px; height:0; opacity:0; pointer-events:none; position:absolute; bottom:0;'>
         </div>
       </form>
       <p><a href="http://xarql.com/help">Help</a></p>
      </div>
      </div>
    </div>
  </div>
</body>
</html>