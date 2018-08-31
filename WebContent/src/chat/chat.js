$(document).ready(function () {
	// Update Messages
	var lastID = $("#last-id").text();
	function update() {
		$(".status").each(function() {
			$(this).text("trying");
		});
	    var updt = $("<div></div>").load("http://xarql.com/chat/updt?last=" + $("#last-id").text(), function(response, status, xhr) {
	    	if(status == "error") {
	    		$(".status").each(function() {
	    			$(this).text(xhr.statusText);
	    		});
	    	}
	    	else {
				$("#messages").append(updt.find("#messages").html());
				if(updt.find("#last-id").text() == 0) {} else
					$("#last-id").text(updt.find("#last-id").text());
				$(".status").each(function() {
					$(this).text(xhr.statusText);
				});
	    	}
	    	lastID = $("#last-id").text();
	    });
	}
	$(".update-button").each(function () {
		$(this).on("click", function () {
			update();
		});
	});
	// AJAX posting
	$( "#message-form" ).submit(function(event) {
		// Stop form from submitting normally
		event.preventDefault();
		$("#status").text("trying");
	   
	    // Get some values from elements on the page:
	    var $form = $(this),
	      message = $form.find("input[name='message']").val(),
	      url = $form.attr("action");
	   
	    // Send the data using AJAX POST
	    $.ajax({
	    	type: "POST",
	    	url: url,
	    	data : {
	    		message: message
	    	}
	    	}).done(function(){
	    		$("#status").text("success");
	    	}).fail(function(){
	    		$("#status").text("error");
	    	}).always(function(){
	    		$form.trigger('reset');
	    	});
	});
	$(".ajax-bar").each(function() {
		$(this).show();
	});
	function updateLoop() {
		update();
		window.setTimeout(updateLoop, 4000); // 4 seconds
	}
	updateLoop();
});