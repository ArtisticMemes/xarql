$(document).ready(function () {
	// Update Messages
  var domain = document.getElementById('domain').getAttribute('value');

	// AJAX posting
	$("#message-form" ).submit(function(event) {
		// Stop form from submitting normally
		event.preventDefault();
    wsSendMessage();
	});

  function addUser(color) {
    $('#users').append('<div class="user-icon" id="user-' + color + '" style="background-color:#' + color + '"></div>');
  }

  function removeUser(color) {
    $('#user-' + color).remove();
  }

  function userTyping(color) {
    if(Cookies.get('theme') === 'dark')
      $('#user-' + color).css('box-shadow', '0px 5px 5px #000');
    else
      $('#user-' + color).css('box-shadow', '0px 5px 5px #999');
  }

  function userNotTyping(color) {
    $('#user-' + color).css('box-shadow', 'none');
  }

  function userBuffered(color) {
    $('#user-' + color).css('border-radius', '100%');

  }

  function userNotBuffered(color) {
    $('#user-' + color).css('border-radius', '25%');
  }

  $("#send-button").on("click", function() {
    wsSendMessage();
  });

  $('#updates').prepend('<div class="small-card" id="status"><p class="status">Connecting...</p></div>');
  var webSocket;
  var protocol;
  if(domain.includes('https://'))
    protocol = "wss";
  else
    protocol = "ws";
  webSocket = new WebSocket(protocol + domain.substr(domain.indexOf("://")) + "/chat/websocket/" + Cookies.get('chat-id').substr(1));
  var message = document.getElementById("message");
  webSocket.onopen = function(message){ wsOpen(message);};
  webSocket.onmessage = function(message){ wsGetMessage(message);};
  webSocket.onclose = function(message){ wsClose(message);};
  webSocket.onerror = function(message){ wsError(message);};
  function wsSendMessage() {
    if(message.value.length !== 0 && message.value.trim() !== "")
      webSocket.send("type:message|" + message.value);
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
    if(headers.get('TYPE') !== 'message')
    {
      if(headers.get('TYPE') === 'user_join') {
        addUser(headers.get('CLIENT_COLOR'));
      }
      else if(headers.get('TYPE') === 'user_exit') {
        removeUser(headers.get('CLIENT_COLOR'));
      }
      else if(headers.get('TYPE') === 'users_report') {
        data = data.substr(7);
        if(data.length > 1)
        {
          while(data.length > 0)
          {
            var client = data.substr(1, 6);
            addUser(client);
            data = data.substr(7);
          }
        }
      }
      else if(headers.get('TYPE') === 'typing_status') {
        if(headers.get('TYPING') === 'true') {
          userTyping(headers.get('CLIENT_COLOR'));
        }
        else if(headers.get('TYPING') === 'false') {
          userNotTyping(headers.get('CLIENT_COLOR'));
        }
      }
      else if(headers.get('TYPE') === 'buffer_status') {
        if(headers.get('BUFFER') === 'true') {
          userBuffered(headers.get('CLIENT_COLOR'));
        }
        else if(headers.get('BUFFER') === 'false') {
          userNotBuffered(headers.get('CLIENT_COLOR'));
        }
      }
      else {
        $('#messages').prepend('<div class="small-card"><p class="status">' + data + '</p></div>');
      }
    }
    else {
      var color = headers.get('CLIENT_COLOR');
      var textColor = headers.get('TEXT_COLOR');
      $('#messages').prepend('<div class="small-card" style="background-color:#' + color + '"><p style="color:#' + textColor + '">' + data + '</p></div>');
    }
  }
  function wsOpen(message) {
    $('#status').replaceWith('<div class="small-card" id="status"><p class="status">Connected!</p>');
    window.setTimeout(function () {
      $('#status').remove();
    }, 3000);
  }
  function wsClose(message) {
    $('#updates').prepend('<div class="small-card"><p class="warn">Disconnected</p></div>');
    window.setTimeout(function () {
      $('#updates').prepend('<div class="small-card"><p class="status">Reloading...</p></div>');
      location.reload();
    }, 3000);
  }
  function wsError(message) {
    $('#updates').prepend('<div class="small-card"><p class="warn">Error!</p></div>');
  }

  var canSendTyping = true;
  var typing = false;
  var buffered = false;
  $("#message").on("change keyup keydown paste", function(event) {
    if(message.value.trim() !== "") {
      if(!buffered) {
        webSocket.send("type:buffer,buffer:true|true");
        buffered = true;
      }
    }
    else {
      if(buffered)
      {
        webSocket.send("type:buffer,buffer:false|false");
        buffered = false;
      }
    }

    if(event.which !== 13 && event.which !== 8 && typeof event.which !== "undefined") {
      if(canSendTyping) {
        if(typing == false) {
          typing = true;
          webSocket.send("type:typing,typing:true|true");
        }
        canSendTyping = false;
        setTimeout(function() {
          canSendTyping = true;
          setTimeout(function() {
            if(canSendTyping) {
              webSocket.send("type:typing,typing:false|false");
              typing = false;
            }
          }, 300);
        }, 1000);
      }
    }
  });

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
