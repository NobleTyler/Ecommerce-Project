function validateInfo(){
	var cfname = document.getElementById("cfname").value;
	var clname = document.getElementById("clname").value;
	var ccnum = document.getElementById("ccnum").value;
	var exp = document.getElementById("expmonth").value;
	var expyear = document.getElementById("expyear").value;
	var ccv = document.getElementById("ccv").value;
	
	errors=[];
	var cardRegex=/^([0-9]{4}[\s-]?){3}([0-9]{4})$/;
	
	
}

function addItemToCart(address) {
	var request = new XMLHttpRequest();
	
	request.onreadystatechange = function() {
		handler(request);
	};
	
	request.open("POST", address, true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send();
	
	//alert(address);
}

function handler(request) {
	if (request.readyState == 4 && request.status == 200) {
		document.getElementById("cartButton").innerHTML = request.responseText;
	}
}

function changeItemQuant(address) {
	var request = new XMLHttpRequest();
	
	request.onreadystatechange = function() {
		removeHandler(request);
	};
	
	request.open("POST", address, true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send();
}

function removeHandler(request) {
	if (request.readyState == 4 && request.status == 200) {
		document.getElementById("cartContainer").innerHTML = request.responseText;
	}
}


function removeItemFromCart(address) {
	var request = new XMLHttpRequest();
	
	request.onreadystatechange = function() {
		removeHandler(request);
	};
	
	request.open("POST", address, true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send();
}

function removeHandler(request) {
	if (request.readyState == 4 && request.status == 200) {
		document.getElementById("cartContainer").innerHTML = request.responseText;
	}
}