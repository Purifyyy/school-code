<?php
require_once "../Invention.php";
require_once "../Inventor.php";
header('Content-Type: application/json; charset=utf-8');

if($_SERVER['REQUEST_METHOD'] === 'GET')
{
    header("HTTP/1.1 404 Not Found");
    $century = $_GET['century'];
    if($century) {
        header("HTTP/1.1 200 OK");
        echo json_encode(Invention::findByCentury($century), JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
    }
} else if($_SERVER['REQUEST_METHOD'] === 'POST') {
    header("HTTP/1.1 404 Not Found");
    $data = json_decode(file_get_contents('php://input'), true);
    $inventor = Inventor::findByIdWithInventios($data['inventor_id']);
    if($inventor)
    {
        header("HTTP/1.1 400 Bad Request");
        if($data['date'] && $data['description'])
        {
            header("HTTP/1.1 201 CREATED");
            $invention= new Invention();
            $invention->setDescription($data['description']);
            $invention->setInventionDate($data['date']);
            $invention->setInventorId($inventor['id']);
            $invention->save();
            echo json_encode($invention->toArray());
        }
    }
}
