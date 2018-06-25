var buttons = document.getElementsByTagName('button');

Array.prototype.forEach.call(buttons, function (b) {
    b.addEventListener('click', createRipple);
  //  b.addEventListener('mouseenter', makeDark);
  //  b.addEventListener('mouseleave', makeLight);
});

function createRipple(e)
{
  var circle = document.createElement('div');
  this.appendChild(circle);

  var d = Math.max(this.clientWidth, this.clientHeight);

  circle.style.width = circle.style.height = d + 'px';

  var rect = this.getBoundingClientRect();
  circle.style.left = e.clientX - rect.left -d/2 + 'px';
  circle.style.top = e.clientY - rect.top - d/2 + 'px';

  circle.classList.add('ripple');
  setTimeout(function() {
    circle.parentNode.removeChild(circle);
  }, 500);
}

function makeDark(e)
{
  var shade = document.createElement('div');
  this.appendChild(shade);

  var d = Math.max(this.clientWidth, this.clientHeight);

  shade.style.width = shade.style.height = d + 'px';

  var rect = this.getBoundingClientRect();
  shade.style.left = e.clientX - rect.left -d/2 + 'px';
  shade.style.top = e.clientY - rect.top - d/2 + 'px';

  circle.classList.add('dark');
  this.addEventListener('mouseleave', makeLight);
}

function makeLight(e)
{
  this.removeChild(shade);
}
