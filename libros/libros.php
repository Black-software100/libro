<?php

    require_once 'db.php';
    $json['Book'] =array();

    $consulta = "SELECT * FROM Book";

    $result = $mysql->query($consulta);

    while ($row = $result->fetch_assoc()){
        
        array_push($json['Book'], array('name'=>$row['name'],'price'=>$row['price']
                        ,'img'=> base64_encode($row['img'])));
       echo json_encode($json); 
    }
    mysqli_close($mysql);
    
  
