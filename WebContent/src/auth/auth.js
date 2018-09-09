function signOut() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function () {
		console.log('User signed out.');
	});
}
function onSignIn(googleUser)
{
	var id_token = googleUser.getAuthResponse().id_token;
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'http://xarql.com/auth/google');
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.onload = function() {
	  //console.log('response loaded');
	};
	xhr.send('id_token=' + id_token);
}
function recaptchaCallback()
{
	document.getElementById('recaptcha_check_empty').value = 1;
	var recaptchaData = grecaptcha.getResponse();
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'http://xarql.com/auth/recaptcha');
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.onload = function() {
	  //console.log('response loaded');
	};
	xhr.send('data=' + recaptchaData);
}
function checkStatus()
{
	var xhr = new XMLHttpRequest();
	xhr.open('GET', 'http://xarql.com/auth/status');
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() 
    {
        if (this.readyState == 4) 
        {
        	if(this.status == 200)
        		document.getElementById('notice').innerHTML = 'You Are Authorized';
        	else if(this.status == 401)
        		document.getElementById('notice').innerHTML = 'You Are Still Not Authorized';
        	else
        		document.getElementById('notice').innerHTML = 'Something Unexpected Happened';
        }
    };
	xhr.send();
}