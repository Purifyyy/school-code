<?php

/**
 * @var string $servername
 * @var string $dbname
 * @var string $username
 * @var string $password
 */
require_once "config.php";
require_once 'PHPGangsta/GoogleAuthenticator.php';

if(isset($_POST['name']))
{
    try {
        $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
        $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

        $stmt = $conn->prepare("SELECT * FROM `users` u WHERE u.email = :email");
        $stmt->bindParam(":email", $_POST['email']);
        $stmt->execute();
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $user = $stmt->fetch();

        if($user == false)
        {
            $stmt = $conn->prepare("INSERT INTO users (name, email) VALUES (:name, :email)");
            $stmt->bindParam(":name", $_POST['name']);
            $stmt->bindParam(":email", $_POST['email']);
            $stmt->execute();
            $user_id = $conn->lastInsertId();
        } else {
            $user_id = $user['id'];
        }

        $stmt = $conn->prepare("SELECT * FROM `accounts` a WHERE a.user_id = :id AND a.type = 'classic'");
        $stmt->bindParam(":id", $user_id);
        $stmt->execute();
        $stmt->setFetchMode(PDO::FETCH_ASSOC);
        $account = $stmt->fetch();

        if($account == null)
        {
            $ga = new PHPGangsta_GoogleAuthenticator();
            $secret = $ga->createSecret();

            $stmt = $conn->prepare("INSERT INTO accounts (user_id, type, password, 2fa_id) VALUES (" . $user_id . ", 'classic', :password, :secret)");
            $passwordHash = password_hash($_POST['password'], PASSWORD_BCRYPT);
            $stmt->bindParam(":password", $passwordHash);
            $stmt->bindParam(":secret", $secret);
            $stmt->execute();

            $account_id = $conn->lastInsertId();
            $stmt = $conn->prepare("INSERT INTO logins (account_id) VALUES (".$account_id.")");
            $stmt->execute();

            $qrCodeUrl = $ga->getQRCodeGoogleUrl('WEBTE2-Login', $secret);

            $query = array(
                'qr' => $qrCodeUrl,
                'session' => $_POST['name'],
                'account_id' => $account_id
            );
            $query = http_build_query($query);
            header("Location: 2fa.php?" . $query);
//            session_start();
//            $_SESSION['name'] = $_POST['name'];
//            header("Location: dashboard.php");
        } else {
            echo "<br><h2 style='text-align: center; color: red;'>Účet s týmto emailom už existuje!</h2>" . "<br>" . "<br>";
        }
    } catch (PDOException $e) {
        echo "<br><h2 style='text-align: center; color: red;'>" . $e->getMessage() ."</h2>" . "<br>" . "<br>";
    }
}
$conn = null;
?>

<!DOCTYPE html>
<html lang="sk">
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
    <title>Register system</title>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col">
            <h1>Register</h1>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <form action="register.php" method="post">
                <div class="row mb-3">
                    <label for="name" class="col-sm-2 col-form-label">Name</label>
                    <div class="col-sm-10">
                        <input id="name" name="name" class="form-control" type="text" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <label for="email" class="col-sm-2 col-form-label">Email</label>
                    <div class="col-sm-10">
                        <input id="email" name="email" class="form-control" type="email" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <label for="password" class="col-sm-2 col-form-label">Password</label>
                    <div class="col-sm-10">
                        <input id="password" name="password" class="form-control" type="password" required>
                    </div>
                </div>
                <div class="row" style="text-align: right">
                    <div class="col">
                        <input type="submit" class="btn btn-primary" value="Register">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h5>Už účet máš? <a class="btn btn-primary" href="index.php">Prihlás sa</a></h5>
        </div>
    </div>
</div>

<!--<form action="register.php" method="post">-->
<!--    <div>-->
<!--        <label for="name">Name</label>-->
<!--        <input id="name" name="name" type="text" required>-->
<!--    </div>-->
<!--    <div>-->
<!--        <label for="email">Email</label>-->
<!--        <input id="email" name="email" type="email" required>-->
<!--    </div>-->
<!--    <div>-->
<!--        <label for="password">Password</label>-->
<!--        <input id="password" name="password" type="password" required>-->
<!--    </div>-->
<!--    <input type="submit" value="Register">-->
<!--</form>-->

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>

