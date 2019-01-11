$(document).ready(function () {
  var domain = document.getElementById('domain').getAttribute('value');
	function search() {
		$(".status").each(function() {
			$(this).text("trying");
		});
		var $form = $("#find-form"),
	      query = $form.find("input[name='q']").serialize(),
	      queryText = $form.find("input[name='q']").val();
	    var updt = $("<div></div>").load(domain + "/polr/find?ajax=true&" + query, function(response, status, xhr) {
	    	if(status == "error") {
	    		$(".status").each(function() {
	    			$(this).text(xhr.statusText);
	    		});
	    	}
	    	else {
				$("#results").html(updt.find("#results").html());
				history.pushState("xarql", "xarql", window.location.pathname + "?" + query);
				$("title").text("Find : " + queryText + " ~ xarql");
				$("#find-form").trigger("reset");
        revealLinks();
				$(".status").each(function() {
					$(this).text(xhr.statusText);
				});
	    	}
	    });
	}
	$("#find-form").submit(function(event) {
		event.preventDefault();
		search();
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

  // Enables links for viewing censored content
  function reveal(id) {
    $('#post-inner-' + id).show();
    $('#post-warning-' + id).hide();
  } // reveal()

  // Enables links for viewing censored content
  function reveal(id) {
    $('#post-inner-' + id).show();
    $('#post-warning-' + id).hide();
  } // reveal()

  function revealLinks() {
    $(".reveal-link").each(function () {
  		var $this = $(this);
	  	$this.on("click", function () {
	  		reveal($this.attr('data'));
        $this.hide();
		  });
	  });
  }
  revealLinks();

});
