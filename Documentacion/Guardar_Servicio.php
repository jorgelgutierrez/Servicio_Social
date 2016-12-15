<?php

include ('Conexion.php');
$codigo = $_POST["codigo"];
$token = $_POST["token"];
$r1 = $_POST["reporte1"];
$r2 = $_POST["reporte2"];
$r3 = $_POST["reporte3"];
$r4 = $_POST["reporte4"];
    
$sql = "SELECT * FROM alumnos_fechas_reportes WHERE Token='$token'";
$result = mysqli_query($conexion,$sql);
    
if (mysqli_num_rows($result) > 0){
    
    $sql_update = "UPDATE alumnos_fechas_reportes SET Codigo='$codigo', Reporte1='$r1', Reporte2='$r2', Reporte3='$r3', Reporte4='$r4' WHERE Token='$token'";
    mysqli_query($conexion,$sql_update);
    echo "si existe se actualizo";
   
} else {
    
    $insertar = "INSERT INTO alumnos_fechas_reportes VALUES ('$codigo','$token','$r1','$r2','$r3','$r4')";
    mysqli_query($conexion,$insertar);
    echo "no existe se creo";

}
    
mysqli_close($conexion);

?>
