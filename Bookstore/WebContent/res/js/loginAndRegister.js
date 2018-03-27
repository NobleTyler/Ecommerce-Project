function registerAjax(address) {
	//perform simple validation
	if (validateRegisterFields()) {
		registerUser(address);
	}
}

function validateRegisterFields() {
	var password = document.getElementById("regPsw").value;
	var rpassword = document.getElementById("rRegPsw").value;
	
	if (password == rpassword) {				//the user typed in the same password twice
		return true;
	}
	else {
		document.getElementById("rpsw-error").innerHTML = "Please re-enter the same password";
		return false;
	}
}

function registerUser(address) {
	//var username = document.getElementById("")
	//document.getElementById("register-content").innerHTML = "<center>Replace login</center>";
	var request = new XMLHttpRequest();
	var data = '';
	
	var username = document.getElementById("regUname").value;
	var password = document.getElementById("regPsw").value;
	var rpassword = document.getElementById("rRegPsw").value;
	
	data += "uname=" + username + "&psw=" + password + "&rpsw=" + rpassword;
	
	request.onreadystatechange = function() {
		registerHandler(request);
	};
	
	request.open("POST", address, true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send(data);
}

function registerHandler(request) {
	
	if (request.readyState == 4 && request.status == 200) {
		
		if (request.responseText.charAt(0) == 'R') {			//Registration successful
			document.getElementById("register-content").innerHTML = request.responseText;
		}
		else {			//registration not successful
			document.getElementById("uname-error").innerHTML = request.responseText;
		}

	}
	
}

function loginAjax(address) {
	if (validateLoginFields()) {
		attemptLogin(address);
	}
}

function validateLoginFields() {

	return true;
	
}

function attemptLogin(address) {
	var request = new XMLHttpRequest();
	var data = '';
	
	var username = document.getElementById("uname").value;
	var password = document.getElementById("psw").value;
	
	data += "uname=" + username + "&psw=" + password;
	
	request.onreadystatechange = function() {
		loginHandler(request);
	};
	
	request.open("POST", address, true);
	request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	request.send(data);
}

function loginHandler(request) {
	
	if (request.readyState == 4 && request.status == 200) {
		
		if (request.responseText.charAt(0) == 'w') {			//login unsuccessful
			document.getElementById("wrong-psw-error").innerHTML = "wrong password entered";
		}
		else {
			window.location.replace("/Bookstore/Start");
		}
		
		//document.getElementById("wrong-psw-error").innerHTML = request.responseText;
	}
	
}