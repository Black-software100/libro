<?php
    // conexion a la base de datos
    require_once 'db.php';
    $json =array();
    // verifica el metodo get
    if(isset($_GET["email"])&&isset($_GET["password"])){
        
        // informacion del correo
        $email = $_GET["email"];
        // informacion de la contraseña
        $password = sha1($_GET["password"]);
        
       $consulta = "SELECT name,addres FROM user WHERE email = '$email' AND "
               . "password = '$password'";
       
       $resultado = $mysql->query($consulta);
       
       $num = $resultado->num_rows;
       
       if($num>0){
           $row = $resultado->fetch_assoc();
           $json['user'][]=$row;
           echo json_encode($json);
           mysqli_close($mysql);
           
       }else{
           echo false;
       }
        
    }else{
        echo 'esperando...';
    }

