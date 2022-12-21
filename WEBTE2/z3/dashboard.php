<?php
session_start();
/**
 * @var string $servername
 * @var string $dbname
 * @var string $username
 * @var string $password
 */
require_once "config.php";

if(!isset($_SESSION['name']))
{
    header("Location: index.php");
}

$results = [];
$classic_count = 0;
$google_count = 0;

try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname", $username, $password);
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    $stmt = $stmt = $conn->prepare("SELECT created_at FROM `logins` l WHERE l.account_id = :id");
    $stmt->bindParam(":id", $_GET['account_id']);
    $stmt->execute();
    $stmt->setFetchMode(PDO::FETCH_ASSOC);
    $results = $stmt->fetchAll();

    $stmt = $stmt = $conn->prepare("SELECT COUNT(*) FROM accounts WHERE accounts.type = 'classic'");
    $stmt->execute();
    $stmt->setFetchMode(PDO::FETCH_ASSOC);
    $classic_count = $stmt->fetch();

    $stmt = $stmt = $conn->prepare("SELECT COUNT(*) FROM accounts WHERE accounts.type = 'google'");
    $stmt->execute();
    $stmt->setFetchMode(PDO::FETCH_ASSOC);
    $google_count = $stmt->fetch();
} catch (PDOException $e) {
    echo "<br><h2 style='text-align: center; color: red;'>" . $e->getMessage() ."</h2>" . "<br>" . "<br>";
}
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
    <title>Dashboard</title>
</head>
<body>

<div class="modal fade" id="loginsModal" tabindex="-1" aria-labelledby="loginsModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="loginsModalLabel">Minulé prihlásenia</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <h4 style="text-align: center; margin-bottom: 2vh;">Typ účtu: <?php echo $_GET['type']; ?></h4>
                <table id="logins-table" class="table table-striped table-dark table-hover table-bordered">
                    <thead>
                        <tr>
                            <th>Timestamp prihlásenia</th>
                        </tr>
                    </thead>
                    <tbody>
                    <?php
                    foreach ($results as $res) {
                        echo "<tr><td>" . $res['created_at'] . "</td></tr>";
                    }
                    ?>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="amountModal" tabindex="-1" aria-labelledby="amountModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="amountModalLabel">Počet užívateľov doteraz prihlásených do systému</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <table id="count-table" class="table table-dark table-striped table-hover table-bordered">
                    <thead>
                    <tr>
                        <th>Typ účtu</th>
                        <th>Počet užívateľov</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Classic</td>
                        <td><?php echo $classic_count['COUNT(*)']; ?></td>
                    </tr>
                    <tr>
                        <td>Google</td>
                        <td><?php echo $google_count['COUNT(*)']; ?></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="col">
            <h2>Dashboard</h2>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <h4>You are logged in as: <?php echo $_SESSION['name']; ?><br></h4>
        </div>
    </div>
    <div class="row">
        <div class="col" style="text-align: right;">
            <button id="loginsButton" type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#loginsModal">Zobraziť minulé prihlásenia</button>
        </div>
        <div class="col" style="text-align: left;">
            <button id="amountButton" type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#amountModal">Počet prihlásení cez účty</button>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <a class="btn btn-danger" href="logout.php">Logout</a>
        </div>
    </div>
</div>

<script>

</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
</body>
</html>
