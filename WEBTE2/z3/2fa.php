<?php

session_start();
$_SESSION['name'] = $_GET['session'];

$query = array(
    'account_id' => $_GET['account_id'],
    'type' => 'classic'
);
$query = http_build_query($query);
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
    <title>Save your QR code</title>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col">
            <h1>Two-Factor Authentication</h1>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h3>Naskenuj si QR kód do aplikácie Google Authenticator,
            <br>pri prihlásení budeš v rámci 2FA údaj z aplikácie potrebovať</h3>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <img src="<?php echo $_GET['qr']; ?>" alt="qrcode">
        </div>
    </div>
    <div class="row">
        <div class="col">
            <button class="btn btn-success" type="button" onclick="location.href='<?php echo "dashboard.php?".$query; ?>'">Pokračuj do dashboardu</button>
        </div>
    </div>
    </div>
</div>



<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>

