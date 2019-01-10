/* After editing, manually rename the nav() function in the .min.js file to stay as nav() */
$(document).ready(function () {
  var domain = document.getElementById('domain').getAttribute('value');
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
		$(".status").each(function() {
			$(this).text("trying");
		});
	    var updt = $("<div></div>").load(domain + "/polr/updt?id=" + $("#main-post-id").text() + "&page=" + $("#page").text() + "&sort=" + $("#sort").text() + "&flow=" + $("#flow").text() + "#full", function(response, status, xhr) {
	    	if(status == "error") {
	    		$(".status").each(function() {
	    			$(this).text(xhr.statusText);
	    		});
	    	}
	    	else {
				$("#main-post").replaceWith(updt.find("#main-post-container").html());
				$("#replies").replaceWith(updt.find("#replies-container").html());
				$("title").text(updt.find("#main-post-title").text() + " ~ xarql");
				viewLinks();
				$(".status").each(function() {
					$(this).text(xhr.statusText);
				});
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
	// Stop form from submitting normally
	    event.preventDefault();
	$(".status").each(function() {
		$(this).text("trying");
	});

    // Get values from form, reset form
    var $form = $( this ),
      title = $form.find("input[name='title']").val(),
      content = $form.find("textarea[name='content']").val(),
      answers = $form.find("input[name='answers']").val(),
      url = $form.attr("action");
    $form.trigger('reset');
    $("#replying-to-input").val(parseInt($("#main-post-id").text()));

    // Send the data using AJAX POST
    $.ajax({
    	type: "POST",
    	url: url,
    	data : {
    		title: title,
    		content: content,
    		answers: answers,
    	}
    	}).done(function(){
    		$(".status").each(function() {
    			$(this).text("success");
          $("#advisory").text("Wait 20 seconds between posts");
          $("#advisory").show();
    		});
    	}).fail(function(){
    		$(".status").each(function() {
          $form.find("input[name='title']").val(title);
          $form.find("textarea[name='content']").val(content);
          $form.find("input[name='answers']").val(answers);
    			$(this).text("error");
          $("#advisory").text("Try reloading if posting fails. Remember to solve the Recaptcha.");
          $("#advisory").show();
    		});
    	}).always(function(){
    		window.setTimeout(update, 500); // wait .5 seconds
    	});
    });

  function nav() {
		$(".status").each(function() {
			$(this).text("trying");
		});
		var $form = $("#nav-form"),
	      page = $("#page-dropdown").val(),
	      sort = $("#sort-dropdown").val(),
	      flow = $("#flow-dropdown").val();
	    var updt = $("<div></div>").load(domain + "/polr/updt?id=" + $("#main-post-id").text() + "&page=" + page + "&sort=" + sort + "&flow=" + flow, function(response, status, xhr) {
	    	if(status == "error") {
	    		$(".status").each(function() {
	    			$(this).text(xhr.statusText);
	    		});
	    	}
	    	else {
	    		$("#main-post").replaceWith(updt.find("#main-post-container").html());
				$("#replies").replaceWith(updt.find("#replies-container").html());
				$("title").text(updt.find("#main-post-title").text() + " ~ xarql");
				history.pushState("xarql", "xarql", window.location.pathname + "?page=" + page + "&sort=" + sort + "&flow=" + flow);
				$("#page").text(page);
				$("#sort").text(sort);
				$("#flow").text(flow);
				viewLinks();
				if(page > 0) { $("#prev-form").show(); $("#prev-form").css("display", "inline");} else { $("#prev-form").hide(); }
				if(page < 4) { $("#next-form").show(); $("#next-form").css("display", "inline");} else { $("#next-form").hide(); }
				$(".status").each(function() {
					$(this).text(xhr.statusText);
				});
				$("html, body").animate({scrollTop: 0}, "fast");
	    	}
	    });
	}
	$("#nav-form").submit(function(event) {
		event.preventDefault();
		nav();
	});
	$('#next-form').submit(function(event) {
		event.preventDefault();
		var pageNum = parseInt($('#page-dropdown').val());
		if(pageNum < 9)
			$('#page-dropdown').val(pageNum + 1);
		nav();
	});
	$('#prev-form').submit(function(event) {
		event.preventDefault();
		var pageNum = parseInt($('#page-dropdown').val());
		if(pageNum > 0)
			$('#page-dropdown').val(pageNum - 1);
		nav();
	});

  // Change font size
  $('html').css('font-size', Cookies.get('font-size'));
  $("#text-up").on("click", function() {
	  var computedFontSize = parseFloat(window.getComputedStyle(document.getElementById("html")).fontSize);
    $('#font-size').remove();
	  $('html').css('font-size', (computedFontSize + 1) + 'px');
	  Cookies.set('font-size', (computedFontSize + 1) + 'px');
  });
  $("#text-dn").on("click", function() {
	  var computedFontSize = parseFloat(window.getComputedStyle(document.getElementById("html")).fontSize); // Get font size of <html></html>
    $('#font-size').remove();
	  $('html').css('font-size', (computedFontSize - 1) + 'px'); // Change font size by -1
	  Cookies.set('font-size', (computedFontSize - 1) + 'px');
  });

  var nav = false;
  function view(id) {
		$(".status").each(function() {
			$(this).text("trying");
		});
	    var updt = $("<div></div>").load(domain + "/polr/updt?id=" + id + "&page=0", function(response, status, xhr) {
	    	if(status == "error") {
	    		$(".status").each(function() {
	    			$(this).text(xhr.statusText);
	    		});
	    	}
	    	else {
				$("#main-post").replaceWith(updt.find("#main-post-container").html());
				$("#replies").replaceWith(updt.find("#replies-container").html());
				$("title").text(updt.find("#main-post-title").text() + " ~ xarql");
        if(nav) {
          // Do nothing
        } else {
          history.pushState("xarql", "xarql", domain + "/polr/" + id);
        }
				$(".status").each(function() {
					$(this).text(xhr.statusText);
				});
				$("html, body").animate({scrollTop: 0}, "fast");
				$("#replying-to-input").val(parseInt($("#main-post-id").text()));
				$("#page-dropdown").val(0);
				$("#prev-form").hide();
				$("#next-form").show();
				$("#next-form").css("display", "inline");
				viewLinks();
	    	}
	    });
	}

  function viewLinks()
  {
	  $(".view-link").each(function () {
		  $(this).unbind("click");
		  $(this).on("click", function () {
			  var id = $(this).attr("post-id");
			  view(id);
			  return false;
		});
	});
  }
  viewLinks();

  window.addEventListener('popstate', function(event) {
      var pieces = window.location.href.split('/');
      var currentID = pieces[pieces.length - 1];
      nav = true;
      view(currentID);
      nav = false;
  }, false);


  function changeTheme(theme) {
    if(theme === 'light' || theme === 'dark' || theme === 'purple') {
    }
    else {
      theme = "light";
    }
    $("#theme-styles").attr("href", domain + "/src/common/" + theme + "-common.min.css");
    Cookies.set('theme', theme);
    $(".theme-button").show();
    $("#" + theme + "-theme-button").hide();
  }
  changeTheme(Cookies.get("theme"));
  $(".theme-button").each(function () {
		var $this = $(this);
		$this.on("click", function () {
			changeTheme($this.attr("data"));
		});
	});

  // Option Pane
  function fontWeight(weight)
  {
	  if(weight === 'light') {
		  $('p').css('font-weight', '200');
		  $('.bold').css('font-weight', '400');
      $('h6').css('font-weight', '400');
		  $('#font-light-button').hide();
		  $('#font-normal-button').show();
		  Cookies.set('font-weight', 'light');
	  }
	  else {
		  $('p').css('font-weight', '400');
		  $('.bold').css('font-weight', '600');
      $('h6').css('font-weight', '600');
		  $('#font-normal-button').hide();
		  $('#font-light-button').show();
		  Cookies.set('font-weight', 'normal');
	  }
  }
  fontWeight(Cookies.get('font-weight'));
  $("#font-light-button").on('click', function() {
	  fontWeight('light');
  });
  $("#font-normal-button").on('click', function() {
	  fontWeight('normal');
  });

  // Enable JS buttons + Option Pane
  $(".ajax-bar").each(function() {
	  $(this).show();
  });
  $('#option-pane-open-button').on('click', function() {
	  $("#option-pane").show();
	  $("#option-pane-close-button").show();
	  $(this).hide();
  });
  $('#option-pane-close-button').on('click', function() {
	  $("#option-pane").hide();
	  $("#option-pane-open-button").show();
	  $(this).hide();
  });

  /* Auto update page to reflect options set on another page
  function autoOption() {
	  if($('#styles').length)
		  defaultStylesInjected = true;
	  if(defaultStylesInjected == false)
		  window.setTimeout(autoCrunch, 100);  wait 100 milliseconds before checking again
	  else {
		  if(Cookies.get('theme') === 'dark')
			  setTheme('dark');
	  }
  }
  autoOption();*/
});