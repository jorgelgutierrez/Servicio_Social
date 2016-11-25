package udg.cusur.ss;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

public class LineaTiempoServicio extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_tiempo_servicio);
    }

    public void dateInicio(View view){

        DatePickerDialog date = new DatePickerDialog(this, this, 2000,1,1);
        date.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView txt = (TextView) findViewById(R.id.txt_fecha);
        txt.setText(String.format("%1$04d/%2$02d/%3$02d",year,month,dayOfMonth));
    }
}
