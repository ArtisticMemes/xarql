<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>blog ~ xarql</title>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div id="content">
        <iframe src="http://xarql.tumblr.com/" frameborder="0"></iframe>
      </div>
    </div>
  </div>
  <noscript id="default-styles">
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/blog/blog.css">
  </noscript>
  <noscript id="fonts">
  </noscript>
  <script>
      var loadDeferredStyles = function() {
        var addStylesNode = document.getElementById("default-styles");
        var replacement = document.createElement("div");
        replacement.innerHTML = addStylesNode.textContent;
        replacement.id = "styles";
        document.body.appendChild(replacement);
        addStylesNode.parentElement.removeChild(addStylesNode);
      };
      var loadDeferredFonts = function() {
    	  var addFontsNode = document.getElementById("fonts");
    	  var replacement = document.createElement("div");
    	  replacement.innerHTML = addFontsNode.textContent;
    	  replacement.id = "fonts";
    	  document.body.appendChild(replacement);
    	  addFontsNode.parentElement.removeChild(addFontsNode);
      };
      var raf = window.requestAnimationFrame || window.mozRequestAnimationFrame ||
          window.webkitRequestAnimationFrame || window.msRequestAnimationFrame;
      if (raf) raf(function() { window.setTimeout(loadDeferredStyles, 0); });
      else window.addEventListener('load', loadDeferredStyles);
      if (raf) raf(function() { window.setTimeout(loadDeferredFonts, 0); });
      else window.addEventListener('load', loadDeferredFonts);
      var defaultStylesInjected = false;
  </script>
</body>
</html>
