/* global document, grecaptcha, XMLHttpRequest, console, setTimeout */
/* exported recaptchaCallback */

var HTTP_READY = 4;
var HTTP_OK = 200;
var HTTP_CLIENT_ERROR = 401;
var RECAPTCHA_TIMEOUT = 2000; // in milliseconds

var domain = document.getElementById("domain").getAttribute("value");
function checkStatusHidden() {
  var xhr = new XMLHttpRequest();
  xhr.open("GET", domain + "/-/auth/status");
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onreadystatechange = function() {
    if (this.readyState === HTTP_READY) {
      if (this.status === HTTP_OK) {
        return true;
      }
      else if (this.status === HTTP_CLIENT_ERROR) {
        return false;
      }
      else {
        return false;
      }
    }
  };
  xhr.send();
}

function recaptchaCallback() {
  document.getElementById("recaptcha_check_empty").value = 1;
  var recaptchaData = grecaptcha.getResponse();
  var xhr = new XMLHttpRequest();
  xhr.open("POST", domain + "/-/auth/recaptcha");
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onload = function() {
    console.log("response loaded");
  };
  xhr.send("data=" + recaptchaData);
  var recaptchaWorked = setTimeout(checkStatusHidden(), RECAPTCHA_TIMEOUT);
  if (recaptchaWorked) {
    var toRemove = document.getElementById("recaptcha-form");
    toRemove.parentNode.removeChild(toRemove);
  }
}
