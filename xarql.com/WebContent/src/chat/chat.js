$(document).ready(function () {
	// Update Messages
  var domain = document.getElementById('domain').getAttribute('value');

	// AJAX posting
	$("#message-form" ).submit(function(event) {
		// Stop form from submitting normally
		event.preventDefault();
    wsSendMessage();
	});

  $("#send-button").on("click", function() {
    wsSendMessage();
  });

  $('#messages').append('<div class="small-card"><p class="status">Connecting...</p></div>');
  var webSocket = new WebSocket("ws://" + domain.substr(domain.indexOf("//")) + "/chat/websocket");
  var message = document.getElementById("message");
  webSocket.onopen = function(message){ wsOpen(message);};
  webSocket.onmessage = function(message){ wsGetMessage(message);};
  webSocket.onclose = function(message){ wsClose(message);};
  webSocket.onerror = function(message){ wsError(message);};
  function wsSendMessage() {
    webSocket.send(message.value);
    message.value = "";
  }
  function wsGetMessage(message) {
    var data = message.data;
    var headers = new Map();
    // Repeat until all key:value,key:value pairs are consumed and | terminates them
    while(data.indexOf(':') != -1 && data.indexOf(':') < data.indexOf('|'))
    {
      // Get key from key:value pair
      var param = data.substr(0, data.indexOf(':'));
      // Remove the key from the start of data
      data = data.substr(data.indexOf(':') + 1);
      // Get the index of , or | --> whichever comes first
      var stop = 0;
      if(data.indexOf(',') != -1 && data.indexOf(',') < data.indexOf('|'))
        stop = data.indexOf(',');
      else
        stop = data.indexOf('|');
      // Get value from key:value pair
      var value = data.substr(0, stop);
      // Trim the value and , or | from data
      data = data.substr(stop + 1);
      // Add the key:value pair to the map
      headers.set(param, value);
    }
    if(headers.get('direct-display') === 'false')
      $('#messages').append('<div class="small-card"><p class="status">' + data + '</p></div>');
    else {
      var color = headers.get('client-name');
      $('#messages').append('<div class="small-card" style="background-color:#' + color + '"><p>' + data + '</p></div>');
    }
  }
  function wsOpen(message) {
    $('#messages').append('<div class="small-card"><p class="status">Connected!</p></div>');
  }
  function wsClose(message) {
    $('#messages').append('<div class="small-card"><p class="warn">Disconnected</p></div>');
    window.setTimeout(function () {
      $('#messages').append('<div class="small-card"><p class="status">Reloading...</p></div>');
      location.reload();
    }, 3000);
  }
  function wsError(message) {
    $('#messages').append('<div class="small-card"><p class="warn">Error!</p></div>');
  }

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

  // Change theme
  function changeTheme(theme) {
    if(theme === 'light' || theme === 'dark' || theme === 'rainbow') {
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

  // Change font weight
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

  // Show option pane
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
});
