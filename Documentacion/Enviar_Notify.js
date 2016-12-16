function enviarNotificacion(){
    var formulario = $('#Form').serialize();
    console.log(formulario);
    $.ajax({
           type:'post',
           url:'Enviar_Notificaciones_Selectivas.php',
           data:formulario,
           success:function(Enviado){
                alert(Enviado);
           }
    });
}
