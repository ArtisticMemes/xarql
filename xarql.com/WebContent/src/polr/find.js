$(document).ready(function () {
	function search() {
		$(".status").each(function() {
			$(this).text("trying");
		});
		var $form = $("#find-form"),
	      query = $form.find("input[name='q']").serialize(),
	      queryText = $form.find("input[name='q']").val();
	    var updt = $("<div></div>").load("http://xarql.com/polr/find?ajax=true&" + query, function(response, status, xhr) {
	    	if(status == "error") {
	    		$(".status").each(function() {
	    			$(this).text(xhr.statusText);
	    		});
	    	}
	    	else {
				$("#results").html(updt.find("#results").html());
				history.pushState("xarql", "xarql", window.location.pathname + "?" + query);
				$("title").text(queryText + " ~ xarql");
				$("#find-form").trigger("reset");
				$(".status").each(function() {
					$(this).text(xhr.statusText);
				});
	    	}
	    });
	}
	$("#find-form").submit(function(event) {
		event.preventDefault();
		search();
	});
});