<?php
require_once "../Inventor.php";
require_once "../Invention.php";
header('Content-Type: application/json; charset=utf-8');

switch ($_SERVER['REQUEST_METHOD']) {
    case "POST":
        header("HTTP/1.1 409 Conflict");
        $data = json_decode(file_get_contents('php://input'), true);
        if(!(Inventor::findBySurname($data['surname']))) {
            header("HTTP/1.1 201 Created");
            $inventor = new Inventor();
            $inventor->setName($data['name']);
            $inventor->setSurname($data['surname']);
            $inventor->setBirthDate(trim($data['birth_date']));
            $inventor->setBirthPlace($data['birth_place']);
            $inventor->setDescription($data['inventor_description']);
            if (!(ctype_space($data['death_date']) || $data['death_date'] == '')) {
                $inventor->setDeathDate(trim($data['death_date']));
            }
            if (!(ctype_space($data['death_place']) || $data['death_place'] == '')) {
                $inventor->setDeathPlace($data['death_place']);
            }
            $inventor->save();

            $invention = new Invention();
            $invention->setDescription($data['invention_description']);
            $invention->setInventionDate($data['invention_date']);
            $invention->setInventorId($inventor->getId());
            $invention->save();
            echo json_encode(Inventor::findByIdWithInventios($inventor->getId()), JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        }
        break;
    case "PUT":
        header("HTTP/1.1 404 Not Found");
        $data = json_decode(file_get_contents('php://input'), true);
        $inventor = Inventor::findById($data['id']);
        if($inventor)
        {
            $toUpdateInventor = new Inventor();
            $inventor = json_decode(json_encode($inventor), TRUE);
            header("HTTP/1.1 200 OK");
            if (!(ctype_space($data['name']) || $data['name'] == '')) {
                $toUpdateInventor->setName($data['name']);
            } else {
                $toUpdateInventor->setName($inventor['name']);
            }

            if (!(ctype_space($data['surname']) || $data['surname'] == '')) {
                $toUpdateInventor->setSurname($data['surname']);
            } else {
                $toUpdateInventor->setSurname($inventor['surname']);
            }

            if (!(ctype_space($data['birth_date']) || $data['birth_date'] == '')) {
                $date = DateTime::createFromFormat('Y-m-d', $data['birth_date']);
            } else {
                $date = DateTime::createFromFormat('Y-m-d', $inventor['birth_date']);
            }
            $toUpdateInventor->setBirthDate($date->format('d.m.Y'));

            if (!(ctype_space($data['birth_place']) || $data['birth_place'] == '')) {
                $toUpdateInventor->setBirthPlace($data['birth_place']);
            } else {
                $toUpdateInventor->setBirthPlace($inventor['birth_place']);
            }

            if (!(ctype_space($data['death_date']) || $data['death_date'] == '')) {
                $date = DateTime::createFromFormat('Y-m-d', $data['death_date']);
            } else {
                $date = DateTime::createFromFormat('Y-m-d', $inventor['death_date']);
            }
            $toUpdateInventor->setDeathDate($date->format('d.m.Y'));

            if (!(ctype_space($data['death_place']) || $data['death_place'] == '')) {
                $toUpdateInventor->setDeathPlace($data['death_place']);
            } else {
                $toUpdateInventor->setDeathPlace($inventor['death_place']);
            }

            if (!(ctype_space($data['description']) || $data['description'] == '')) {
                $toUpdateInventor->setDescription($data['description']);
            } else {
                $toUpdateInventor->setDescription($inventor['description']);
            }
            $toUpdateInventor->update($inventor['id']);
            echo json_encode($toUpdateInventor->toArray());
        }
        break;
    case "DELETE":
        $id = $_GET['id'];
        Inventor::destroy($id);
        header("HTTP/1.1 204 No Content");
        break;
    case "GET":
        header("HTTP/1.1 200 OK");
        $id = $_GET['id'];
        if($id) {
            header("HTTP/1.1 404 Not Found");
            $inventor = Inventor::findByIdWithInventios($id);
            if($inventor) {
                header("HTTP/1.1 200 OK");
                echo json_encode($inventor, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
            }
        } else {
            echo json_encode(Inventor::all(), JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
        }
        break;
}

