<?php

include ('Conexion.php');
$mensaje = "Reporte ";
$titulo = "Es Tiempo de hacer tu reporte";

$path_to_fcm = "https://fcm.googleapis.com/fcm/send";
$server_key = "AIzaSyDAHjQTRGE8lbyInN1JVsHEf_J_uRQgTSU";
    
    $hoy = getdate();
    $dia = $hoy['mday'];
    $mes = $hoy['mon'];
    $year = $hoy['year'];
    
    if($dia < 10){
        $dia = "0".$dia;
    }
    if($mes < 10){
        $mes = "0".$mes;
    }
    
    $fecha_actual = $dia."/".$mes."/".$year;
    echo $fecha_actual;
    
    $sql_reporte1 = "SELECT Token FROM fechas_reportes WHERE Reporte1='$fecha_actual'";
    $result_reporte1 = mysqli_query($conexion,$sql_reporte1);
    $mensaje = "Reporte 1";

    while($row = mysqli_fetch_array($result_reporte1)){

        $key = $row['Token'];

        $headers = array(

            'Authorization:key='.$server_key,
            'Content-Type:application/json'
        );

        $fields = array('to'=>$key,
        'notification'=>array('title'=>$titulo,
                            'body'=>$mensaje,
                            'icon' => "ic_notificaciones",
                            'sound' => "default",
                            'color' => "#366695", 
                            'click_action' => "BandejaNotificaciones"),
        'data' => array('Titulo' => $titulo, 
                            'Mensaje' => $mensaje, 
                            'Id_Notificacion' => $id_notificacion)
                );

        $payload = json_encode($fields);

        $curl_session = curl_init();
        curl_setopt($curl_session, CURLOPT_URL, $path_to_fcm);
        curl_setopt($curl_session, CURLOPT_POST, true);
        curl_setopt($curl_session, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($curl_session, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl_session, CURLOPT_SSL_VERIFYHOST, 0);
        curl_setopt($curl_session, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($curl_session, CURLOPT_IPRESOLVE, CURL_IPRESOLVE_V4);
        curl_setopt($curl_session, CURLOPT_POSTFIELDS, $payload);

        curl_exec($curl_session);
        curl_close($curl_session);

    }
    
    $sql_reporte2 = "SELECT Token FROM fechas_reportes WHERE Reporte2='$fecha_actual'";
    $result_reporte2 = mysqli_query($conexion,$sql_reporte2);
    $mensaje = "Reporte 2";
    
    while($row = mysqli_fetch_array($result_reporte2)){
        
        $key = $row['Token'];
        
        $headers = array(
                         
                         'Authorization:key='.$server_key,
                         'Content-Type:application/json'
                         );
        
        $fields = array('to'=>$key,
                        'notification'=>array('title'=>$titulo,'body'=>$mensaje));
        
        $payload = json_encode($fields);
        
        $curl_session = curl_init();
        curl_setopt($curl_session, CURLOPT_URL, $path_to_fcm);
        curl_setopt($curl_session, CURLOPT_POST, true);
        curl_setopt($curl_session, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($curl_session, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl_session, CURLOPT_SSL_VERIFYHOST, 0);
        curl_setopt($curl_session, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($curl_session, CURLOPT_IPRESOLVE, CURL_IPRESOLVE_V4);
        curl_setopt($curl_session, CURLOPT_POSTFIELDS, $payload);
        
        curl_exec($curl_session);
        curl_close($curl_session);
        
    }
    
    $sql_reporte3 = "SELECT Token FROM fechas_reportes WHERE Reporte3='$fecha_actual'";
    $result_reporte3 = mysqli_query($conexion,$sql_reporte3);
    $mensaje = "Reporte 3";
    
    while($row = mysqli_fetch_array($result_reporte3)){
        
        $key = $row['Token'];
        
        $headers = array(
                         
                         'Authorization:key='.$server_key,
                         'Content-Type:application/json'
                         );
        
        $fields = array('to'=>$key,
                        'notification'=>array('title'=>$titulo,'body'=>$mensaje));
        
        $payload = json_encode($fields);
        
        $curl_session = curl_init();
        curl_setopt($curl_session, CURLOPT_URL, $path_to_fcm);
        curl_setopt($curl_session, CURLOPT_POST, true);
        curl_setopt($curl_session, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($curl_session, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl_session, CURLOPT_SSL_VERIFYHOST, 0);
        curl_setopt($curl_session, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($curl_session, CURLOPT_IPRESOLVE, CURL_IPRESOLVE_V4);
        curl_setopt($curl_session, CURLOPT_POSTFIELDS, $payload);
        
        curl_exec($curl_session);
        curl_close($curl_session);
        
    }
    
    $sql_reporte4 = "SELECT Token FROM fechas_reportes WHERE Reporte4='$fecha_actual'";
    $result_reporte4 = mysqli_query($conexion,$sql_reporte4);
    $mensaje = "Reporte 4";
    
    while($row = mysqli_fetch_array($result_reporte4)){
        
        $key = $row['Token'];
        
        $headers = array(
                         
                         'Authorization:key='.$server_key,
                         'Content-Type:application/json'
                         );
        
        $fields = array('to'=>$key,
                        'notification'=>array('title'=>$titulo,'body'=>$mensaje));
        
        $payload = json_encode($fields);
        
        $curl_session = curl_init();
        curl_setopt($curl_session, CURLOPT_URL, $path_to_fcm);
        curl_setopt($curl_session, CURLOPT_POST, true);
        curl_setopt($curl_session, CURLOPT_HTTPHEADER, $headers);
        curl_setopt($curl_session, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($curl_session, CURLOPT_SSL_VERIFYHOST, 0);
        curl_setopt($curl_session, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($curl_session, CURLOPT_IPRESOLVE, CURL_IPRESOLVE_V4);
        curl_setopt($curl_session, CURLOPT_POSTFIELDS, $payload);
        
        curl_exec($curl_session);
        curl_close($curl_session);
        
    }

mysqli_close($conexion);

?>
