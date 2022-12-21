<?php

/**
 * @var string $servername
 * @var string $dbname
 * @var string $username
 * @var string $password
 */
require_once "config.php";
require_once 'vendor/autoload.php';

$client = new Google\Client();
$client->setAuthConfig('...');

if (isset($_GET['code'])) {
    $token = $client->fetchAccessTokenWithAuthCode($_GET['code']);

    $client->setAccessToken($token['access_token']);

    $google_oauth = new Google_Service_Oauth2($client);
    $google_account_info = $google_oauth->userinfo->get();
    $email = $google_account_info->email;
    $name = $google_account_info->name;
    $id = $google_account_info->getId();

    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $stmt = $stmt = $conn->prepare("SELECT * FROM `users` u WHERE u.email = :email");
        $stmt->bindParam(":email", $email);
        $stmt->execute();
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $user = $stmt->fetch();

        if($user == false)
        {
            $stmt = $conn->prepare("INSERT INTO users (name, email) VALUES (:name, :email)");
            $stmt->bindParam(":name", $name);
            $stmt->bindParam(":email", $email);
            $stmt->execute();
            $user_id = $conn->lastInsertId();
        } else {
            $user_id = $user['id'];
        }

        $stmt = $stmt = $conn->prepare("SELECT * FROM `accounts` a WHERE a.google_id = :google_id");
        $stmt->bindParam(":google_id", $id);
        $stmt->execute();
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $account = $stmt->fetch();

        if($account == null)
        {
            $stmt = $conn->prepare("INSERT INTO accounts (user_id, type, google_id) VALUES (".$user_id.", 'google', '".$id."')");
            $stmt->execute();
            $account_id = $conn->lastInsertId();
        } else {
            $account_id = $account['id'];
        }

        $stmt = $conn->prepare("INSERT INTO logins (account_id) VALUES (".$account_id.")");
        $stmt->execute();

        session_start();
        $_SESSION['name'] = $name;
        $query = array(
            'login' => $name,
            'account_id' => $account_id,
            'type' => 'google'
        );
        $query = http_build_query($query);
        header("Location: dashboard.php?" . $query);
    } catch (PDOException $e) {
        echo "<br><h2 style='text-align: center; color: red;'>" . $e->getMessage() ."</h2>" . "<br>" . "<br>";
    }
}