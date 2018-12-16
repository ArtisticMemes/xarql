var domain = document.getElementById('domain').getAttribute('value');
function recaptchaCallback()
{
	document.getElementById('recaptcha_check_empty').value = 1;
	var recaptchaData = grecaptcha.getResponse();
	var xhr = new XMLHttpRequest();
	xhr.open('POST', domain + '/-/auth/recaptcha');
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.onload = function() {
	  //console.log('response loaded');
	};
	xhr.send('data=' + recaptchaData);
	var recaptchaWorked = setTimeout(checkStatusHidden(), 2000);
	if(recaptchaWorked)
	{
		var toRemove = document.getElementById('recaptcha-form');
	    toRemove.parentNode.removeChild(toRemove);
	}
}
function checkStatusHidden()
{
	var xhr = new XMLHttpRequest();
	xhr.open('GET', domain + '/-/auth/status');
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function()
    {
        if(this.readyState == 4)
        {
        	if(this.status == 200)
        		return true;
        	else if(this.status == 401)
        		return false;
        	else
        		return false;
        }
    };
	xhr.send();
}