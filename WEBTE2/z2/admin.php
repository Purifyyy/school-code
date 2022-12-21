<?php

/**
 * @var string $servername
 * @var string $dbname
 * @var string $username
 * @var string $password
 */
require_once "config.php";
require_once "MyPdo.php";
require_once "Word.php";
require_once "Translation.php";

$myPdo = new MyPDO("mysql:host=$servername;dbname=$dbname", $username, $password);

if (isset($_FILES['file']))
{
//    var_dump($_FILES);
    $file = fopen($_FILES['file']['tmp_name'], "r");
    while (!feof($file))
    {
        $pole = fgetcsv($file, null, ";");
        if($pole[0])
        {
            $word = new Word($myPdo);
            $word->setTitle($pole[0]);
            $word->save();

            $englishTranslation = new Translation($myPdo);
            $englishTranslation->setTitle($pole[0]);
            $englishTranslation->setDescription($pole[1]);
            $englishTranslation->setLanguageId(3);
            $englishTranslation->setWordId($word->getId());
            $englishTranslation->save();

            $slovakTranslation = new Translation($myPdo);
            $slovakTranslation->setTitle($pole[2]);
            $slovakTranslation->setDescription($pole[3]);
            $slovakTranslation->setLanguageId(1);
            $slovakTranslation->setWordId($word->getId());
            $slovakTranslation->save();
        }
    }
}

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
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link rel="stylesheet" href="styles.css">
    <title>Glosar Admin</title>
</head>
<body>
<p class="h1">Glosar - ADMIN</p>

<h3 class="h3">CSV file upload</h3>
<form action="index.php" method="post" class="smallerForm" enctype="multipart/form-data">

    <div class="form-group" style="margin-bottom: 1vh">
        <div><label for="file">Súbor:</label>
            <input class="form-control-file" type="file" id="file" name="file"></div>
    </div>
    <div class="form-group">
        <input type="submit" class="btn btn-primary btn-dark" value="Upload">
    </div>

</form>

<hr style="visibility: hidden;">

<h3 class="h3">Define new term</h3>
<form action="define.php" class="smallerForm" method="post">
    <div class="form-group">
        <label for="sk_pojem">Slovenský pojem:</label>
        <input type="text" id="sk_pojem" class="form-control" name="sk_pojem" required>
    </div>
    <div class="form-group">
        <label for="sk_vysvetlenie">Slovenské vysvetlenie:</label>
        <input type="text" id="sk_vysvetlenie" class="form-control" name="sk_vysvetlenie" required>
    </div>
    <div class="form-group">
        <label for="en_pojem">Anglický pojem:</label>
        <input type="text" id="en_pojem" class="form-control" name="en_pojem" required>
    </div>
    <div class="form-group">
        <label for="en_vysvetlenie">Anglické vysvetlenie:</label>
        <input type="text" id="en_vysvetlenie" class="form-control" name="en_vysvetlenie" required>
    </div>
    <div class="form-group">
        <input type="submit" class="btn btn-primary btn-dark" value="Define">
    </div>
</form>

<hr style="visibility: hidden;">

<h3 class="h3">Modify database</h3>
<form id="search_form" class="smallerForm">
    <div class="form-group">
        <label hidden for="language">Jazyk</label>
        <select hidden name="language_id" class="form-control" id="language">
            <?php
            foreach ($languages as $language) {
                echo "<option value='".$language['id']."'>".$language['title']."</option>";
            }
            ?>
        </select>
    </div>
    <div class="form-group">
        <label for="search">Pojem v databáze (alebo jeho časť):</label>
        <input type="text" class="form-control" name="search" id="search">
    </div>
    <div class="form-group">
        <button id="search-button" class="btn btn-primary btn-dark" type="button">Find</button>
    </div>
</form>

<table id="result-table" class="table table-striped table-hover table-bordered">
    <thead>
    </thead>
    <tbody>
    </tbody>
</table>

