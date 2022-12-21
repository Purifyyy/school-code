<?php

require_once "../Invention.php";
require_once "../Inventor.php";
header('Content-Type: application/json; charset=utf-8');

if($_SERVER['REQUEST_METHOD'] === 'GET')
{
    header("HTTP/1.1 404 Not Found");
    $year = $_GET['year'];
    if($year) {
        header("HTTP/1.1 200 OK");
        $inventors_birth = Inventor::findByBirthYear($year);
        $inventors_death = Inventor::findByDeathYear($year);
        $inventions_year = Invention::findByYear($year);
        echo json_encode(array_merge($inventors_birth, $inventors_death, $inventions_year), JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
    }
}
