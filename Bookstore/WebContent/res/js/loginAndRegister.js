function registerAjax(address) {
	//perform simple validation
	if (validateRegisterFields()) {
		registerUser(address);
	}
}

function validateRegisterFields() {
	document.getElementById("uname-error").innerHTML = " ";
	document.getElementById("rpsw-error").innerHTML = " ";		//clearing previous error fields
	document.getElementById("password-error").innerHTML = " ";
	document.getElementById("fname-error").innerHTML = " ";
	document.getElementById("city-error").innerHTML = " ";
	document.getElementById("address-error").innerHTML = " ";
	document.getElementById("province-error").innerHTML = " ";
	document.getElementById("pcode-error").innerHTML = " ";
	
	var tester=true;
	var regex=/^(?=.*\d)(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z]).{6,9}$/;
	var regexlogin=/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	var minLength=6;
	var maxLength=9;

	errors=[];
	
	var username = document.getElementById("regUname").value;
	var password = document.getElementById("regPsw").value;
	var rpassword = document.getElementById("rRegPsw").value;
	var fname = document.getElementById("fname").value;
	var city=document.getElementById("city").value;
	var address=document.getElementById("street").value;
	var province=document.getElementById("province").value;
	var postalCode=document.getElementById("pcode").value;
	
	if(username.trim() == "") {				//the username field is empty		
		document.getElementById("uname-error").innerHTML = "Username must contain characters";
		tester= false;
	}
	if(regexlogin.test(username)==false){
		errors.push("Username must be a valid email adress.");
		tester=false;
	}
	if(password != rpassword) { 			//the user did not type in the same password twice
		document.getElementById("rpsw-error").innerHTML = "Please re-enter the same password";
		tester=false
	}
	if(password.trim()==""){
		document.getElementById("password-error").innerHTML = "Password must contain characters";
		tester=false;
	}
	if(regex.test(password)==false){
		errors.push("Password must be "+minLength+"-"+maxLength+" characters long and \n" +
				"-must contain a special character\n" +
				"-must contain an uppercase letter\n" +
				"-must contain a lowercase letter\n" +
				"-must contain a number");
		tester=false;
	}
	
	if(fname.trim()==""){
		document.getElementById("fname-error").innerHTML = "Full name cannot be empty";
		tester=false;
	}
	if(city.trim()==""){
		document.getElementById("city-error").innerHTML = "City name cannot be empty";
		tester=false;
	}
	if(address.trim()==""){
		document.getElementById("address-error").innerHTML = "Address cannot be empty";
		tester=false;
	}
	if(province.trim()==""){
		document.getElementById("province-error").innerHTML = "Province cannot be empty";
		tester=false;
	}
	if(postalCode.trim()==""){
		document.getElementById("pcode-error").innerHTML = "Postal code cannot be empty";
		tester=false;
	}
	
	if(errors.length>0){
		alert(errors.join("\n"))
	}
	
	return tester;
}

function clearRegisterFields() {
	document.getElementById("register-form").reset();
	document.getElementById("uname-error").innerHTML = " ";
	document.getElementById("rpsw-error").innerHTML = " ";		//clearing previous error fields
	document.getElementById("fname-error").innerHTML = " ";
	document.getElementById("city-error").innerHTML = " ";
	document.getElementById("address-error").innerHTML = " ";
	document.getElementById("province-error").innerHTML = " ";
	document.getElementById("pcode-error").innerHTML = " ";
}

function registerUser(address) {
	//var username = document.getElementById("")
	//document.getElementById("register-content").innerHTML = "<center>Replace login</center>";
	var request = new XMLHttpRequest();
	var data = '';
	
	var username = document.getElementById("regUname").value;
	var password = document.getElementById("regPsw").value;
	var rpassword = document.getElementById("rRegPsw").value;
	var fname = document.getElementById("fname").value;
	var street = document.getElementById("street").value;
	var city = document.getElementById("city").value;
	var province = document.getElementById("province").value;
	var pcode = document.getElementById("pcode").value;
	
	data += "uname=" + username + "&psw=" + password + "&rpsw=" + rpassword;
	data += "&fname=" + fname + "&street=" + street + "&city=" + city;
	data += "&province=" + province + "&pcode=" + pcode;
	
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
	document.getElementById("login-uname-error").innerHTML = " ";		//clearing previous error messages
	document.getElementById("wrong-psw-error").innerHTML = " ";
	
	var username = document.getElementById("uname").value;
	
	if (username.trim() == "") {
		document.getElementById("login-uname-error").innerHTML = "Please enter a valid username.";
		return false;
	}
	else {
		return true;
	}
	
}

function clearLoginFields() {
	document.getElementById("login-form").reset();
	document.getElementById("login-uname-error").innerHTML = " ";		//clearing previous error messages
	document.getElementById("wrong-psw-error").innerHTML = " ";
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
		else {	//successful login, redirect to home page
			window.location.replace(window.location.href);
		}
		
		//document.getElementById("wrong-psw-error").innerHTML = request.responseText;
	}
	
}