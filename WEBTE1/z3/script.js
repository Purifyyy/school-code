function age_difference() {
    var input = document.getElementById('bday').value;
    var age = document.getElementById('age').value;
    if(input && age) {
        var d = new Date();
        var bday = new Date(input);
        var expected_age = d.getFullYear() - bday.getFullYear();
        
        if(d.getMonth() < bday.getMonth()) {
            expected_age--;
        } 
        else if(d.getMonth() == bday.getMonth()) {
            if(d.getDate() < bday.getDate()) {
                expected_age--;        
            }
        }
        if(expected_age != age) {
            document.getElementById("page").innerHTML = "Vek sa nezhoduje s dátumom narodenia";
            document.getElementById("page").style.visibility = "visible";
            document.getElementById("age").style.borderColor = "red";
            document.getElementById("pbday").innerHTML = "Dátum narodenia sa nezhoduje s vekom";
            document.getElementById("pbday").style.visibility = "visible";
            document.getElementById("bday").style.borderColor = "red";
            return !(expected_age != age);
        } else {
            document.getElementById("page").style.visibility = "hidden";
            document.getElementById("age").style.borderColor = "black";
            document.getElementById("pbday").style.visibility = "hidden";
            document.getElementById("bday").style.borderColor = "black";
            return !(expected_age != age);
        }
    }
}

function check_name() {
    var name = document.getElementById('name').value;
    var reg = /^[A-Za-z]{1,}$/;
    if(reg.test(name) == true) {
        document.getElementById("name").style.borderColor = "black";
        document.getElementById("pname").style.visibility = "hidden";
        return reg.test(name) == true;
    } else {
        document.getElementById("name").style.borderColor  = "red";
        document.getElementById("pname").style.visibility = "visible";
        return reg.test(name) == true;
    }
}

function check_sname() {
    var sname = document.getElementById('sname').value;
    var reg = /^[A-Za-z]{1,}$/;
    if(reg.test(sname) == true) {
        document.getElementById("sname").style.borderColor = "black";
        document.getElementById("psname").style.visibility = "hidden";
        return reg.test(sname) == true;
    } else {
        document.getElementById("sname").style.borderColor  = "red";
        document.getElementById("psname").style.visibility = "visible";
        return reg.test(sname) == true;
    }
}

function chceck_sub() {
    if(age_difference() && check_name() && check_sname()) {
        return true;
    } else {
        return false
    }
}

function check_telnum() {
    var number = document.getElementById('num').value;
    var reg = /09[0-9]{8}/;
    if(reg.test(number) == true) {
        document.getElementById("num").style.borderColor = "black";
        document.getElementById("pnum").style.visibility = "hidden";
    } else {
        document.getElementById("num").style.borderColor  = "red";
        document.getElementById("pnum").style.visibility = "visible";
    }
}

function check_mail() {
    var mail_value = document.getElementById('mail').value;
    var reg = /^.{3,}@.{1,}(([.]{1}.{0,}){0,})[.]{1}.{2,4}$/;
    if(reg.test(mail_value) == false)
    {
        document.getElementById("pmail").style.visibility = "visible";
        document.getElementById("mail").style.borderColor = "red";
    } else {
        document.getElementById("pmail").style.visibility = "hidden";
        document.getElementById("mail").style.borderColor = "black";
    }
}

