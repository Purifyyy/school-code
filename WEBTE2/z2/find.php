<?php

/**
 * @var string $servername
 * @var string $dbname
 * @var string $username
 * @var string $password
 */
require_once "config.php";

$languages = [];
$result = null;

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $stmt = $conn->prepare("SELECT * FROM languages");
    $stmt->execute();

    $stmt->setFetchMode(PDO::FETCH_ASSOC);
    $languages = $stmt->fetchAll();
    }
catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
    }
$conn = null;
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link rel="stylesheet" href="styles.css">
    <title>Glosar User</title>
</head>
<body>
<h1 class="h1">Glosar - USER</h1>
<hr style="visibility: hidden">
<form id="search_form" class="smallerForm">
    <div class="form-group">
        <label for="language">Jazyk:</label>
        <select class="form-control" name="language_id" id="language">
            <?php
            foreach ($languages as $language) {
                echo "<option value='".$language['id']."'>".$language['title']."</option>";
            }
            ?>
        </select>
    </div>
    <div class="form-group">
        <label for="search">Hľadané slovo:</label>
        <input type="text" class="form-control" name="search" id="search">
    </div>
    <div class="form-group">
        <label for="search_type">Typ vyhľadávania:</label>
        <select name="search_type_return" class="form-control" id="search_type">
            <option value="1">Obyčajné</option>
            <option value="2">Fulltext (3+ znaky)</option>
        </select>
    </div>
    <div class="form-group">
        <button id="search-button" class="btn btn-primary btn-dark" type="button">Vyhľadaj</button>
    </div>
</form>

<hr style="visibility: hidden">

<table id="result-table" class="table table-striped table-hover table-bordered">
    <thead>
    </thead>
    <tbody>
    </tbody>
</table>

<div class="modal fade" id="explainModal" tabindex="-1" aria-labelledby="explainModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="explainModalLabel">Vysvetlenie pojmu</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="centerDiv">
                    <span id="ex_term"></span>
                </div>
                <div class="centerDiv">
                    <label for="ex_term_explanation" class="centeredLabel">Vysvetlenie: </label>
                    <textarea id="ex_term_explanation" name="ex_term_explanation" readonly rows="5" cols="50"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="explainTranslateModal" tabindex="-1" aria-labelledby="explainTranslateModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="explainTranslateModalLabel">Vysvetlenie a preklad pojmu</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="centerDiv">
                    <label for="ex_tr_term_explanation" class="centeredLabel">Vysvetlenie pojmu: </label>
                    <span id="ex_tr_term"></span>
                    <textarea id="ex_tr_term_explanation" name="ex_tr_term_explanation" readonly rows="5" cols="50"></textarea>
                </div>
                <hr>
                <div class="centerDiv">
                    <label for="ex_tr_term_t_translation" class="centeredLabel">Preklad pojmu: </label>
                    <span id="ex_tr_term_t"></span>
                    <textarea id="ex_tr_term_t_translation" name="ex_tr_term_t_translation" readonly rows="5" cols="50"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script>
    const form = document.querySelector('#search_form');
    const button = document.querySelector("#search-button");
    const table = document.querySelector("#result-table");
    const thead = document.querySelector("#result-table").tHead;
    const tbody = document.querySelector("#result-table").tBodies[0];
    const searchMethod = document.querySelector("#search_type");
    const langSelect = document.querySelector("#language");
    const searchedWord = document.querySelector("#search");

    searchMethod.addEventListener('change', () => {
        if(searchMethod.value === "1") {
            button.style.visibility = "visible"
        } else {
            button.style.visibility = "hidden"
        }
    })

    button.addEventListener('click', () => {
        printResults()
    })

    searchedWord.addEventListener('input', () => {
        if(searchedWord.value.length >= 3 && searchMethod.value !== "1") {
            printResults()
        } else {
            thead.innerHTML = ''
            tbody.innerHTML = ''
        }
    })

    function printResults() {
        const data = new FormData(form);
        let searchString;
        if(searchMethod.value === "1") {
            searchString = "translate.php?search="
        } else {
            searchString = "translateFull.php?search="
        }
        fetch(searchString+data.get('search')+"&language_id="+data.get('language_id'),
            {method: "get"}
        )
        .then(response => response.json())
        .then(result => {
            thead.innerHTML = ''
            tbody.innerHTML = ''
            if(result.length > 0) {
                const trTop = document.createElement("tr");
                const thLang = document.createElement("th");
                if(langSelect.value === '1') {
                    thLang.append("<?php echo $languages[0]['title'] ?>");
                } else {
                    thLang.append("<?php echo $languages[1]['title'] ?>");
                }
                const thExplain = document.createElement("th");
                thExplain.colSpan = 2;
                trTop.append(thLang);
                trTop.append(thExplain);
                thead.append(trTop);
                result.forEach(item => {
                    const tr = document.createElement("tr");
                    const td = document.createElement("td");
                    td.style.verticalAlign = "middle";
                    const td2 = document.createElement("td");
                    const td3 = document.createElement("td");
                    const buttonExplain = document.createElement("button");
                    buttonExplain.append("Vysvetli");
                    buttonExplain.setAttribute("type", "button");
                    buttonExplain.setAttribute("class", "btn btn-primary");
                    buttonExplain.setAttribute("data-bs-toggle", "modal");
                    buttonExplain.setAttribute("data-bs-target", "#explainModal");
                    buttonExplain.addEventListener('click', () => {
                        const term = document.querySelector("#ex_term");
                        const termExplanation = document.querySelector("#ex_term_explanation");
                        term.innerHTML = item.searchTitle;
                        termExplanation.value = item.searchDesc;
                    })
                    const buttonExplainTranslate = document.createElement("button");
                    buttonExplainTranslate.append("Vysvetli a Prelož");
                    buttonExplainTranslate.setAttribute("type", "button");
                    buttonExplainTranslate.setAttribute("class", "btn btn-primary");
                    buttonExplainTranslate.setAttribute("data-bs-toggle", "modal");
                    buttonExplainTranslate.setAttribute("data-bs-target", "#explainTranslateModal");
                    buttonExplainTranslate.addEventListener('click', () => {
                        const term = document.querySelector("#ex_tr_term");
                        const termExplanation = document.querySelector("#ex_tr_term_explanation");
                        const termTranslated = document.querySelector("#ex_tr_term_t");
                        const termExplanationTranslated = document.querySelector("#ex_tr_term_t_translation");
                        term.innerHTML = item.searchTitle;
                        termExplanation.value = item.searchDesc;
                        termTranslated.innerHTML = item.resultTitle;
                        termExplanationTranslated.value = item.resultDesc;
                    })
                    td.append(item.searchTitle);
                    td2.append(buttonExplain);
                    td3.append(buttonExplainTranslate);
                    tr.append(td);
                    tr.append(td2);
                    tr.append(td3);
                    tbody.append(tr);
                })
            }
        })
    }
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>

