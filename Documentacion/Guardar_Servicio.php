<?php

include ('Conexion.php');
$token = $_POST["token"];
$r1 = $_POST["reporte1"];
$r2 = $_POST["reporte2"];
$r3 = $_POST["reporte3"];
$r4 = $_POST["reporte4"];
    
$sql = "SELECT * FROM fechas_reportes WHERE Token='$token'";
$result = mysqli_query($conexion,$sql);
    
if (mysqli_num_rows($result) > 0){
    
    $sql_update = "UPDATE fechas_reportes SET Reporte1='$r1', Reporte2='$r2', Reporte3='$r3', Reporte4='$r4' WHERE Token='$token'";
    mysqli_query($conexion,$sql_update);
    echo "si existe se actualizo";
   
} else {
    
    $insertar = "INSERT INTO fechas_reportes VALUES ('$token','$r1','$r2','$r3','$r4')";
    mysqli_query($conexion,$insertar);
    echo "no existe se creo";

}
    
mysqli_close($conexion);

?>
