<?php
header('Location: admin.php');
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

$myPdo = new MyPDO("mysql:host=".$servername.";dbname=".$dbname, $username, $password);

$word = new Word($myPdo);
$word->setTitle($_POST["en_pojem"]);
$word->save();

$englishTranslation = new Translation($myPdo);
$englishTranslation->setTitle($_POST["en_pojem"]);
$englishTranslation->setDescription($_POST["en_vysvetlenie"]);
$englishTranslation->setLanguageId(3);
$englishTranslation->setWordId($word->getId());
$englishTranslation->save();

$slovakTranslation = new Translation($myPdo);
$slovakTranslation->setTitle($_POST["sk_pojem"]);
$slovakTranslation->setDescription($_POST["sk_vysvetlenie"]);
$slovakTranslation->setLanguageId(1);
$slovakTranslation->setWordId($word->getId());
$slovakTranslation->save();
