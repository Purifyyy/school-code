<!DOCTYPE html>
<html lang="sk">
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link rel="stylesheet" href="styles.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <title>Client side</title>
    <script>
        $(document).ready(function(){
            let getInventorMethodId = document.querySelector("#myGET_ID");
            let getInventionsMethodCentury = document.querySelector("#myGET_inventions_century");
            let getEventsMethodYear = document.querySelector("#myGET_events_year");
            let deleteInventorById = document.querySelector("#myDELETE_ID");
            let getInventorMethodSurname = document.querySelector("#myGET_surname");
            let resultModalBody = document.querySelector("#resultModalBodyPre");
            let resultModalHeader = document.querySelector("#resultModalLabel");
            var resultModal = new bootstrap.Modal(document.querySelector("#resultModal"), {
                keyboard: false
            })
            var inputModal = new bootstrap.Modal(document.querySelector("#inputModal"), {
                keyboard: false
            })
            var updateModal = new bootstrap.Modal(document.querySelector("#updateModal"), {
                keyboard: false
            })
            var smallInputModal = new bootstrap.Modal(document.querySelector("#smallInputModal"), {
                keyboard: false
            })
            $("#myGET_events").click(function(){
                $.ajax({
                    url: "..."+getEventsMethodYear.value,
                    type: 'GET',
                    dataType: 'json',
                    success: function(res) {
                        resultModalHeader.innerHTML = "Result";
                        resultModalBody.innerHTML = JSON.stringify(res, null, "   ");
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        resultModalHeader.innerHTML = "Error";
                        resultModalBody.innerHTML = JSON.stringify(XMLHttpRequest, null, "    ");
                    }
                });
                resultModal.show();
            });
            $("#myGET_inventions").click(function(){
                $.ajax({
                    url: "..."+getInventionsMethodCentury.value,
                    type: 'GET',
                    dataType: 'json',
                    success: function(res) {
                        resultModalHeader.innerHTML = "Result";
                        resultModalBody.innerHTML = JSON.stringify(res, null, "   ");
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        resultModalHeader.innerHTML = "Error";
                        resultModalBody.innerHTML = JSON.stringify(XMLHttpRequest, null, "    ");
                    }
                });
                resultModal.show();
            });
            $("#myGET_inventor").click(function(){
                if(getInventorMethodId.value !== "")
                {
                    $.ajax({
                        url: "..."+getInventorMethodId.value,
                        type: 'GET',
                        dataType: 'json',
                        success: function(res) {
                            resultModalHeader.innerHTML = "Result";
                            resultModalBody.innerHTML = JSON.stringify(res, null, "   ");
                        },
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            resultModalHeader.innerHTML = "Error";
                            resultModalBody.innerHTML = JSON.stringify(XMLHttpRequest, null, "    ");
                        }
                    });
                    resultModal.show();
                }
            });
            $("#myGET_inventor_surname").click(function(){
                $.ajax({
                    url: "..."+gtInventorMethodSurname.value,
                    type: 'GET',
                    dataType: 'json',
                    success: function(res) {
                        resultModalHeader.innerHTML = "Result";
                        resultModalBody.innerHTML = JSON.stringify(res, null, "   ");
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        resultModalHeader.innerHTML = "Error";
                        resultModalBody.innerHTML = JSON.stringify(XMLHttpRequest, null, "    ");
                    }
                });
            });
            $("#myGET_inventors").click(function(){
                $.ajax({
                    url: "...",
                    type: 'GET',
                    dataType: 'json',
                    success: function(res) {
                        resultModalHeader.innerHTML = "Result";
                        resultModalBody.innerHTML = JSON.stringify(res, null, "   ");
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        resultModalHeader.innerHTML = "Error";
                        resultModalBody.innerHTML = JSON.stringify(XMLHttpRequest, null, "    ");
                    }
                });
            });
            $("#myPOST_inventor").click(function(){
                if(!document.getElementById('myPOST_inventor_name').validity.valid) {
                    document.getElementById('myPOST_inventor_name').style.borderColor = "red";
                    return;
                } else {
                    document.getElementById('myPOST_inventor_name').style.borderColor = "grey";
                }
                if(!document.getElementById('myPOST_inventor_surname').validity.valid) {
                    document.getElementById('myPOST_inventor_surname').style.borderColor = "red";
                    return;
                } else {
                    document.getElementById('myPOST_inventor_surname').style.borderColor = "grey";
                }
                if(!document.getElementById('myPOST_inventor_birth_date').validity.valid) {
                    document.getElementById('myPOST_inventor_birth_date').style.borderColor = "red";
                    return;
                } else {
                    document.getElementById('myPOST_inventor_birth_date').style.borderColor = "grey";
                }
                if(!document.getElementById('myPOST_inventor_birth_place').validity.valid) {
                    document.getElementById('myPOST_inventor_birth_place').style.borderColor = "red";
                    return;
                } else {
                    document.getElementById('myPOST_inventor_birth_place').style.borderColor = "grey";
                }
                if(!document.getElementById('myPOST_inventor_inventor_description').validity.valid) {
                    document.getElementById('myPOST_inventor_inventor_description').style.borderColor = "red";
                    return;
                } else {
                    document.getElementById('myPOST_inventor_inventor_description').style.borderColor = "grey";
                }
                if(!document.getElementById('myPOST_inventor_invention_description').validity.valid) {
                    document.getElementById('myPOST_inventor_invention_description').style.borderColor = "red";
                    return;
                } else {
                    document.getElementById('myPOST_inventor_invention_description').style.borderColor = "grey";
                }
                if(!document.getElementById('myPOST_inventor_invention_date').validity.valid) {
                    document.getElementById('myPOST_inventor_invention_date').style.borderColor = "red";
                    return;
                } else {
                    document.getElementById('myPOST_inventor_invention_date').style.borderColor = "grey";
                }
                let bd = new Date(document.querySelector("#myPOST_inventor_birth_date").value);
                let inventor_birth_date = bd.getDate() + "." + (bd.getMonth() + 1) + "." + bd.getFullYear()
                let id = new Date(document.querySelector("#myPOST_inventor_death_date").value);
                let inventor_death_date = id.getDate() + "." + (id.getMonth() + 1) + "." + id.getFullYear()
                if(inventor_death_date === "NaN.NaN.NaN") {
                    inventor_death_date = "";
                }
                let iid = new Date(document.querySelector("#myPOST_inventor_invention_date").value);
                let invention_date = iid.getDate() + "." + (iid.getMonth() + 1) + "." + iid.getFullYear()
                inputModal.hide();
                $.ajax({
                    type: 'POST',
                    url: '...',
                    data: '{"name":"'+document.querySelector("#myPOST_inventor_name").value+'",' +
                        '"surname":"'+document.querySelector("#myPOST_inventor_surname").value+'",' +
                        '"birth_date":"'+inventor_birth_date+'",' +
                        '"birth_place":"'+document.querySelector("#myPOST_inventor_birth_place").value+'",' +
                        '"inventor_description":"'+document.querySelector("#myPOST_inventor_inventor_description").value+'",' +
                        '"death_date":"'+inventor_death_date+'",' +
                        '"death_place":"'+document.querySelector("#myPOST_inventor_death_place").value+'",' +
                        '"invention_date":"'+invention_date+'",' +
                        '"invention_description":"'+document.querySelector("#myPOST_inventor_invention_description").value+'"}',
                    dataType: 'json',
                    success: function(res) {
                        resultModalHeader.innerHTML = "Result";
                        resultModalBody.innerHTML = JSON.stringify(res, null, "   ");
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        resultModalHeader.innerHTML = "Error";
                        resultModalBody.innerHTML = JSON.stringify(XMLHttpRequest, null, "    ");
                    }
                });
                resultModal.show();
            });
            function onlySpaces(str) {
                return str.trim().length === 0;
            }
            $("#myPOST_invention").click(function(){
                if(!document.getElementById('myPOST_invention_inventor_id').validity.valid) {
                    document.getElementById('myPOST_invention_inventor_id').style.borderColor = "red";
                    return;
                } else {
                    document.getElementById('myPOST_invention_inventor_id').style.borderColor = "grey";
                }
                if(!document.getElementById('myPOST_invention_invention_date').validity.valid) {
                    document.getElementById('myPOST_invention_invention_date').style.borderColor = "red";
                    return;
                } else {
                    document.getElementById('myPOST_invention_invention_date').style.borderColor = "grey";
                }
                if(!document.getElementById('myPOST_invention_invention_description').validity.valid || onlySpaces(document.getElementById('myPOST_invention_invention_description').value)) {
                    document.getElementById('myPOST_invention_invention_description').style.borderColor = "red";
                    return;
                } else {
                    document.getElementById('myPOST_invention_invention_description').style.borderColor = "grey";
                }
                let id = new Date(document.querySelector("#myPOST_invention_invention_date").value);
                let invention_date = id.getDate() + "." + (id.getMonth() + 1) + "." + id.getFullYear()
                smallInputModal.hide();
                $.ajax({
                    type: 'POST',
                    url: '...',
                    data: '{"inventor_id":"'+document.querySelector("#myPOST_invention_inventor_id").value+'",' +
                        '"date":"'+invention_date+'",' +
                        '"description":"'+document.querySelector("#myPOST_invention_invention_description").value+'"}',
                    dataType: 'json',
                    success: function(res) {
                        resultModalHeader.innerHTML = "Result";
                        resultModalBody.innerHTML = JSON.stringify(res, null, "   ");
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        resultModalHeader.innerHTML = "Error";
                        resultModalBody.innerHTML = JSON.stringify(XMLHttpRequest, null, "    ");
                    }
                });
                resultModal.show();
            });
            $("#myDELETE_inventor").click(function(){
                $.ajax({
                    type: 'DELETE',
                    url: '...'+deleteInventorById.value,
                    success: function() {
                        resultModalHeader.innerHTML = "Result";
                        resultModalBody.innerHTML = "done";
                }});
            });
            $("#myPUT_inventor").click(function(){
                updateModal.hide();
                $.ajax({
                    type: 'PUT',
                    url: '...',
                    data: '{"id":"'+document.querySelector("#myPUT_inventor_id").value+'",' +
                        '"name":"'+document.querySelector("#myPUT_inventor_name").value+'",' +
                        '"surname":"'+document.querySelector("#myPUT_inventor_surname").value+'",' +
                        '"birth_date":"'+document.querySelector("#myPUT_inventor_birth_date").value+'",' +
                        '"birth_place":"'+document.querySelector("#myPUT_inventor_birth_place").value+'",' +
                        '"inventor_description":"'+document.querySelector("#myPUT_inventor_inventor_description").value+'",' +
                        '"death_date":"'+document.querySelector("#myPUT_inventor_death_date").value+'",' +
                        '"death_place":"'+document.querySelector("#myPUT_inventor_death_place").value+'"}',
                    dataType: 'json',
                    success: function(res) {
                        resultModalHeader.innerHTML = "Result";
                        resultModalBody.innerHTML = JSON.stringify(res, null, "   ");
                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        resultModalHeader.innerHTML = "Error";
                        resultModalBody.innerHTML = JSON.stringify(XMLHttpRequest, null, "    ");
                    }
                });
                resultModal.show();
            });
        });
    </script>
