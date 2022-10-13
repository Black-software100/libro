<?php

require_once 'db.php';

if(isset($_GET['id'])){
       
    $id = $_GET['id'];
    
    $consulta = "SELECT * FROM Book WHERE idBook ='$id'";
    
           
       $resultado = $mysql->query($consulta);
       
    while ($row = $resultado->fetch_assoc()){
        
        $Book["idBook"] = $row['idBook'];
        $Book["img"] = base64_encode($row['img']);
        $Book["name"] = $row['name'];
        $Book["price"] = $row['price'];
        $json['Book'][]=$Book;
       
    }
    echo json_encode($json); 
    mysqli_close($mysql);
}else{
    echo 'esperando respuesta';
}

