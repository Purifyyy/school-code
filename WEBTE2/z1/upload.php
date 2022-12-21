<?php
    if(isset($_POST['title']) && isset($_FILES['fileToUpload']))
    {
        $filetitle = $_POST['title'];
        $ext = pathinfo($_FILES['fileToUpload']['name'], PATHINFO_EXTENSION);
        //$filename = "uploads/".$filetitle.".$ext";
        $i = 1;
        while(file_exists("uploads/".$filetitle.".".$ext)){
            $filetitle = $_POST['title']."($i)";
            $i++;
        }
        $filename = "uploads/".$filetitle.".$ext";
        move_uploaded_file($_FILES['fileToUpload']['tmp_name'], $filename);
    }
    header("Location: index.php");
    exit;