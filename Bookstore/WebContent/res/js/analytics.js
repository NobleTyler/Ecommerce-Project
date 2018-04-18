/**
 * 
 */


function validate()
{
	var result = true;
	var error = "";
	var prefix = document.getElementById("namePrefix").value;
	var creditTaken = document.getElementById("creditTaken").value;
	
	//alert("grab it and hold on we going for a wild ride");
	if (isNaN(creditTaken) || creditTaken < 0 || creditTaken.trim() == "" )
	{
		error = "If thats how many credits you've got you fucked up.\n";
		result = false;
	}
	if (prefix.length < 1)
	{
		error = "Im onto your bullshit...put in a real prefix!\n";
		result = false;
	}
	
	if (!result)
	{
		alert(error);
	}
	
	return result;
}



function getPOMonth(address){
	
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



function handler(request){
	 if ((request.readyState == 4) && (request.status == 200))
	 {
		 var target = document.getElementById("results");
		 target.innerHTML = request.responseText;
	 }
	}

