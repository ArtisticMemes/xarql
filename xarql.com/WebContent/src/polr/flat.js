$(document).ready(function () {
  var domain = document.getElementById('domain').getAttribute('value');
  function browse() {
		$(".status").each(function() {
			$(this).text("trying");
		});
		var $form = $("#flat-form"),
	      page = $("#page-dropdown").val(),
	      sort = $("#sort-dropdown").val(),
	      flow = $("#flow-dropdown").val();
	    var updt = $("<div></div>").load(domain + "/polr/flat?ajax=true&page=" + page + "&sort=" + sort + "&flow=" + flow, function(response, status, xhr) {
	    	if(status == "error") {
	    		$(".status").each(function() {
	    			$(this).text(xhr.statusText);
	    		});
	    	}
	    	else {
				$("#results").html(updt.find("#results").html());
				history.pushState("xarql", "xarql", window.location.pathname + "?page=" + page + "&sort=" + sort + "&flow=" + flow);
				$(".status").each(function() {
					$(this).text(xhr.statusText);
				});
	    	}
	    });
	}
	$("#flat-form").submit(function(event) {
		event.preventDefault();
		browse();
    fontWeight(Cookies.get('font-weight'));
	});

  // Use options from /polr
  function fontWeight(weight)
  {
	  if(weight === 'light') {
		  $('p').css('font-weight', 'lighter');
		  $('.bold').css('font-weight', 'normal');
		  Cookies.set('font-weight', 'light');
	  }
	  else {
		  $('p').css('font-weight', 'normal');
		  $('.bold').css('font-weight', 'bold');
		  Cookies.set('font-weight', 'normal');
	  }
  }
  fontWeight(Cookies.get('font-weight'));
  $('html').css('font-size', Cookies.get('font-size'));
});