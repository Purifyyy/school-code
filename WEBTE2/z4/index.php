<?php
// Read the JSON file
$json = file_get_contents('./storage/restaurant1.json');
$json2 = file_get_contents('./storage/restaurant2.json');
$json3 = file_get_contents('./storage/restaurant3.json');

$json_data = json_decode($json,true);
$json_data2 = json_decode($json2,true);
$json_data3 = json_decode($json3,true);

$foods = $json_data['data'];
$foods2 = $json_data2['data'];
$foods3 = $json_data3['data'];

$timeDifference = (new DateTime())->getTimestamp() - $json_data['timestamp'];

if($timeDifference > 800) {
    $ch = curl_init();

    curl_setopt($ch, CURLOPT_URL, "https://www.delikanti.sk/prevadzky/3-jedalen-prif-uk/");

    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

    $output = curl_exec($ch);

    curl_close($ch);

    $dom = new DOMDocument();

    @$dom->loadHTML($output);
    $dom->preserveWhiteSpace = false;
    $tables = $dom->getElementsByTagName('table');

    $rows = $tables->item(0)->getElementsByTagName('tr');
    $index = 0;
    $dayCount = 0;

    $foods = [];
    $foodCount = $rows->item(0)->getElementsByTagName('th')->item(0)->getAttribute('rowspan');

    foreach ($rows as $row) {

        if($row->getElementsByTagName('th')->item(0)){
            $foodCount = $row->getElementsByTagName('th')->item(0)->getAttribute('rowspan');

            $day = trim($rows->item($index)->getElementsByTagName('th')->item(0)->getElementsByTagName('strong')->item(0)->nodeValue);

            $th = $rows->item($index)->getElementsByTagName('th')->item(0);

            foreach($th->childNodes as $node)
                if(!($node instanceof \DomText))
                    $node->parentNode->removeChild($node);

            $date = trim($rows->item($index)->getElementsByTagName('th')->item(0)->nodeValue);


            array_push($foods, ["date" => $date, "day" => $day, "place" => "Delikanti", "menu" => []]);

            for($i = $index; $i <  $index + intval($foodCount); $i++)
            {
                if($foods[$dayCount])
                    array_push($foods[$dayCount]["menu"], trim($rows->item($i)->getElementsByTagName('td')->item(1)->nodeValue));
            }
            $index += intval($foodCount);
            $dayCount++;
        }

    }

    $data = ["timestamp" => (new DateTime())->getTimestamp(), "data" => $foods];

    $fp = fopen('./storage/restaurant1.json', 'w');
    fwrite($fp, json_encode($data));
    fclose($fp);


    $ch = curl_init();

    curl_setopt($ch, CURLOPT_URL, "http://eatandmeet.sk/tyzdenne-menu");

    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

    $output = curl_exec($ch);

    curl_close($ch);

    $dom = new DOMDocument();

    @$dom->loadHTML($output);
    $dom->preserveWhiteSpace = false;

    $parseNodes = ["day-1", "day-2", "day-3", "day-4", "day-5", "day-6", "day-7",];

    $jedla = [
        ["date" => date( 'd.m.Y', strtotime( 'monday this week' ) ), "day" => "Pondelok", "place" => "Eat&Meet", "menu" => []],
        ["date" => date( 'd.m.Y', strtotime( 'tuesday this week' ) ), "day" => "Utorok", "place" => "Eat&Meet", "menu" => []],
        ["date" => date( 'd.m.Y', strtotime( 'wednesday this week' ) ), "day" => "Streda", "place" => "Eat&Meet", "menu" => []],
        ["date" => date( 'd.m.Y', strtotime( 'thursday this week' ) ), "day" => "Štvrtok", "place" => "Eat&Meet", "menu" => []],
        ["date" => date( 'd.m.Y', strtotime( 'friday this week' ) ), "day" => "Piatok", "place" => "Eat&Meet", "menu" => []],
        ["date" => date( 'd.m.Y', strtotime( 'saturday this week' ) ), "day" => "Sobota", "place" => "Eat&Meet", "menu" => []],
        ["date" => date( 'd.m.Y', strtotime( 'sunday this week' ) ), "day" => "Nedeľa", "place" => "Eat&Meet", "menu" => []],
    ];

    foreach ($parseNodes as $index => $nodeId) {

        $node = $dom->getElementById($nodeId);

        foreach ($node->childNodes as $menuItem)
        {
            if($menuItem && $menuItem->childNodes->item(1) && $menuItem->childNodes->item(1)->childNodes->item(3)){
                $nazov = trim($menuItem->childNodes->item(1)->childNodes->item(3)->childNodes->item(1)->childNodes->item(1)->nodeValue);
                $cena = trim($menuItem->childNodes->item(1)->childNodes->item(3)->childNodes->item(1)->childNodes->item(3)->nodeValue);
                $popis = trim($menuItem->childNodes->item(1)->childNodes->item(3)->childNodes->item(3)->nodeValue);
                array_push($jedla[$index]["menu"], "$nazov ($popis): $cena");
            }
        }
    }

    $finder = new DomXPath($dom);
    $classname="mb-20";
    $nodes = $finder->query("//*[contains(concat(' ', normalize-space(@class), ' '), ' $classname ')]");

    $foodsWeekly = [];

    for ($i = 0; $i < count($nodes); $i++) {
        $node = $nodes[$i];
        for ($x = 1; $x < $node->childNodes->item(1)->childNodes->item(1)->childNodes->count(); $x = $x + 2) {
            $nazov = $node->childNodes->item(1)->childNodes->item(1)->childNodes->item($x)->childNodes->item(1)->childNodes->item(3)->childNodes->item(1)->childNodes->item(1)->nodeValue;
            $cena = $node->childNodes->item(1)->childNodes->item(1)->childNodes->item($x)->childNodes->item(1)->childNodes->item(3)->childNodes->item(1)->childNodes->item(3)->nodeValue;
            $foodsWeekly["menu"][] = "$nazov: $cena";
        }
    }

    for($i = 0; $i < count($jedla); $i++) {
        $jedla[$i]["menu"] = array_merge($jedla[$i]["menu"],$foodsWeekly["menu"]);
    }


    $data = ["timestamp" => (new DateTime())->getTimestamp(), "data" => $jedla];

    $fp = fopen('./storage/restaurant2.json', 'w');
    fwrite($fp, json_encode($data));
    fclose($fp);


    $ch = curl_init();

    curl_setopt($ch, CURLOPT_URL, "http://www.freefood.sk/menu/#fiit-food");

    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

    $output = curl_exec($ch);

    curl_close($ch);

    $dom = new DOMDocument();

    @$dom->loadHTML($output);
    $dom->preserveWhiteSpace = false;

    $base = $dom->getElementById('fiit-food')->childNodes->item(3)->childNodes->item(3);

    $food = [];

    foreach ($base->childNodes as $childNode) {
        if($childNode->childNodes->item(0)->nodeValue != null) {
            $pieces = explode(",", $childNode->childNodes->item(0)->nodeValue);
            $day = trim($pieces[0]);
            $date = trim($pieces[1]);
        }
        if($childNode->childNodes->item(1)->childNodes != null) {
            $arr = [];
            for ($i = 0; $i < $childNode->childNodes->item(1)->childNodes->length; $i++) {
                echo "<pre>";
                $arr[] = $childNode->childNodes->item(1)->childNodes->item($i)->childNodes->item(1)->nodeValue . " "
                    . $childNode->childNodes->item(1)->childNodes->item($i)->childNodes->item(2)->nodeValue;
                echo "</pre>";
            }
            $food[] = ["date" => $date, "day" => $day, "place" => "FIITFOOD", "menu" => $arr];
        }
    }

    if(count($food) == 5) {
        $food[] = ["date" => date('d.m.Y', strtotime('saturday this week')), "day" => "Sobota", "place" => "FIITFOOD", "menu" => []];
        $food[] = ["date" => date( 'd.m.Y', strtotime( 'sunday this week' )), "day" => "Nedeľa", "place" => "FIITFOOD", "menu" => []];
    }

    $data = ["timestamp" => (new DateTime())->getTimestamp(), "data" => $food];

    $fp = fopen('./storage/restaurant3.json', 'w');
    fwrite($fp, json_encode($data));
    fclose($fp);
}

