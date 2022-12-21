function validate_input1() {
    var regex = /^[1-9]{1}$/;
    var input = document.getElementById("xfield").value;
    if(regex.test(input) == false) {
        document.getElementById("s1").style.visibility = "visible";
        document.getElementById("xfield").style.backgroundColor = "red";
        document.getElementById("xfield").style.color = "white";
        return false;
    } else {
        document.getElementById("s1").style.visibility = "hidden";
        document.getElementById("xfield").style.backgroundColor = "white";
        document.getElementById("xfield").style.color = "black";
        return true;
    }
}

function validate_input2() {
    var regex = /^[1-9]{1}$/;
    var input = document.getElementById("yfield").value;
    if(regex.test(input) == false) {
        document.getElementById("s2").style.visibility = "visible";
        document.getElementById("yfield").style.backgroundColor = "red";
        document.getElementById("yfield").style.color = "white";
        return false;
    } else {
        document.getElementById("s2").style.visibility = "hidden";
        document.getElementById("yfield").style.backgroundColor = "white";
        document.getElementById("yfield").style.color = "black";
        return true;
    }
}

function create_table() {
    var x = document.getElementById('xfield').value;
    var y = document.getElementById('yfield').value;
    for(var i = 0; i <= parseInt(y); i++) {
        var table = document.getElementById('tbl').insertRow(i);
        for(var j = 0; j <= parseInt(x); j++) {
            var newcell =  table.insertCell(j);
            if (i == 0 && j > 0) {
                newcell.innerHTML='X=' + (j)*(i+1); 
            }
            else if (i > 0 && j == 0) {
                newcell.innerHTML='Y=' + (j+1)*(i); 
            }
            else if(i > 0 && j > 0) {
                newcell.innerHTML=(j)*(i); 
            }
        }
    }
}

function generate_if() {
    if(validate_input1() && validate_input2()) {
        var win = document.getElementById("popup");
        create_table();
        win.style.display = "block";
    }
}

function remove_table() 
{
    var y = document.getElementById('yfield').value;
    var win = document.getElementById("popup");
    for (var i = 0; i <= y; i++) {
        document.getElementById("tbl").deleteRow(0);
    }
    win.style.display = "none";
}
