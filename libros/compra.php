<?php
     require_once 'db.php';
    if(isset($_POST['idBook'])&& isset($_POST['email'])&& isset($_POST['password'])){
        
        $idBook = $_POST['idBook'];
        $email = $_POST['email'];
        $password  = sha1($_POST['password']);
        $activo = 'no';
        $consulta = "SELECT * FROM user WHERE email = '$email' "
                . "AND password = '$password'";
        
        $resultado = $mysql->query($consulta);
        $row = $resultado->fetch_assoc();
        $idUser = $row['idUser'];
        $num = $resultado->num_rows;
        
        if($num>0){
            $num = null;
            $consulta = null;
            $resultado = null;
            
            $consulta = "SELECT * FROM Book WHERE idBook = '$idBook'";
            $resultado = $mysql->query($consulta);
            $num = $resultado->num_rows;
            
            if($num>0){
                $resultado = null;
                $update = "UPDATE Book SET activo = '$activo' WHERE idBook = '$idBook'";
                $resultado = $mysql->query($update);
                
                if($resultado){
                    $resultado = null;
                    $insert = "INSERT INTO reserve(idUser,idBook)values('$idUser','$idBook')";
                    $resultado = $mysql->query($insert);
                    if($resultado){
                        echo 'true';   
                    }
                }else{
                    echo 'No se pudo hacer la modificacion';
                }
            }else{
                echo 'el libro no existe';
            }
           
       }else{
           echo 'el usuario o la contraseña son incorrecto';
       }
        
    }else{
        echo 'esperando respuesta...';
    }
    mysqli_close($mysql);

