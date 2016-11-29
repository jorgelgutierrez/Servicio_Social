package udg.cusur.ss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LineaTiempo extends AppCompatActivity {

    String date = "";
    int carrera = 0;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    ImageView linea1, linea2, linea3, linea4;
    TextView reporte1, reporte2, reporte3, reporte4;
    TextView fecha_inicio_ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_tiempo);

        fecha_inicio_ss = (TextView) findViewById(R.id.txt_fecha_inicio_linea_tiempo);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        linea1 = (ImageView) findViewById(R.id.linea1);
        linea2 = (ImageView) findViewById(R.id.linea2);
        linea3 = (ImageView) findViewById(R.id.linea3);
        linea4 = (ImageView) findViewById(R.id.linea4);
        reporte1 = (TextView) findViewById(R.id.txt_reporte1);
        reporte2 = (TextView) findViewById(R.id.txt_reporte2);
        reporte3 = (TextView) findViewById(R.id.txt_reporte3);
        reporte4 = (TextView) findViewById(R.id.txt_reporte4);


        //Recibiendo los datos de fecha inicio de ss y carrera...
        Bundle datos = this.getIntent().getExtras();
        if(datos != null) {
            date = datos.getString("fecha_inicio");
            fecha_inicio_ss.setText("Fecha Inicio Servicio Social "+date);
            carrera = datos.getInt("carrera");
            Toast.makeText(getApplicationContext(),"Fecha de inicio "+date+" carrera "+ carrera,Toast.LENGTH_SHORT).show();
            if(carrera <= 10){
                Toast.makeText(getApplicationContext(),"480 horas",Toast.LENGTH_SHORT).show();
                progressBar4.setVisibility(View.INVISIBLE);
                linea4.setVisibility(View.INVISIBLE);
            }else if(carrera == 12){
                Toast.makeText(getApplicationContext(),"nutricion",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(),"1 anio exactamente",Toast.LENGTH_SHORT).show();
                reporte1.setText("Primer reporte 30 de Abril");
                reporte2.setText("Segundo reporte 31 de Julio");
                reporte3.setText("Tercero reporte 31 de Octubre");
                reporte4.setText("Cuarto reporte 31 de Enero");
            }
        }
    }
}