function select_changer1() {
    if(sel1.options[sel1.selectedIndex].index == 0) {
        sel2.options[0].text = "Na báze proteínu";
        sel2.options[0].value = "Na báze proteínu";
        sel2.options[1].text = "Adenovírus";
        sel2.options[1].value = "Adenovírus";
        sel2.options[2].text = "mRNA";
        sel2.options[2].value = "mRNA";
        sel3.options[0].text = "FBRI SRC VB VECTOR";
        sel3.options[0].value = "FBRI SRC VB VECTOR";
        sel3.options[1].text = "Sanofi-GSK";
        sel3.options[1].value = "Sanofi-GSK";
        sel3.options[2].text = "Novavax";
        sel3.options[2].value = "Novavax";
        document.getElementById('ftype').innerHTML = "Typ vakcíny";
        document.getElementById('fopt').innerHTML = "Spoločnosť";
        document.getElementById('sel2').name = "Typ vakcíny";
        document.getElementById('sel3').name = "Spoločnosť"; 
    }
    else if(sel1.options[sel1.selectedIndex].index == 1) {
        sel2.options[0].text = "Molekulárny test PCR";
        sel2.options[0].value = "Molekulárny test PCR";
        sel2.options[1].text = "Antigénový test";
        sel2.options[1].value = "Antigénový test";
        sel2.options[2].text = "Test na protilátky";
        sel2.options[2].value = "Test na protilátky";
        sel3.options[0].text = "<24h";
        sel3.options[0].value = "<24h";
        sel3.options[1].text = "<48h";
        sel3.options[1].value = "<48h";
        sel3.options[2].text = "<72h";
        sel3.options[2].value = "<72h";
        document.getElementById('ftype').innerHTML = "Typ testu"; 
        document.getElementById('fopt').innerHTML = "Čas od vykonania testu";
        document.getElementById('sel2').name = "Typ testu";
        document.getElementById('sel3').name = "Čas od vykonania testu"; 
    } else {
        sel2.options[0].text = "Bezpríznakový priebeh";
        sel2.options[0].value = "Bezpríznakový priebeh";
        sel2.options[1].text = "Ľahký priebeh";
        sel2.options[1].value = "Ľahký priebeh";
        sel2.options[2].text = "Ťažký priebeh";
        sel2.options[2].value = "Ťažký priebeh";
        sel3.options[0].text = "<60 dní";
        sel3.options[0].value = "<60 dní";
        sel3.options[1].text = "<120 dní";
        sel3.options[1].value = "<120 dní";
        sel3.options[2].text = "<180 dní";
        sel3.options[2].value = "<180 dní";
        document.getElementById('ftype').innerHTML = "Priebeh ochorenia"; 
        document.getElementById('fopt').innerHTML = "Doba od prekonania ochorenia";
        document.getElementById('sel2').name = "Priebeh ochorenia";
        document.getElementById('sel3').name = "Doba od prekonania ochorenia"; 
    }
}

