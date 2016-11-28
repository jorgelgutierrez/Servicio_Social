package udg.cusur.ss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Toast;

public class LineaTiempo extends AppCompatActivity {

    String date = "";
    int carrera = 0;
    SeekBar linea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_tiempo);

        linea = (SeekBar) findViewById(R.id.sb_linea_tiempo);

        Bundle datos = this.getIntent().getExtras();
        if(datos != null) {
            date = datos.getString("fecha_inicio");
            carrera = datos.getInt("carrera");
            Toast.makeText(getApplicationContext(),"Fecha de inicio "+date+" carrera "+ carrera,Toast.LENGTH_SHORT).show();
            if(carrera <= 10){
                Toast.makeText(getApplicationContext(),"480 horas",Toast.LENGTH_SHORT).show();
                linea.setMax(3);
            }else if(carrera == 12){
                Toast.makeText(getApplicationContext(),"nutricion",Toast.LENGTH_SHORT).show();
                linea.setMax(4);
            }else{
                Toast.makeText(getApplicationContext(),"1 anio exactamente",Toast.LENGTH_SHORT).show();
                linea.setMax(4);
            }
        }
    }
}
