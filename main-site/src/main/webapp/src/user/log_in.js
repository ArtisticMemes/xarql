$(document).ready(function() {
  var domain = $("#domain").attr("value");

  var form = $("#log_in-form");
  var url = domain + "/user/log_in/meta";

  // AJAX posting
  form.submit(function( event ) {
	// Stop form from submitting normally
	    event.preventDefault();
      var username = $("#username").val();
      var password = $("#password").val();
      form.trigger('reset');

    // Send the data using AJAX POST
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
        $("#status").show();
        if($xml.find("success").text() == "true") {
          window.location.href = domain + "/user";
        }
        else {
          $("#username").val(username);
        }
      }
    });
  });
});
