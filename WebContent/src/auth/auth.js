function signOut() {
	var auth2 = gapi.auth2.getAuthInstance();
	auth2.signOut().then(function () {
		console.log('User signed out.');
	});
}
function onSignIn(googleUser)
{
	var profile = googleUser.getBasicProfile();
	console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
	console.log('Name: ' + profile.getName());
	console.log('Image URL: ' + profile.getImageUrl());
	console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
	var id_token = googleUser.getAuthResponse().id_token;
	console.log(id_token);
	var xhr = new XMLHttpRequest();
	xhr.open('POST', 'http://localhost:8080/xarql/auth/google');
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.onload = function() {
	  console.log('response loaded');
	};
	xhr.send('id_token=' + id_token);
}
$(document).ready(function () {
	function enableSignOut() {
		$('#google-p').on('click', function() {
			$(this).html('<a href="#">Sign out</a>');
			signOut();
		});
	}
	enableSignOut();
});
