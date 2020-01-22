/* global gapi, grecaptcha */
/* exported signOut, onSignIn, checkStatus, recaptchaCallback */
/* eslint-env browser*/

var HTTP_READY = 4;
var HTTP_OK = 200;
var HTTP_CLIENT_ERROR = 401;
var RECAPTCHA_TIMEOUT = 2000; // in milliseconds

var domain = document.getElementById("domain").getAttribute("value");
var redirect = document.getElementById("redirect").getAttribute("value");
console.log(redirect);

function signOut() {
  var auth2 = gapi.auth2.getAuthInstance();
  auth2.signOut().then(function() {
    console.log("User signed out.");
  });
}

function onSignIn(googleUser) {
  var id_token = googleUser.getAuthResponse().id_token;
  var xhr = new XMLHttpRequest();
  xhr.open("POST", domain + "/auth/google");
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onload = function() {
    console.log("response loaded");
  };
  xhr.send("id_token=" + id_token);
}

function checkStatus() {
  document.getElementById("notice").innerHTML = "Checking...";
  var xhr = new XMLHttpRequest();
  xhr.open("GET", domain + "/auth/status");
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onreadystatechange = function() {
    if (this.readyState === HTTP_READY) {
      if (this.status === HTTP_OK) {
        document.getElementById("notice").innerHTML = "You Are Authorized";
        return true;
      }
      else if (this.status === HTTP_CLIENT_ERROR) {
        document.getElementById("notice").innerHTML = "You Are Still Not Authorized";
      }
      else {
        document.getElementById("notice").innerHTML = "Something Unexpected Happened";
      }
    }
    else if (this.readyState < HTTP_READY) {
      document.getElementById("notice").innerHTML = "Checking Status...";
    }
  };
  xhr.send();
}

function checkStatusHidden() {
  var xhr = new XMLHttpRequest();
  xhr.open("GET", domain + "/auth/status");
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
  try {
    xhr.send();
  }
  catch (e) {
    console.log(e);
  }
}

function recaptchaCallback() {
  document.getElementById("recaptcha_check_empty").value = 1;
  var recaptcha_data = grecaptcha.getResponse();
  var xhr = new XMLHttpRequest();
  xhr.open("POST", domain + "/auth/recaptcha");
  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  xhr.onload = function() {
    var recaptcha_worked = setTimeout(checkStatusHidden(), RECAPTCHA_TIMEOUT);
    if (recaptcha_worked) {
      var to_remove = document.getElementById("recaptcha-form");
      to_remove.parentNode.removeChild(to_remove);

      if (redirect !== null) {
        window.location.replace(domain + redirect);
      }
    }
  };
  xhr.send("data=" + recaptcha_data);
}
