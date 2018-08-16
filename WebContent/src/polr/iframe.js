$(document).ready(function () {
	function getUrlParameter(sParam) {
	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;

	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');

	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	}
	$('.inject-button').each(function () {
		var $this = $(this);
		$this.on("click", function () {
			if($('#iframe-' + $this.attr('post-id')).length == 0) {
				var iframe = document.createElement('iframe');
				iframe.setAttribute('src', 'http://xarql.com/polr/peek?id=' + $this.attr('post-id'));
				iframe.setAttribute('class', 'injected-iframe');
				iframe.setAttribute('id', 'iframe-' + $this.attr('post-id'));
				document.getElementById('wrapper').appendChild(iframe);
				$this.hide();
				$('#remover' + $this.attr('post-id')).show();
				history.pushState("xarql", "xarql", window.location.pathname + "?id=" + $('#main-post').text() + "&iframe=" + $this.attr('post-id'));
			}
		});
	});
	$('.remove-button').each(function () {
		var $this = $(this);
		$this.hide();
		$this.on("click", function () {
			if($('#iframe-' + $this.attr('post-id')).length > 0) {
				history.pushState("xarql", "xarql", window.location.pathname + "?id=" + $('#main-post').text());
				$('#iframe-' + $this.attr('post-id')).remove();
				$this.hide();
				$('#injector' + $this.attr('post-id')).show();
			}
		});
	});
	// Inject iframe mentioned in url
	var urliframe = getUrlParameter('frame');
	if(typeof urliframe != 'undefined') {
		var iframe = document.createElement('iframe');
		iframe.setAttribute('src', 'http://xarql.com/polr/peek?id=' + urliframe);
		iframe.setAttribute('class', 'injected-iframe');
		iframe.setAttribute('id', 'iframe-' + urliframe);
		document.getElementById('wrapper').appendChild(iframe);
		$('#injector' + urliframe).hide();
		$('#remover' + urliframe).show();
	}
});