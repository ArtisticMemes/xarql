$(document).ready(function() {
  var domain = $("#domain").attr("value");

  var form = $("#log_in-form");
  var url = domain + "/user/log_in/meta";

  // aJAX posting
  form.submit(function( event ) {
	// stop form from submitting normally
	    event.preventDefault();

      $("#status").text("Working...");
      $("#status").show();

      var username = $("#username").val();
      var password = $("#password").val();
      form.trigger('reset');

    // send the data using AJAX POST
    var attempt = $.ajax({
    	type: "POST",
    	url: url,
      dataType: "xml",
    	data : {
        username: username,
        password: password
    	},
      success: function(data) {
        $xml = $(data);
        $("#status").text($xml.find("comment").text());
        if ($xml.find("success").text() == "true") {
          window.location.href = domain + "/user";
        }
        else {
          $("#username").val(username);
        }
      }
    });
  });
});
