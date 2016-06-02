(function() {
	console.log('hello world');
	
	var p = document.createElement('p');
	p.innerHTML = 'I was appended through Javascript';
	
	var img = document.getElementsByTagName('img')[0];
	
	img.parentNode.insertBefore(p, img);
})();