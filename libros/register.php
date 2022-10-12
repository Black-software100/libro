<?php

    require_once 'db.php';
    if(isset($_POST["name"]) && isset($_POST["old_year"]) && isset($_POST["addres"])
            && isset($_POST["email"]) && isset($_POST["password"])){
    
        $name = $_POST["name"];
        
        $old_year = $_POST["old_year"];
        
        $addres = $_POST["addres"];
        
        $email = $_POST["email"];
        
        $password = sha1($_POST["password"]);
        
        $register = "INSERT INTO usuario(name,old_year,addres,email,password)VALUES('$name',"
                . "'$old_year','$addres','$email','$password')";
        
        $resultado = $mysql->query($register);
        
        if($resultado){
            echo 'true';
            mysqli_close($mysql);
        }
    }else{
        echo 'esperando...';
    }