<div class="modal fade" id="modifyModal" tabindex="-1" aria-labelledby="modifyModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modifyModalLabel">Modifikácia záznamu</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="modify.php" method="post">
                    <div class="form-group">
                        <span  id="modif_sk_pojem"></span>
                    </div>
                    <div class="form-group">
                        <label for="modif_sk_vysvetlenie">Slovenské vysvetlenie:</label>
                        <textarea class="form-control" id="modif_sk_vysvetlenie" name="modif_sk_vysvetlenie" required rows="4" cols="50"></textarea>
                    </div>
                    <hr style="visibility: hidden">
                    <div class="form-group">
                        <span id="modif_en_pojem"></span>
                    </div class="form-group">
                    <div>
                        <label for="modif_en_vysvetlenie">Anglické vysvetlenie:</label>
                        <textarea class="form-control" id="modif_en_vysvetlenie" name="modif_en_vysvetlenie" required rows="4" cols="50"></textarea>
                    </div>
                    <div class="form-group">
                        <input type="hidden" class="form-control" id="modif_slovo_id" name="modif_slovo_id">
                    </div>
                    <div class="form-group">
                        <input id="mdlbtn" type="submit" class="btn btn-primary" value="Modifikuj">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>
    const searchBox = document.querySelector("#search")
    const form = document.querySelector('#search_form');
    const button = document.querySelector("#search-button");
    const table = document.querySelector("#result-table");
    const thead = document.querySelector("#result-table").tHead;
    const tbody = document.querySelector("#result-table").tBodies[0];

    button.addEventListener('click', () => {
        const data = new FormData(form);
        fetch("translate.php?search="+data.get('search')+"&language_id="+data.get('language_id'),
            {method: "get"}
        )
            .then(response => response.json())
            .then(result => {
                thead.innerHTML = ''
                tbody.innerHTML = ''
                if(result.length > 0) {
                    const trTop = document.createElement("tr");
                    const th1 = document.createElement("th");
                    th1.append("<?php echo $languages[0]['title'] ?>");
                    const th2 = document.createElement("th");
                    th2.append("<?php echo $languages[1]['title'] ?>");
                    const thDelete = document.createElement("th");
                    thDelete.colSpan = 2;
                    trTop.append(th1);
                    trTop.append(th2);
                    trTop.append(thDelete);
                    thead.append(trTop);
                    tbody.innerHTML = ''
                    result.forEach(item => {
                        const tr = document.createElement("tr");
                        const td1 = document.createElement("td");
                        const td2 = document.createElement("td");
                        const td3 = document.createElement("td");
                        const td4 = document.createElement("td");
                        const buttonDelete = document.createElement("button");
                        buttonDelete.setAttribute("class", "btn btn-primary");
                        buttonDelete.append("Vymaž");
                        buttonDelete.addEventListener("click", () => {
                            fetch("delete.php", {
                                method: "post",
                                body: JSON.stringify({id: item.word_id})
                            })
                                .then(response => response.json())
                                .then(result => {
                                    if (result.deleted) {
                                        tr.remove();
                                    }
                                })
                        })
                        const buttonModify = document.createElement("button");
                        buttonModify.setAttribute("type", "button");
                        buttonModify.setAttribute("class", "btn btn-primary");
                        buttonModify.setAttribute("data-bs-toggle", "modal");
                        buttonModify.setAttribute("data-bs-target", "#modifyModal");
                        buttonModify.append("Modifikuj");
                        buttonModify.addEventListener("click", () => {
                            const sk_term = document.querySelector("#modif_sk_pojem");
                            const sk_meaning = document.querySelector("#modif_sk_vysvetlenie");
                            const en_term = document.querySelector("#modif_en_pojem");
                            const en_meaning = document.querySelector("#modif_en_vysvetlenie");
                            const affected_word = document.querySelector("#modif_slovo_id");
                            sk_term.innerHTML = item.searchTitle;
                            sk_meaning.value = item.searchDesc;
                            en_term.innerHTML = item.resultTitle;
                            en_meaning.value = item.resultDesc;
                            affected_word.setAttribute("value", item.word_id)
                        })
                        td1.append(item.searchTitle);
                        td2.append(item.resultTitle);
                        td3.append(buttonDelete);
                        td4.append(buttonModify);
                        tr.append(td1);
                        tr.append(td2);
                        tr.append(td3);
                        tr.append(td4);
                        tbody.append(tr);
                    })
                }
            })
    })
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>

