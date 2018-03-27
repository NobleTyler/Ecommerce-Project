function addItemToCart() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			document.getElementById("cartButton").innerHTML = 1;
		}
	};
}

function addTest() {
	document.getElementById("cartButton").innerHTML = 1;
}

function handler(request) {
	var target = document.getElementById("cartButton").innerHTML;
	if (request.readyState == 4 && request.status == 200) {
		target.innerHTML = 1
	}
}