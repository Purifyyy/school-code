<?php

/**
 * @var string $servername
 * @var string $dbname
 * @var string $username
 * @var string $password
 */
require_once "config.php";

$result = null;

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    if(isset($_GET['search'])) {
        $search = "%".$_GET['search']."%";
        $stmt = $conn->prepare("SELECT 
                table1.title as searchTitle,
                table1.description as searchDesc,
                table2.title as resultTitle,
                table2.description as resultDesc,
                table1.word_id
                FROM translations table1 
                JOIN translations table2 
                    ON table1.word_id = table2.word_id
                WHERE 
                      ((table1.title LIKE :search_term) OR (table1.description LIKE :search_term)) AND
                      table1.id <> table2.id AND 
                      table1.language_id = :language_id");
        $stmt->bindParam(':language_id', $_GET['language_id'], PDO::PARAM_INT);
        $stmt->bindParam(':search_term', $search, PDO::PARAM_STR);
        $stmt->execute();
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $result = $stmt->fetchAll();

        echo json_encode($result);
    }
}
catch (PDOException $e) {
    echo "Error: " . $e->getMessage();
}
$conn = null;