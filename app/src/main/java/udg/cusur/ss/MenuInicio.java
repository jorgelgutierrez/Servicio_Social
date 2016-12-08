package udg.cusur.ss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MenuInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);

        //Metodo si es la primera vez que se abre la app redirecciona a ajustes...
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
        Boolean first = sharedPreferences.getBoolean("first",true);
        if(first){
            Intent intent = new Intent(MenuInicio.this,Ajustes.class);
            startActivity(intent);
        }
    }

    public void irAjustes(View view){
        Intent intent = new Intent(MenuInicio.this,Ajustes.class);
        startActivity(intent);
    }

    public void irLineaTiempo(View view){
        Intent intent = new Intent(MenuInicio.this,LineaTiempo.class);
        startActivity(intent);
    }

    public void irReportes(View view){
        Intent intent = new Intent(MenuInicio.this,Reportes.class);
        startActivity(intent);
    }
}
