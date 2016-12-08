package udg.cusur.ss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class Reportes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        setTitle("Reportes");

    }

    public void openReglamento(View vie){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cusur.udg.mx/es/sites/default/files/reglamento_gral_para_la_prestacion_del_servicio_social.pdf"));
        startActivity(browserIntent);
    }

    public void openGuiaReportes(View vie){

        Intent browserIntent = null;

        if(getCarrera() > 10){
            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cusur.udg.mx/es/sites/default/files/guia_reportes_trimestrales_y_global_nutricion_enfermeria_y_medicina._2016.pdf"));
        }else{
            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.cusur.udg.mx/es/sites/default/files/guia_de_reportes_bimestrales_2016.pdf"));
        }

        startActivity(browserIntent);
    }

    public int getCarrera(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
        int carrera = sharedPreferences.getInt("carrera",0);
        return carrera;
    }
}
