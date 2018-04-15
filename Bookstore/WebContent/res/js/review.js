function count_down(obj) {

	var element = document.getElementById('count2');

	element.innerHTML = 1000 - obj.value.length;

	if (100 - obj.value.length < 0) {
		alert("Too much text")

	} else {
		element.innerHTML.value = object.value.length;
	}

}

function buttonIsPressed() {
	var ok = false;
	
	
	if(document.getElementById('comment').value==="Enter some text here"){
		document.getElementById('comment').value="";
		//alert(document.getElementById('comment').value)
	}
	
	if (document.getElementById('5-star').checked) {
		ok = true;
	} else if (document.getElementById('4-star').checked) {
		ok = true;
	} else if (document.getElementById('3-star').checked) {
		ok = true;
	} else if (document.getElementById('2-star').checked) {
		ok = true;
	} else if (document.getElementById('1-star').checked) {
		ok = true;
	}

	if (!ok) {
		alert("Please give this book a rating")
		ok = false;
	}
	
	return ok;
}
