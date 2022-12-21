<?php
require_once 'vendor/autoload.php';
require_once 'PHPGangsta/GoogleAuthenticator.php';

$client = new Google\Client();
$client->setAuthConfig('...');
$redirect_uri = '...';
$client->addScope("email");
$client->addScope("profile");
$client->setRedirectUri($redirect_uri);

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
    <title>Login System</title>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col">
            <h1>Login</h1>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <form action="login.php" method="post">
                <div class="row mb-3">
                    <label for="email" class="col-sm-3 col-form-label">Email</label>
                    <div class="col-sm-9">
                        <input id="email" name="email" class="form-control" type="email" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <label for="password" class="col-sm-3 col-form-label">Password</label>
                    <div class="col-sm-9">
                        <input id="password" name="password" class="form-control" type="password" required>
                    </div>
                </div>
                <div class="row mb-3">
                    <label for="auth" class="col-sm-3 col-form-label">Authenticator code</label>
                    <div class="col-sm-9">
                        <input id="auth" name="auth" class="form-control" type="text" required>
                    </div>
                </div>
                <div class="row" style="text-align: right; margin-bottom: 2vh !important;">
                    <div class="col">
                        <input type="submit" class="btn btn-primary" value="Login">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <?php echo "<a class='btn btn-danger' href='".$client->createAuthUrl()."'>Login with Google account</a>"; ?>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h5>Nemáš ešte účet? <a class="btn btn-primary" href="register.php">Zaregistruj sa</a></h5>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>
