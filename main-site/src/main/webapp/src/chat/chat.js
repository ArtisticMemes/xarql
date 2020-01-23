/* global $, Map, Cookies */
/* eslint-env browser*/

$(document).ready(function() {
  var NOTICE_TIMEOUT = 3000; // milliseconds
  var TYPING_UPDATE_TIMEOUT = 300;
  var TYPING_STATUS_TIME = 1000;
  var KEYCODE_ENTER = 13;
  var KEYCODE_BACKSPACE = 8;
  var ID_LENGTH = 6;

  // update Messages
  var domain = $("#domain").attr("value");
  var room = $("#room").attr("value");
  var webSocket = new WebSocket();
  console.log(room);

  function addUser(color) {
    $("#users").append("<div class=\"user-icon\" id=\"user-" + color + "\" style=\"background-color:#" + color + "\"></div>");
  }

  function removeUser(color) {
    $("#user-" + color).remove();
  }

  function userTyping(color) {
    if (Cookies.get("theme") === "dark") {
      $("#user-" + color).css("box-shadow", "0px 5px 5px #000");
    }
    else {
      $("#user-" + color).css("box-shadow", "0px 5px 5px #999");
    }
  }

  function userNotTyping(color) {
    $("#user-" + color).css("box-shadow", "none");
  }

  function userBuffered(color) {
    $("#user-" + color).css("border-radius", "100%");

  }

  function userNotBuffered(color) {
    $("#user-" + color).css("border-radius", "25%");
  }

  // simple data function
  function parseHeaders(message) {
    var data = message.data;
    var headers = new Map();
    // repeat until all key:value,key:value pairs are consumed and | terminates them
    while (data.indexOf(":") !== -1 && data.indexOf(":") < data.indexOf("|")) {
      // get key from key:value pair
      var param = data.substr(0, data.indexOf(":"));
      // remove the key from the start of data
      data = data.substr(data.indexOf(":") + 1);
      // get the index of , or | --> whichever comes first
      var stop = 0;
      if (data.indexOf(",") !== -1 && data.indexOf(",") < data.indexOf("|")) {
        stop = data.indexOf(",");
      }
      else {
        stop = data.indexOf("|");
      }
      // get value from key:value pair
      var value = data.substr(0, stop);
      // trim the value and , or | from data
      data = data.substr(stop + 1);
      // add the key:value pair to the map
      headers.set(param, value);
    }
    return headers;
  }

  function parseContent(message) {
    var data = message.data;
    data = data.substr(data.indexOf("|") + 1, data.length - data.indexOf("|"));
    return data;
  }

  function wsSendMessage(message) {
    if (message.value.length !== 0 && message.value.trim() !== "") {
      webSocket.send("type:message|" + message.value);
    }
    message.value = "";
  }

  function wsGetMessage(message) {
    var content = parseContent(message);
    var headers = parseHeaders(message);
    if (headers.get("TYPE") === "message") {
      var color = headers.get("CLIENT_COLOR");
      var textColor = headers.get("TEXT_COLOR");
      $("#messages").prepend("<div class=\"small-card\" style=\"background-color:#" + color + "\"><p style=\"color:#" + textColor + "\">" + content + "</p></div>");
    }
    else if (headers.get("TYPE") === "user_join") {
      addUser(headers.get("CLIENT_COLOR"));
    }
    else if (headers.get("TYPE") === "user_exit") {
      removeUser(headers.get("CLIENT_COLOR"));
    }
    else if (headers.get("TYPE") === "users_report") {
      content = content.substr(ID_LENGTH + 1);
      if (content.length > 1) {
        while (content.length > 0) {
          var client = content.substr(1, ID_LENGTH);
          addUser(client);
          content = content.substr(ID_LENGTH + 1);
        }
      }
    }
    else if (headers.get("TYPE") === "typing_status") {
      if (headers.get("TYPING") === "true") {
        userTyping(headers.get("CLIENT_COLOR"));
      }
      else if (headers.get("TYPING") === "false") {
        userNotTyping(headers.get("CLIENT_COLOR"));
      }
    }
    else if (headers.get("TYPE") === "buffer_status") {
      if (headers.get("BUFFER") === "true") {
        userBuffered(headers.get("CLIENT_COLOR"));
      }
      else if (headers.get("BUFFER") === "false") {
        userNotBuffered(headers.get("CLIENT_COLOR"));
      }
    }
    else {
      $("#messages").prepend("<div id=\"status-tmp\" class=\"small-card\"><p class=\"status\">" + content + "</p></div>");
      window.setTimeout(function() {
        $("#status-tmp").remove();
      }, NOTICE_TIMEOUT);
    }
  }

  function wsOpen() {
    $("#status").replaceWith("<div class=\"small-card\" id=\"status\"><p class=\"status\">Connected!</p>");
    window.setTimeout(function() {
      $("#status").remove();
    }, NOTICE_TIMEOUT);
  }

  function wsClose() {
    $("#updates").prepend("<div class=\"small-card\"><p class=\"warn\">Disconnected</p></div>");
    window.setTimeout(function() {
      $("#updates").prepend("<div class=\"small-card\"><p class=\"status\">Reloading...</p></div>");
      location.reload();
    }, NOTICE_TIMEOUT);
  }

  function wsError() {
    $("#updates").prepend("<div class=\"small-card\"><p class=\"warn\">Error!</p></div>");
  }

  // aJAX posting
  $("#message-form" ).submit(function(event) {
    // stop form from submitting normally
    event.preventDefault();
    wsSendMessage();
  });

  $("#send-button").on("click", function() {
    wsSendMessage();
  });

  function protocolOf(domain) {
    if (domain.includes("https://")) {
      return "wss";
    }
    else {
      return "ws";
    }
  }

  $("#updates").prepend("<div class=\"small-card\" id=\"status\"><p class=\"status\">Connecting...</p></div>");
  var protocol = protocolOf(domain);
  webSocket = new WebSocket(protocol + domain.substr(domain.indexOf("://")) + "/chat/websocket/" + room + "/" + Cookies.get("chat-id").substr(1));
  var message = document.getElementById("message");
  webSocket.onopen = function(message) {
    delete message;
    wsOpen();
  };
  webSocket.onmessage = function(message) {
    wsGetMessage(message);
  };
  webSocket.onclose = function(message) {
    delete message;
    wsClose();
  };
  webSocket.onerror = function(message) {
    delete message;
    wsError();
  };

  var canSendTyping = true;
  var typing = false;
  var buffered = false;
  $("#message").on("change keyup keydown paste", function(event) {
    if (message.value.trim() !== "") {
      if (!buffered) {
        webSocket.send("type:buffer,buffer:true|true");
        buffered = true;
      }
    }
    else if (buffered) {
      webSocket.send("type:buffer,buffer:false|false");
      buffered = false;
    }

    if (event.which !== KEYCODE_ENTER && event.which !== KEYCODE_BACKSPACE && typeof event.which !== "undefined") {
      if (canSendTyping) {
        if (typing === false) {
          typing = true;
          webSocket.send("type:typing,typing:true|true");
        }
        canSendTyping = false;
        setTimeout(function() {
          canSendTyping = true;
          setTimeout(function() {
            if (canSendTyping) {
              webSocket.send("type:typing,typing:false|false");
              typing = false;
            }
          }, TYPING_STATUS_TIME);
        }, TYPING_UPDATE_TIMEOUT);
      }
    }
  });

  // change font size
  $("html").css("font-size", Cookies.get("font-size"));
  function changeFontSize(n) {
    var computedFontSize = parseFloat(window.getComputedStyle(document.getElementById("html")).fontSize); // get font size of <html></html>
    $("#font-size").remove();
    $("html").css("font-size", computedFontSize + n + "px"); // change font size by -1
    Cookies.set("font-size", computedFontSize + n + "px");
  }

  $("#text-up").on("click", function() {
    changeFontSize(1);
  });

  $("#text-dn").on("click", function() {
    changeFontSize(-1);
  });

  // change theme
  function changeTheme(theme) {
    if (theme !== "light" && theme !== "dark" && theme !== "rainbow") {
      theme = "light";
    }
    $("#theme-styles").attr("href", domain + "/src/common/" + theme + "-common.min.css");
    Cookies.set("theme", theme);
    $(".theme-button").show();
    $("#" + theme + "-theme-button").hide();
  }
  changeTheme(Cookies.get("theme"));
  $(".theme-button").each(function() {
    var $this = $(this);
    $this.on("click", function() {
      changeTheme($this.attr("data"));
    });
  });

  // change font weight
  function fontWeight(weight) {
    if (weight === "light") {
      $("p").css("font-weight", "200");
      $(".bold").css("font-weight", "400");
      $("h6").css("font-weight", "400");
      $("#font-light-button").hide();
      $("#font-normal-button").show();
      Cookies.set("font-weight", "light");
    }
    else {
      $("p").css("font-weight", "400");
      $(".bold").css("font-weight", "600");
      $("h6").css("font-weight", "600");
      $("#font-normal-button").hide();
      $("#font-light-button").show();
      Cookies.set("font-weight", "normal");
    }
  }

  fontWeight(Cookies.get("font-weight"));
  $("#font-light-button").on("click", function() {
    fontWeight("light");
  });
  $("#font-normal-button").on("click", function() {
    fontWeight("normal");
  });

  // show option pane
  $(".ajax-bar").each(function() {
    $(this).show();
  });
  $("#option-pane-open-button").on("click", function() {
    $("#option-pane").show();
    $("#option-pane-close-button").show();
    $(this).hide();
  });
  $("#option-pane-close-button").on("click", function() {
    $("#option-pane").hide();
    $("#option-pane-open-button").show();
    $(this).hide();
  });
});
