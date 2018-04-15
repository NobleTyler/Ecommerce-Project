function validateInfo(){
	var cFname = document.getElementById("cfname").value;
	var cLname = document.getElementById("clname").value;
	var ccNum = document.getElementById("ccnum").value;
	var expMonth = document.getElementById("expmonth").value;
	var expYear = document.getElementById("expyear").value;
	var cvv = document.getElementById("cvv").value;
	
	errors=[];
	var tester=true;
	var cardRegex = /^([0-9]{4}[\s-]?){3}([0-9]{4})$/;
	var pinRegex = /^[0-9]{3}$/;
	var today = new Date();
	var dateProvided = new Date();
	dateProvided.setFullYear(expYear, expMonth, 1);

	if(expMonth>=1 && expMonth<=12){
		if(dateProvided<today){
			errors.push("ERROR: The date provided is incorrect");
			tester=false;
		}
	}
	else{
		errors.push("ERROR: The month provided doesn't make any sense");
		tester=false
	}
	
	if(cardRegex.test(ccNum)==false){
		errors.push("ERROR: The credit card number provided is in the incorrect format\n" +
				"-1111-2222-3333-4444 or 1111 2222 3333 4444 are " +
				"the correct formats");
		tester=false;
	}
	
	if(pinRegex.test(cvv)==false){
		errors.push("ERROR: The security code provided has an incorrect format");
		tester=false;
	}
	
	if(errors.length>0){
		alert(errors.join("\n\n"))
	}
	
	return tester;
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