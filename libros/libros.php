<?php

    require_once 'db.php';
    
    $activo = "si";

    $consulta = "SELECT * FROM Book WHERE activo = '$activo'";

    $result = $mysql->query($consulta);
    
    while ($row = $result->fetch_assoc()){
        
        $resultado["idBook"] = $row['idBook'];
        $resultado["img"] = base64_encode($row['img']);
        $resultado["name"] = $row['name'];
        $resultado["price"] = $row['price'];
        $json['Books'][]=$resultado;
       
    }
    echo json_encode($json); 
    mysqli_close($mysql);
    
    
  
