<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Help (/polr) ~ xarql</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta id="domain" value="${domain}">
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
    h5, .bold {
      font-weight: ${font_weight + 200}
    }
  </style>
</head>
<body>
  <div id="wrapper">
    <div id="column">
      <div class="large-card">
        <!-- Copy&Paste from markdown -->
<h3>/polr</h3><p>is an anonymous forum that is meant to be conducive to debate. Its main feature is that replies have replies, ad infinitum.</p><h5>Navigation</h5><p>There is a post at the top, before the "Create Post" element, which is called the "Main Post". The posts below 'Create Post' are the "Replies" to the main post. This vocabulary is important. <br /><strong>Replies per Page</strong> <br />20 replies are shown on each "Page". To view the other pages, the URL must be changed to include <code>?page=</code> with a number afterwards. The initial page is number 0, and there are 4 pages per main post, going up to the 4th which is page 3. To access the last one you would add <code>?page=3</code>. Although this means only 80 replies are available, there is no limit to how many may be made. <br /><strong>Sort</strong> <br />The default sorting method for replies is <code>subbump</code>. This relates to a date held in the database which represents the last time a reply was made either to the main post or one of its children. <code>bump</code> is the same but only with direct replies. <code>subresponses</code> is the amount of replies a post and all of its children have combined. <code>responses</code> is the amount of direct replies. <code>date</code> is the time at which the post was made. These sorting methods can be accessed by typing <code>?sort=</code> and whichever one you want. To get the replies with the most responses, you would put <code>?sort=responses</code>. To combine this with a page number and get the 21st through 40th replies sorted by responses, you would put <code>?page=1&amp;sort=responses</code>. The order of the parameters don't matter, as long as they start with a <code>?</code> and have a <code>&amp;</code> between them. <br /><strong>Flow</strong> <br />By default, sorting is done by most to least. For <code>date</code> that means newest first. In order to specify most first or least first, use <code>?flow=</code> + either <code>desc</code> or <code>asc</code>. <code>desc</code> will give you most first. <code>asc</code> will give you least first. <br /><strong>"Navigation" element</strong> <br />If you don't want to enter these manually, you can select these options from the navigation and click on "Custom". To view the next page, click "Next". To view the previous page, click "Prev".  </p><h5>Create Post</h5><p>This element allows user to submit posts to <code>/polr</code>. It has a form with 3 inputs: <code>title</code>, <code>content</code>, and <code>answers</code>. The title and content have full text formatting. See the wiki page for TextFormatter for more information. The <code>answers</code> input field is labeled as "Replying To:" and says which main post the reply is for. Users must be Authenticated before posting. See the wiki page for <code>/auth</code> for more information. Requests from this form go to <code>/polr/post</code>.</p><h5>ID</h5><p>Each and every post has a unique ID. This ID will be the the previously highest ID + 1. To view a certain post, you can enter its ID after <code>/polr/</code>. To view post 99, you would enter <code>/polr/99</code>.</p><h5>View link</h5><p>The "View" link will make the post on which it appears the main post, and will use the defaults for sort and flow. If JavaScript is enabled, this is achieved by making a request to <code>polr/updt</code> that includes the post's ID and replacing the HTML being shown. Otherwise, the link acts as normal.</p><h5>Report link</h5><p>The "Report" link will only show up on the main post. It allows a user to jump to a reporting form at <code>/flag</code> with the main post's ID autofilled.</p>
      </div>
      <div class="small-card">
        <a style="margin-top:0.5em;" class="centered" href="${domain}/help/main"><span class="italic">Main Help Page</span></a>
      </div>
    </div>
  </div>
</body>
</html>
