$(document).ready(function () {
  // Enable Navigation link
  var defaultPage = parseInt($('#page').text(), 10);
  $('#page-dropdown').val(defaultPage);
  $('#sort-dropdown').val($('#sort').text());
  $('#flow-dropdown').val($('#flow').text());
  function navlink() {
	  var page = $('#page-dropdown').val();
      var sort = $('#sort-dropdown').val();
      var flow = $('#flow-dropdown').val();
      $('#nav-link').html('<a href="http://xarql.com/polr/flat?page=' + page + '&sort=' + sort + '&flow=' + flow + '" id="nav-link">Go</a>');
  }
  $('#page-dropdown').change(function () {
	  navlink();
  });
  $('#sort-dropdown').change(function () {
	  navlink();
  });
  $('#flow-dropdown').change(function () {
	  navlink();
  });
  navlink();
});