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
      <div class="large-card">
        <form action="${domain}/polr/edit?type=remove" method="POST" id="post-form" accept-charset="UTF-8">
          <br/>
          <label>ID : </label><input autocomplete="off"type="number" name="id" value="${id}" min="1" size="9" required="" style="width:4rem;"/>
          <input id="submit" class="button" type="submit" value="Remove"/>
        </form>
        <form action="${domain}/polr/edit?type=restore" method="POST" id="post-form" accept-charset="UTF-8">
          <br/>
          <label>ID : </label><input autocomplete="off"type="number" name="id" value="${id}" min="1" size="9" required="" style="width:4rem;"/>
          <input id="submit" class="button" type="submit" value="Restore"/>
        </form>
        <form action="${domain}/polr/edit?type=replace" method="POST" id="post-form" accept-charset="UTF-8">
          <br/>
          <label>ID : </label><input autocomplete="off"type="number" name="id" value="${id}" min="1" size="9" required="" style="width:4rem;"/>
          <input id="submit" class="button" type="submit" value="Replace"/>
          <input type="text" name="title" placeholder="Title (optional)" maxlength="64" style="width:100%;"/>
          <br/>
          <textarea spellcheck="true" autocomplete="off" name="content" cols="64" rows="8" tabindex="0" placeholder="Content (required)" wrap="soft" maxlength="4096" required style="width:100%;height:8rem;"></textarea>
        </form>
      </div>
    </div>
  </div>
</body>
</html>