</head>
<body>
<div class="modal fade" id="resultModal" tabindex="-1" aria-labelledby="resultModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="resultModalLabel"></h5>
            </div>
            <div class="modal-body" id="resultModalBody"><pre id="resultModalBodyPre"></pre></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="inputModal" tabindex="-1" aria-labelledby="inputModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body" id="inputModalBody">
                <label for="myPOST_inventor_name">Inventor name</label>
                <input type="text" id="myPOST_inventor_name" required><br>
                <label for="myPOST_inventor_surname">Inventor surname</label>
                <input type="text" id="myPOST_inventor_surname" required><br>
                <label for="myPOST_inventor_birth_date">Inventor birth date</label>
                <input type="date" id="myPOST_inventor_birth_date" required><br>
                <label for="myPOST_inventor_birth_place">Inventor birth place</label>
                <input type="text" id="myPOST_inventor_birth_place" required><br>
                <label for="myPOST_inventor_inventor_description">Inventor description</label>
                <input type="text" id="myPOST_inventor_inventor_description" required><br>
                <label for="myPOST_inventor_death_date">Inventor death date (optional)</label>
                <input type="date" id="myPOST_inventor_death_date"><br>
                <label for="myPOST_inventor_death_place">Inventor death place (optional)</label>
                <input type="text" id="myPOST_inventor_death_place"><br>
                <label for="myPOST_inventor_invention_description">Inventors invention description</label>
                <input type="text" id="myPOST_inventor_invention_description" required><br>
                <label for="myPOST_inventor_invention_date">Inventors invention date</label>
                <input type="date" id="myPOST_inventor_invention_date" required><br>
                <button type="button" id="myPOST_inventor" class="btn btn-primary">POST</button><br><br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body" id="updateModalBody">
                <h3 style="margin-bottom: 2vh;">Leave empty to preserve original information</h3>
                <label for="myPUT_inventor_id">Inventor ID</label>
                <input type="text" id="myPUT_inventor_id" required><br>
                <label for="myPUT_inventor_name">Name</label>
                <input type="text" id="myPUT_inventor_name" required><br>
                <label for="myPUT_inventor_surname">Surname</label>
                <input type="text" id="myPUT_inventor_surname" required><br>
                <label for="myPUT_inventor_birth_date">Birth date</label>
                <input type="date" id="myPUT_inventor_birth_date" required><br>
                <label for="myPUT_inventor_birth_place">Birth place</label>
                <input type="text" id="myPUT_inventor_birth_place" required><br>
                <label for="myPUT_inventor_inventor_description">Description</label>
                <input type="text" id="myPUT_inventor_inventor_description" required><br>
                <label for="myPUT_inventor_death_date">Death date</label>
                <input type="date" id="myPUT_inventor_death_date"><br>
                <label for="myPUT_inventor_death_place">Death place</label>
                <input type="text" id="myPUT_inventor_death_place"><br>
                <button type="button" id="myPUT_inventor" class="btn btn-warning">PUT</button><br><br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="smallInputModal" tabindex="-1" aria-labelledby="smallInputModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body" id="smallInputModalBody">
                <label for="myPOST_invention_inventor_id">Inventor ID</label>
                <input type="text" id="myPOST_invention_inventor_id" required><br>
                <label for="myPOST_invention_invention_date">Invention date</label>
                <input type="date" id="myPOST_invention_invention_date" required><br>
                <label for="myPOST_invention_invention_description">Invention description</label>
                <input type="text" id="myPOST_invention_invention_description" required><br>
                <button type="button" id="myPOST_invention" class="btn btn-primary">POST</button><br><br>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


