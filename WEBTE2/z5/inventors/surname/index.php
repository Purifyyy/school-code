<?php
require_once "../../Inventor.php";
header('Content-Type: application/json; charset=utf-8');

if($_SERVER['REQUEST_METHOD'] === 'GET')
{
    $surname = $_GET['surname'];
    header("HTTP/1.1 404 Not Found");
    if($surname) {
        $inventor = Inventor::findBySurname($surname);
        if($inventor) {
            header("HTTP/1.1 200 OK");
            echo json_encode($inventor, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        }
    }
}