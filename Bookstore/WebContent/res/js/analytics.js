/**
 * 
 */


function validate()
{
	var result = true;
	var error = "";
	var month = document.getElementById("month").value;

	
	//alert("grab it and hold on we going for a wild ride");
	if (isNaN(month) || month < 0 || creditTaken.trim() == "" || month > 12)
	{
		error = "Please input a proper month \n";
		result = false;
	}
	
	if (!result)
	{
		alert(error);
	}
	
	return result;
}



function getPOMonth(address){
	if(validate(month)){
		 var request = new XMLHttpRequest();
		
		

		 var data= document.getElementById("month");

		 
		 request.onreadystatechange = function()
		 {
				handler(request);
		 };
		 request.open("POST", address, true);
		 request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 request.send(data);
	//	 alert("its javascript mother fucker and it works");
	}
	
}



function handler(request){
	 if ((request.readyState == 4) && (request.status == 200))
	 {
		 var target = document.getElementById("results");
		 target.innerHTML = request.responseText;
	 }
	}

