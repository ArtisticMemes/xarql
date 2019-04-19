<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html id="html">
<head>
  <!-- Global site tag (gtag.js) - Google Analytics -->
  <script async src="https://www.googletagmanager.com/gtag/js?id=${google_analytics_id}"></script>
  <script>
    window.dataLayer = window.dataLayer || [];
    function gtag(){dataLayer.push(arguments);}
    gtag('js', new Date());
    gtag('config', '${google_analytics_id}');
  </script>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Flag A Post From /polr ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
  <link rel="stylesheet" type="text/css" id="theme-styles" href="${domain}/src/common/${theme}-common.min.css">
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous" defer=""></script>
  <script src="${domain}/src/common/jscookie.js" defer=""></script>
  <link rel="shortcut icon" href="${domain}/logo.png" type="image/x-icon">
  <style id="font-size">
    html, body {
      font-size: ${font_size}
    }
  </style>
  <style id="font-weight">
    p {
      font-weight: ${font_weight}
    }
    h6, .bold {
      font-weight: ${font_weight + 200}
    }
  </style>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div class="large-card" style="x-overflow:hidden;">
       <h4>Report Post</h4>
       <form action="${domain}/flag/report" method="POST" id="report-form">
         <select name="type" id="type-dropdown">
           <option value="Hate Speech">Hate Speech</option>
           <option value="Spam">Spam</option>
         </select>
         <label>Post ID : </label><input type="number" id="report-id-input" name="id" value="${id}" min="0" size="4" required="" style="width:4rem;"/>
         <textarea autofocus spellcheck="true" autocomplete="off" name="description" cols="64" rows="8" tabindex="0" placeholder="Description (required)" wrap="soft" maxlength="512" required style="width:100%;height:8rem;"></textarea>
         <input id="submit" class="button" type="submit" value="Report"/>
       </form>
       <c:if test="${not auth}">
         <form action="${domain}/auth/recaptcha" method="POST" id="recaptcha-form">
           <div style="position:relative;">
             <div data-theme="${theme}" class="g-recaptcha" data-callback="recaptchaCallback" data-sitekey="${recaptcha_key}"></div>
             <input id='recaptcha_check_empty' required="" tabindex='-1' style='width:50px; height:0; opacity:0; pointer-events:none; position:absolute; bottom:0;'>
           </div>
         </form>
         <script src="${domain}/src/auth/auth.min.js" defer=""></script>
         <script src="https://www.google.com/recaptcha/api.js" async="" defer=""></script>
       </c:if>
      </div>
      <c:forEach begin="0" var="report" items="${reports}">
		  <div class="large-card">
        <h4><a href="${domain}/polr/${report.getPostID()}">${report.getPostID()}</a></h4>
		    <h6>${report.getType()}</h6>
		    <p>${report.getDescription()}</p>
		  </div>
		</c:forEach>
    </div>
  </div>
</body>
</html>