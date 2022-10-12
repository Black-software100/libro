<?php
    $json['img']=array();
    
    require_once 'db.php';
    
    if(isset($_POST["btn"])){
        
        $ruta = "img";
        
        $archivo =$_FILES['img']['tmp_name'];
        
        $nameArchivo = $_FILES['img']['name'];
        
        move_uploaded_file($archivo, $ruta."/".$nameArchivo);
        $ruta=$ruta."/".$nameArchivo;
        
        $name = $_POST['name'];
        
        $price = $_POST['price'];
        
        echo '<br>';
        echo 'nombre ';
        echo $name;
        echo '<br>';
        echo 'precio ';
        echo $price;
        echo '<br>';
        echo 'ruta ';
        echo $ruta;
        echo '<br>';
        echo 'tipo imagen ';
        echo ($_FILES['img']['type']);
        echo '<br>';
        echo '<br>';
        echo "imagen <br> <img src='$ruta'>";
        echo '<br>';
        echo 'imagen en Byte';
        echo '<br>';
        echo '<br>';
        //echo $bytesArchivo= file_get_contents($ruta);
        echo '<br>';
        
        $bytesArchivo= file_get_contents($ruta);
        
        $insert = "INSERT INTO Book(img,name,price)values (?,?,?)";
        
        $stm=$mysql->prepare($insert);
        $stm->bind_param('ssi',$bytesArchivo,$name,$price);
        
        if($stm->execute()){
            echo 'imagen insertada Exitosamente';
            $consulta = "select * from Book where name = '{$name}'";
            
            $resultado =$mysql->query($consulta);
            
            echo '<br>';
            
            while ($row =$resultado->fetch_assoc()){
                
                echo $row['name'].' - '. $row['price'].'<br>';
                
                array_push($json['img'], array('name'=>$row['name'],'price'=>$row['price']
                        ,'img'=> base64_encode($row['name'])));
                    
                echo json_encode($json);
            }
        }
    }else{
       echo "esperando respuesta";
    }