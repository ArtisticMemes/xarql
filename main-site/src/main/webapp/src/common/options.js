// Copy & Paste in to another file to limit amount of requests
// Logic for the option pane
$(document).ready(function () {
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
    if(theme === 'light' || theme === 'dark' || theme === 'purple') {
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