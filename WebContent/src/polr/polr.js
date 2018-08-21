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
	function update() {
		$("#status").text("trying");
	    var updt = $("<div></div>").load("http://xarql.com/polr/updt?id=" + $("#main-post-id").text() + " #full", function(response, status, xhr) {
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
});