?>

<html lang="sk">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link rel="stylesheet" href="styles.css">
    <title>Papanica</title>
</head>
<body>

<div class="modal fade" id="dayModal" tabindex="-1" aria-labelledby="dayModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="dayModalLabel">den v tyzdni</h5>
            </div>
            <div class="modal-body" id="dayModalBody"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<h1 style="margin-top: 5vh;">
    Žrádielko v okolí našej fakulty
</h1>

<div class="container" style="margin-top: 5vh;">
    <div class="row">
        <div class="col">
            <label id="selectLabel" class="d-flex justify-content-end" for="days"><h3>Vyber si deň v týždni</h3></label>
        </div>
        <div class="col">
            <select class="form-select form-select-lg mb-3" name="days" id="days">
                <option value="0">Pondelok <?php echo date('d.m.Y', strtotime('monday this week')); ?></option>
                <option value="1">Utorok <?php echo date('d.m.Y', strtotime('tuesday this week')); ?></option>
                <option value="2">Streda <?php echo date('d.m.Y', strtotime('wednesday this week')); ?></option>
                <option value="3">Štvrtok <?php echo date('d.m.Y', strtotime('thursday this week')); ?></option>
                <option value="4">Piatok <?php echo date('d.m.Y', strtotime('friday this week')); ?></option>
                <option value="5">Sobota <?php echo date('d.m.Y', strtotime('saturday this week')); ?></option>
                <option value="6">Nedeľa <?php echo date('d.m.Y', strtotime('sunday this week')); ?></option>
            </select>
        </div>
        <div class="col">
            <button type="button" id="showbtn" class="btn btn-info btn-lg" data-bs-toggle="modal" data-bs-target="#dayModal">
                Zobraz
            </button>
        </div>
    </div>

    <h1 style="margin-top: 12vh; margin-bottom: 5vh;">
        Rozpis celého týždňa
    </h1>

    <div class="row">
        <div class="col">
            <?php
            for($i = 0; $i < 7; $i++) {
                echo "<br>". "<h2>" .$foods2[$i]['date']. " " .$foods2[$i]['day']. "</h2>". "<br>";

                echo "<br>". "<h4>".$foods[$i]['place']."</h4>"."<br>";
                if(!empty($foods[$i]['menu'])) {
                    foreach ($foods[$i]['menu'] as $foodItem) {
                        echo $foodItem . "<br><br>";
                    }
                } else {
                    echo "Na tento deň nie je denné menu"."<br><br>";
                }

                echo "<br>". "<h4>". $foods2[$i]['place']."</h4>"."<br>";
                if(!empty($foods2[$i]['menu'])) {
                    foreach ($foods2[$i]['menu'] as $foodItem) {
                        echo $foodItem."<br><br>";
                    }
                } else {
                    echo "Na tento deň nie je denné menu"."<br><br>";
                }

                echo "<br>". "<h4>". $foods3[$i]['place']."</h4>"."<br>";
                if(!empty($foods3[$i]['menu'])) {
                    foreach ($foods3[$i]['menu'] as $foodItem) {
                        echo $foodItem."<br><br>";
                    }
                } else {
                    echo "Na tento deň nie je denné menu"."<br><br>";
                }
            }
            ?>
        </div>
    </div>
