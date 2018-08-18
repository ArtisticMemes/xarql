$(document).ready(function () {
	$(".update-button").each(function () {
		var $this = $(this);
		$this.on("click", function () {
		    var updt = $("<div></div>").load("http://xarql.com/polr/updt?id=" + $("#main-post-id").text() + "#full", function() {
		    	$("#main-post").replaceWith(updt.find("#main-post-container").html());
		    	$("#replies").replaceWith(updt.find("#replies-container").html());
		    });
		});
	});
});