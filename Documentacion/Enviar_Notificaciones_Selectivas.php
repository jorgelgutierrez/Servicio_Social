<?php
    
    include ('Conexion.php');
    $mensaje = $_POST["mensaje"];
    $titulo = $_POST["titulo"];
    
    $path_to_fcm = "https://fcm.googleapis.com/fcm/send";
    $server_key = "AIzaSyDAHjQTRGE8lbyInN1JVsHEf_J_uRQgTSU";
    
    if( $_SERVER['REQUEST_METHOD'] == "POST" ){
        
        $sql = "SELECT Token FROM alumnos_fechas_reportes WHERE Reporte1='$fecha_actual'";
        $result = mysqli_query($conexion,$sql);
        
        while($row = mysqli_fetch_array($result)){
            
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
                                                  'color' => "#00695C"),
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
        
        $sql_update = "UPDATE token SET Enviar='false'";
        mysqli_query($conexion,$sql_update);
        
    }
    
    mysqli_close($conexion);
    
    ?>