</div>

<script>
    const showButton = document.querySelector("#showbtn");
    const daySelect = document.querySelector("#days");
    let modalHead = document.querySelector("#dayModalLabel");
    let modalBody = document.querySelector("#dayModalBody");

    showButton.addEventListener('click', () => {
        modalHead.innerHTML  = daySelect.options[daySelect.selectedIndex].text;
        modalBody.innerHTML = '';
        const get = async (url) => {
            const res = await fetch(url)
            if (!res.ok) {
                throw new Error(`${res.status}: ${await res.text()}`)
            }
            return res.json()
        }
        const urls = ["./storage/restaurant1.json", "./storage/restaurant2.json", "./storage/restaurant3.json"]
        Promise.allSettled(urls.map(get)).then(responses => {
            const data = responses
                .filter(({ status }) => status === "fulfilled")
                .map(({ value }) => value)
            for(let index = 0; index < data.length; index++) {
                let placeName = document.createElement("h2");
                placeName.innerHTML = data[index]['data'][daySelect.value]['place'];
                modalBody.appendChild(placeName);
                modalBody.appendChild(document.createElement("br"));
                if(data[index]['data'][daySelect.value]['menu'].length > 0) {
                    for (const food of data[index]['data'][daySelect.value]['menu']) {
                        let foodItem = document.createElement("p");
                        foodItem.innerHTML = food;
                        modalBody.appendChild(foodItem);
                    }
                } else {
                    let noFood = document.createElement("p");
                    noFood.innerHTML = "Na tento deň nie je denné menu";
                    modalBody.appendChild(noFood);
                }
                if(!(index === data.length-1)) {
                    modalBody.appendChild(document.createElement("hr"));
                }
            }
        })
    })
</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>
