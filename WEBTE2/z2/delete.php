<?php
header('Content-Type: application/json; charset=utf-8');
$data = json_decode(file_get_contents('php://input'), true);
if(isset($data)) {
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
            "delete from words where id = :id"
        );
        $stmt->bindParam(':id', $data['id'], PDO::PARAM_INT);
        $stmt->execute();

        $result = ["deleted" => true, "message" => "Deleted successfully"];
        echo json_encode($result);

    } catch (PDOException $e) {
        $result = ["deleted" => false, "message" => "Error: " . $e->getMessage()];
        echo json_encode($result);
    }
    $conn = null;
}
