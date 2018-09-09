<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${code}</title>
<style>
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
  max-width: 100%;
  height: 18.5rem;
  position: relative;
}
#error {
  margin: 1rem 0rem 0rem 0rem;
}
#code {
  margin: 0 0 0 0;
  line-height: 5rem;
}
.link {
  display: inline;
  margin: 2rem;
}
#link-div {
  display: flex;
  justify-content: center;
  width: 100%;  
}
#link-span {
  position: absolute;
  bottom: 2rem;
}
</style>
</head>
<body>
<div id="wrapper">
  <div id="column">
    <div class="card" style="width:30rem;">
      <h4 class="centered" id="error">Error</h5>
      <h1 class="centered" id="code">${code}</h1>
      <h5 class="centered">${type}</h4>
      <div id="link-div">
        <span id="link-span">
        <p class="link"><a href="http://xarql.com">Home</a></p>
        <p class="link"><a href="#" onclick="history.back()">Return</a></p>
        <p class="link"><a href="http://xarql.com/help">Help</a></p>
        </span>
      </div>
  </div>
</div>
</div>
<noscript id="default-styles">
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.css">
    <link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/card/large.css">
    <script>defaultStylesInjected = true;</script>
  </noscript>
  <noscript id="fonts">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <!-- <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Code+Pro"> -->
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