<div class="container" style="text-align: center">
    <div class="row" style="margin-bottom: 5vh!important; margin-top: 5vh;">
        <div class="col">
            <a href="docs.php" target="_blank" style="font-size: 150%" class="btn btn-info">API Docs</a>
        </div>
    </div>
    <div class="row" style="margin-top: 10vh;">
        <div class="col">
            <label for="myGET_ID">By Inventor ID</label>
            <input type="number" id="myGET_ID" value="1">
            <button id="myGET_inventor" class="btn btn-primary">GET</button>
        </div>
        <div class="col">
            <label for="myGET_surname">By Inventor surname</label>
            <input type="text" id="myGET_surname">
            <button id="myGET_inventor_surname" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#resultModal">GET</button>
        </div>
        <div class="col">
            <label for="myDELETE_ID">By Inventor ID</label>
            <input type="number" id="myDELETE_ID" value="1">
            <button id="myDELETE_inventor" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#resultModal">DELETE</button>
        </div>
        <div class="col">
            <button id="openInputPOST" type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#inputModal">POST inventor</button>
        </div>
        <div class="col">
            <button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#updateModal">PUT inventor</button>
        </div>
        <div class="col">
            <button id="myGET_inventors" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#resultModal">GET all inventors</button>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <label for="myGET_inventions_century">Inventions by century</label>
            <input type="number" id="myGET_inventions_century" value="18">
            <button id="myGET_inventions" class="btn btn-primary">GET</button>
        </div>
        <div class="col">
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#smallInputModal">POST invention</button>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <label for="myGET_events_year">Events by year</label>
            <input type="number" id="myGET_events_year" value="1915">
            <button id="myGET_events" class="btn btn-primary">GET</button>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>

