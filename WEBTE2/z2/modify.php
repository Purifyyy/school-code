<?php
header('Location: admin.php');
/**
 * @var string $servername
 * @var string $dbname
 * @var string $username
 * @var string $password
 */
require_once "config.php";

    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $stmt = $conn->prepare(
            "UPDATE translations 
                    SET description=:skDescription 
                    WHERE word_id =:wordId AND
                    language_id = 1;"
        );
        $stmt->bindParam(':wordId', $_POST["modif_slovo_id"], PDO::PARAM_INT);
        $stmt->bindParam(':skDescription', $_POST["modif_sk_vysvetlenie"]);
        $stmt->execute();

        $stmt = $conn->prepare(
            "UPDATE translations 
                    SET description=:enDescription 
                    WHERE word_id =:wordId AND
                    language_id = 3;"
        );
        $stmt->bindParam(':wordId', $_POST["modif_slovo_id"], PDO::PARAM_INT);
        $stmt->bindParam(':enDescription', $_POST["modif_en_vysvetlenie"]);
        $stmt->execute();

        $result = ["modified" => true, "message" => "Modified successfully"];
        echo json_encode($result);

    } catch (PDOException $e) {
        $result = ["modified" => false, "message" => "Error: " . $e->getMessage()];
        echo json_encode($result);
    }
    $conn = null;

