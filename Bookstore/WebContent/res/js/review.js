     
        function count_down(obj) {
             
            var element = document.getElementById('count2');
             
            element.innerHTML = 100 - obj.value.length;
             
            if (100 - obj.value.length < 0) {
                alert("Too much text")
             
            } else {
                element.style.color = 'grey';
                element.innerHTML =object.value.length + "/" +"100"; 
            }
             
        }