function select_changer2() {
    if(sel1.options[sel1.selectedIndex].index == 0 && sel2.options[sel2.selectedIndex].index == 0) {
        sel3.options[0].text = "FBRI SRC VB VECTOR";
        sel3.options[0].value = "FBRI SRC VB VECTOR";
        sel3.options[1].text = "Sanofi-GSK";
        sel3.options[1].value = "Sanofi-GSK";
        sel3.options[2].text = "Novavax";
        sel3.options[2].value = "Novavax";
        document.getElementById('fopt').innerHTML = "Spoločnosť";
        document.getElementById('sel3').name = "Spoločnosť"; 
    }
    else if(sel1.options[sel1.selectedIndex].index == 0 && sel2.options[sel2.selectedIndex].index == 1) {
        sel3.options[0].text = "Oxford–AstraZeneca";
        sel3.options[0].value = "Oxford–AstraZeneca";
        sel3.options[1].text = "Johnson & Johnson/Janssen Pharmaceuticals";
        sel3.options[1].value = "Johnson & Johnson/Janssen Pharmaceuticals";
        sel3.options[2].text = "Gamaleya Research Institute of Epidemiology and Microbiology";
        sel3.options[2].value = "Gamaleya Research Institute of Epidemiology and Microbiology"; 
        document.getElementById('fopt').innerHTML = "Spoločnosť";
        document.getElementById('sel3').name = "Spoločnosť";  
    } 
    else if(sel1.options[sel1.selectedIndex].index == 0 && sel2.options[sel2.selectedIndex].index == 2) {
        sel3.options[0].text = "BioNTech a Pfizer";
        sel3.options[0].value = "BioNTech a Pfizer";
        sel3.options[1].text = "Moderna";
        sel3.options[1].value = "Moderna";
        sel3.options[2].text = "CureVac";
        sel3.options[2].value = "CureVac";
        document.getElementById('fopt').innerHTML = "Spoločnosť";
        document.getElementById('sel3').name = "Spoločnosť"; 
    }
    else if(sel1.options[sel1.selectedIndex].index == 1 && sel2.options[sel2.selectedIndex].index == 0) {
        sel3.options[0].text = "<24h";
        sel3.options[0].value = "<24h";
        sel3.options[1].text = "<48h";
        sel3.options[1].value = "<48h";
        sel3.options[2].text = "<72h";
        sel3.options[2].value = "<72h";
        document.getElementById('fopt').innerHTML = "Čas od vykovania testu";
        document.getElementById('sel3').name = "Čas od vykovania testu"; 
    }
    else if(sel1.options[sel1.selectedIndex].index == 1 && sel2.options[sel2.selectedIndex].index == 1) {
        sel3.options[0].text = "<24h";
        sel3.options[0].value = "<24h";
        sel3.options[1].text = "<48h";
        sel3.options[1].value = "<48h";
        sel3.options[2].text = "<72h";
        sel3.options[2].value = "<72h";
        document.getElementById('fopt').innerHTML = "Čas od vykovania testu";
        document.getElementById('sel3').name = "Čas od vykovania testu";
    }
    else if(sel1.options[sel1.selectedIndex].index == 1 && sel2.options[sel2.selectedIndex].index == 2) {
        sel3.options[0].text = "Ig S1/S2";
        sel3.options[0].value = "Ig S1/S2";
        sel3.options[1].text = "IgG - IgM";
        sel3.options[1].value = "IgG - IgM";
        sel3.options[2].text = "IgG-IgA-IgM";
        sel3.options[2].value = "IgG-IgA-IgM"; 
        document.getElementById('fopt').innerHTML = "Testovacia báza";
        document.getElementById('sel3').name = "Testovacia báza";
    }
    else if(sel1.options[sel1.selectedIndex].index == 2 && sel2.options[sel2.selectedIndex].index == 0) {
        sel3.options[0].text = "<60 dní";
        sel3.options[0].value = "<60 dní";
        sel3.options[1].text = "<120 dní";
        sel3.options[1].value = "<120 dní";
        sel3.options[2].text = "<180 dní";
        sel3.options[2].value = "<180 dní";
        document.getElementById('fopt').innerHTML = "Doba od prekonania ochorenia";
        document.getElementById('sel3').name = "Doba od prekonania ochorenia";
    }
    else if(sel1.options[sel1.selectedIndex].index == 2 && sel2.options[sel2.selectedIndex].index == 1) {
        sel3.options[0].text = "<60 dní";
        sel3.options[0].value = "<60 dní";
        sel3.options[1].text = "<120 dní";
        sel3.options[1].value = "<120 dní";
        sel3.options[2].text = "<180 dní";
        sel3.options[2].value = "<180 dní"; 
        document.getElementById('fopt').innerHTML = "Doba od prekonania ochorenia";
        document.getElementById('sel3').name = "Doba od prekonania ochorenia";
    }
    else if(sel1.options[sel1.selectedIndex].index == 2 && sel2.options[sel2.selectedIndex].index == 2) {
        sel3.options[0].text = "Žiadne";
        sel3.options[0].value = "Žiadne";
        sel3.options[1].text = "Mierne";
        sel3.options[1].value = "Mierne";
        sel3.options[2].text = "Silné";
        sel3.options[2].value = "Silné";
        document.getElementById('fopt').innerHTML = "Dlhodobé následky";
        document.getElementById('sel3').name = "Dlhodobé následky";
    }
}

function define_transport() {
    if(document.getElementById('other').checked) {
        document.getElementById('txta').style.display = "initial";
        document.getElementById('vehicledef').name = "Iný dopravný prostriedok";
    } else {
        document.getElementById('txta').style.display = "none";
        document.getElementById('vehicledef').name = "";
    }
}

function address_form() {
    if(document.getElementById('viapost').checked) {
        document.getElementById('address_form').style.display = "grid";
        document.getElementById('country').name = "Krajina";
        document.getElementById('city').name = "Mesto";
        document.getElementById('address').name = "Adresa";
    } else {
        document.getElementById('address_form').style.display = "none";
        document.getElementById('country').name = "";
        document.getElementById('city').name = "";
        document.getElementById('address').name = "";
    }
}