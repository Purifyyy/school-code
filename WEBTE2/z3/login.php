<?php

/**
 * @var string $servername
 * @var string $dbname
 * @var string $username
 * @var string $password
 */
require_once "config.php";
require_once 'PHPGangsta/GoogleAuthenticator.php';

if(isset($_POST['email']))
{
    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $stmt = $conn->prepare("SELECT name, password, user_id, 2fa_id, accounts.id AS account
                                FROM users JOIN accounts ON users.id = accounts.user_id WHERE
                                users.email = :email AND `type` = 'classic'");
        $stmt->bindParam(":email", $_POST['email']);
        $stmt->execute();
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $user = $stmt->fetch();

        if(password_verify($_POST['password'], $user['password']))
        {
            $ga = new PHPGangsta_GoogleAuthenticator();
            $checkResult = $ga->verifyCode($user['2fa_id'], $_POST['auth'], 2);
            if ($checkResult) {
                $stmt = $conn->prepare("INSERT INTO logins (account_id) VALUES (".$user["account"].")");
                $stmt->execute();
                session_start();
                $_SESSION['name'] = $user['name'];
                $query = array(
                    'login' => $user['name'],
                    'account_id' => $user["account"],
                    'type' => 'classic'
                );
                $query = http_build_query($query);
                header("Location: dashboard.php?" . $query);
            }
        }
    } catch (PDOException $e) {
        echo "<br><h2 style='text-align: center; color: red;'>" . $e->getMessage() ."</h2>" . "<br>" . "<br>";
    }

    $conn = null;
}

?>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <link rel="stylesheet" href="styles.css">
    <title>Login Failed</title>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col">
            <h3>Zadané údaje sú nesprávne!</h3>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <a class="btn btn-secondary" href='index.php'>Skúsiť znova</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>
