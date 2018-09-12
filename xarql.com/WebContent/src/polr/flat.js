$(document).ready(function () {
  function browse() {
		$(".status").each(function() {
			$(this).text("trying");
		});
		var $form = $("#flat-form"),
	      page = $("#page-dropdown").val(),
	      sort = $("#sort-dropdown").val(),
	      flow = $("#flow-dropdown").val();
	    var updt = $("<div></div>").load("http://xarql.com/polr/flat?ajax=true&page=" + page + "&sort=" + sort + "&flow=" + flow, function(response, status, xhr) {
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
	});
});