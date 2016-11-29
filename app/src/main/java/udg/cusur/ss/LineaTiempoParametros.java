package udg.cusur.ss;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LineaTiempoParametros extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


    TextView txt_fecha_inicio;
    Spinner carrera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_tiempo_parametros);

        txt_fecha_inicio = (TextView) findViewById(R.id.txt_fecha_inicio_parametros);
        carrera = (Spinner) findViewById(R.id.sp_carrera);
    }

    public void dateInicio(View view){

        DatePickerDialog date = new DatePickerDialog(this, this, 2016,1,1);
        date.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        txt_fecha_inicio.setText(String.format("%3$02d/%2$02d/%1$04d",dayOfMonth,month+1,year));
    }

    public void crearLinea(View view){
        if(txt_fecha_inicio.getText() != "" && carrera.getSelectedItemPosition() > 0 ){
            Intent intent = new Intent(LineaTiempoParametros.this,LineaTiempo.class);
            intent.putExtra("fecha_inicio",txt_fecha_inicio.getText().toString());
            intent.putExtra("carrera",carrera.getSelectedItemPosition());
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Seleccionar los datos correctamente",Toast.LENGTH_SHORT).show();
        }
    }
}
