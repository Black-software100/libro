<?php

require_once 'db.php';

if(isset($_POST['email'])&& isset($_POST['password'])&& isset($_POST['upassword'])){
    
    
    $email = $_POST['email'];
    
    $password = sha1($_POST['password']);
    
    $uPassword = sha1($_POST['upassword']);
    
    $consulta = "SELECT * FROM user WHERE email = '$email' AND password = '$password'";
    
    $resultado = $mysql->query($consulta);
    
    $num = $resultado->num_rows;
    
    if($num>0){
        $resultado = null;
        $update = "UPDATE user SET password = '$uPassword' WHERE email = '$email'";
        $resultado = $mysql->query($update);
        
        if($resultado){
            echo 'true';
        }else{
            echo 'false';
        }
    }else{
        echo 'usuario no existe';
    }
}else{
    echo 'esperando respuesta';
}

