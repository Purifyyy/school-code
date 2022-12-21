<?php
    /**
     * @var string $adr
     */
?>
<pre>
<?php
    require 'config.php';
    $path = isset($_GET['path']) ? realpath($_GET['path'])."/" : $adr;
    $link = strstr($path, 'uploads');
?>
</pre>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>File upload</title>
    <style>
        html {
            display: table;
            margin: auto;
        }

        body {
            display: table-cell;
            vertical-align: middle;
            color: white !important;
            background-color: black !important;
        }

        table, tr, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            color: white !important;
        }

        table.sortable thead {
            font-weight: bold;
            cursor: pointer;
        }

        .table .thead-dark th {
            border-color: white !important;
            border-bottom: none !important;
        }

        h1, h2 {
            text-align: center;
        }

        hr {
            visibility: hidden;
        }
    </style>
    <script src="sorttable.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
</head>

<body>

<h1>Uploaded files</h1>
<table class="sortable table table-striped table-hover table-bordered">
    <thead class="thead-dark">
        <tr>
            <th class="sorttable_alpha">Názov</th>
            <th>Veľkosť</th>
            <th>Čas</th>
        </tr>
    </thead>
    <tbody>
    <?php
        $files = scandir($path);
        foreach ($files as $file)
        {
            if(is_dir($path.$file))
            {
                ?>
                <?php
                if ((realpath($_GET['path']) == "..." || $path == "uploads/") && $file == "..")
                {
                    ?>
                    <tr>
                        <td><?php echo $file?></td>
                        <td colspan="2"></td>
                    </tr>
                <?php
                }
                else
                {
                    ?>
                    <tr>
                        <td><a href="?path=<?php echo $link.$file."/"?>"><?php echo $file?></a></td>
                        <td colspan="2"></td>
                    </tr>
                <?php
                }
            }
            else
            {
                ?>
                <tr>
                    <td><?php echo $file?></td>
                    <td><?php echo filesize($path.$file) ?></td>
                    <td><?php echo date("d.m.Y h:i:s",filemtime($path.$file)) ?></td>
                </tr>
                <?php
            }
        }
    ?>
    </tbody>
</table>
<hr>
<h2>Formulár pre upload</h2>
<div class="form-group">
    <form action="upload.php" enctype="multipart/form-data" method="post">
        <div class="form-group">
            <div><label for="title">Názov súboru:</label><input class="form-control" type="text" id="title" name="title"></div>
        </div>
        <div class="form-group">
            <div><label for="file">Súbor:</label><input class="form-control-file" type="file" id="file" name="fileToUpload"></div>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-dark">Upload</button>
        </div>
    </form>
</div>
</body>
</html>
