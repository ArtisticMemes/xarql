$(document).ready(function () {
	function getUrlParameter(sParam) {
	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	}
	
	// Update page contents
	function update() {
		$("#status").text("trying");
	    var updt = $("<div></div>").load("http://xarql.com/polr/updt?id=" + $("#main-post-id").text() + "&page=" + $("#page").text() + "&sort=" + $("#sort").text() + "&flow=" + $("#flow").text() + "#full", function(response, status, xhr) {
	    	if(status == "error") {
	    		$("#status").text(xhr.statusText);
	    	}
	    	else {
				$("#main-post").replaceWith(updt.find("#main-post-container").html());
				$("#replies").replaceWith(updt.find("#replies-container").html());
				$("#status").text(xhr.statusText);
	    	}
	    });
	}
	// Refresh on page load
	var refresh = getUrlParameter('refresh');
	if(typeof refresh != 'undefined' && refresh === 'true') {
		update();
		history.pushState("xarql", "xarql", window.location.pathname); // Remove parameters in URL
	}
  $(".update-button").each(function () {
		var $this = $(this);
		$this.on("click", function () {
			update();
		});
	});
  
  // AJAX posting
  $( "#post-form" ).submit(function( event ) {
	  $("#status").text("trying");
    // Stop form from submitting normally
    event.preventDefault();
   
    // Get some values from elements on the page:
    var $form = $( this ),
      title = $form.find("input[name='title']").val(),
      content = $form.find("textarea[name='content']").val(),
      answers = $form.find("input[name='answers']").val(),
      url = $form.attr("action");
   
    // Send the data using AJAX POST
    $.ajax({
    	type: "POST",
    	url: url,
    	data : {
    		title: title,
    		content: content,
    		answers: answers,
    		captcha: grecaptcha.getResponse()
    	}
    	}).done(function(){
    		$("#status").text("success");
    	}).fail(function(){
    		$("#status").text("error");
    	}).always(function(){
    		update();
    		$form.trigger('reset');
    	});
    });
  
  // Enable Navigation link
  var defaultPage = parseInt($('#page').text(), 10) + 1;
  $('#page-dropdown').val(defaultPage);
  $('#sort-dropdown').val($('#sort').text());
  $('#flow-dropdown').val($('#flow').text());
  function navlink() {
	  var page = $('#page-dropdown').val();
      var sort = $('#sort-dropdown').val();
      var flow = $('#flow-dropdown').val();
      $('#nav-link').html('<a href="http://xarql.com/polr/' + $("#main-post-id").text() + '?page=' + page + '&sort=' + sort + '&flow=' + flow + '" id="nav-link">Go</a>');
  }
  $('#page-dropdown').change(function () {
	  navlink();
  });
  $('#sort-dropdown').change(function () {
	  navlink();
  });
  $('#flow-dropdown').change(function () {
	  navlink();
  });
  navlink();
  
  // Change font size
  $('html').css('font-size', Cookies.get('font-size'));
  $("#text-up").on("click", function() {
	  var computedFontSize = parseFloat(window.getComputedStyle(document.getElementById("html")).fontSize);
	  $('html').css('font-size', (computedFontSize + 1) + 'px');
	  Cookies.set('font-size', (computedFontSize + 1) + 'px', { path: '' });
  });
  $("#text-dn").on("click", function() {
	  var computedFontSize = parseFloat(window.getComputedStyle(document.getElementById("html")).fontSize); // Get font size of <html></html>
	  $('html').css('font-size', (computedFontSize - 1) + 'px'); // Change font size by -1
	  Cookies.set('font-size', (computedFontSize - 1) + 'px', { path: '' });
  });
  
  // Use crunch styles
  function crunch() {
    $("#styles").replaceWith('<link id="styles" rel="stylesheet" type="text/css" href="http://xarql.com/src/common/crunch.css">');
    Cookies.set('crunch', 'true', { path: ''});
    $("#crunch-button").hide();
    $("#uncrunch-button").show();
  }
  function uncrunch() {
    $("#styles").replaceWith('<div id="styles"><link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/common.css"><link rel="stylesheet" type="text/css" href="http://xarql.com/src/common/card/large.css"></div>');
    Cookies.set('crunch', 'false', { path: ''});
    $("#uncrunch-button").hide();
    $("#crunch-button").show();
  }
  $("#crunch-button").on("click", function() {
    crunch();
  });
  $("#uncrunch-button").on("click", function() {
    uncrunch();
  });
  $("#uncrunch-button").hide();
  $("#ajax-bar").show();
  function autoCrunch() {
	  if($('#styles').length)
		  defaultStylesInjected = true;
	  if(defaultStylesInjected == false)
		  window.setTimeout(autoCrunch, 100); /* wait 100 milliseconds before checking again */
	  else {
		  if(Cookies.get('crunch') === 'true')
			  crunch();
	  }
  }
  autoCrunch();
});