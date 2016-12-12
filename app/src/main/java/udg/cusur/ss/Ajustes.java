package udg.cusur.ss;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;


public class Ajustes extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    Spinner sp_carrera;
    TextView txt_titulo_fecha_inicio;
    Button bt_fecha_inicio;
    TextView txt_fecha_inicio;
    TextView txt_titulo_fecha_inicio_salud;
    Spinner sp_fecha_inicio_salud;
    String diaSistema = "";
    int mesSistema = 0;
    int anioSistema = 0;
    String fecha_inicio = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        sp_carrera = (Spinner) findViewById(R.id.sp_carrera);
        txt_titulo_fecha_inicio = (TextView) findViewById(R.id.txt_titulo_fecha_inicio);
        bt_fecha_inicio = (Button) findViewById(R.id.bt_fecha_inicio);
        txt_fecha_inicio = (TextView) findViewById(R.id.txt_fecha_inicio);
        txt_titulo_fecha_inicio_salud = (TextView) findViewById(R.id.txt_titulo_fecha_inicio_salud);
        sp_fecha_inicio_salud = (Spinner) findViewById(R.id.sp_fecha_inicio_salud);

        setTitle("Ajustes");

        //Obteniendo la fecha del sistema...
        Date d = new Date();
        String fechaActual  = String.valueOf(DateFormat.format("dd/MM/yyyy", d.getTime()));
        String[] aFechaIng = fechaActual.split("/");
        diaSistema = aFechaIng[0];
        mesSistema = Integer.parseInt(aFechaIng[1]);
        anioSistema = Integer.parseInt(aFechaIng[2]);

        cargarPreferencias();

        //Meotodo de escucha para el spinner carreras y para mostrar lo necesario de acuerdo a la carrera...
        sp_carrera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0 && i <=11){
                    txt_titulo_fecha_inicio.setVisibility(View.VISIBLE);
                    bt_fecha_inicio.setVisibility(View.VISIBLE);
                    txt_fecha_inicio.setVisibility(View.VISIBLE);
                    txt_titulo_fecha_inicio_salud.setVisibility(View.INVISIBLE);
                    sp_fecha_inicio_salud.setVisibility(View.INVISIBLE);
                }else if(i >= 12){
                    txt_titulo_fecha_inicio.setVisibility(View.INVISIBLE);
                    bt_fecha_inicio.setVisibility(View.INVISIBLE);
                    txt_fecha_inicio.setVisibility(View.INVISIBLE);
                    txt_titulo_fecha_inicio_salud.setVisibility(View.VISIBLE);
                    sp_fecha_inicio_salud.setVisibility(View.VISIBLE);
                }else if(i == 0){
                    txt_titulo_fecha_inicio.setVisibility(View.INVISIBLE);
                    bt_fecha_inicio.setVisibility(View.INVISIBLE);
                    txt_fecha_inicio.setVisibility(View.INVISIBLE);
                    txt_titulo_fecha_inicio_salud.setVisibility(View.INVISIBLE);
                    sp_fecha_inicio_salud.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    //Metodo para iniciar el datepickerdialog...
    public void dateInicio(View view){
        DatePickerDialog date = new DatePickerDialog(this, this, 2016,1,1);
        date.show();
    }

    //Metodo para vizualisar la fecha agregada a la txt fecha inicio...
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        txt_fecha_inicio.setText(String.format("%1$02d/%2$02d/%3$02d",dayOfMonth,month+1,year));
    }

    //Metodo si es la primera vez que se abre la app redirecciona a ajustes...
    public void cargarPreferencias(){

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
        int carrera = sharedPreferences.getInt("carrera",0);
        if(carrera != 0){
            sp_carrera.setSelection(carrera);
            if(carrera > 11){
                String fecha_inicio = sharedPreferences.getString("fecha_inicio_salud","");
                String [] fecha = fecha_inicio.split("/");
                if(fecha[1].equals("02")){
                    sp_fecha_inicio_salud.setSelection(1);
                }else{
                    sp_fecha_inicio_salud.setSelection(2);
                }
            }else{
                String fecha_inicio = sharedPreferences.getString("fecha_inicio","");
                txt_fecha_inicio.setText(fecha_inicio);
            }
        }else{
            Toast.makeText(getApplicationContext(),"Indica tu carrea y fecha de inicio de tu servicio social",Toast.LENGTH_LONG).show();
        }
    }

    //Metodo para cargar los ajustes para la creacio de la linea del tiempo dependiendo de su carrera y fecha de inicio...
    public void crearLinea(View view){
        if(sp_carrera.getSelectedItemPosition() > 0 && txt_fecha_inicio.getText() != "" || sp_fecha_inicio_salud.getSelectedItemPosition() > 0){
            //Si la carrera es de 480 horas su servicio solo se guarda su carrera y la fecha de inicio que se selecciono de acuerdo a su oficio de comision...
            if (sp_carrera.getSelectedItemPosition() < 11) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fecha_inicio", fecha_inicio = txt_fecha_inicio.getText().toString());
                editor.putInt("carrera", sp_carrera.getSelectedItemPosition());
                editor.putInt("Total horas", 480);
                editor.putInt("Horas", 0);
                editor.putBoolean("first",false);
                editor.commit();
                calcularFechasReportes();
                new enviarMiServicio().execute();

            } else if (sp_carrera.getSelectedItemPosition() == 11) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fecha_inicio", fecha_inicio = txt_fecha_inicio.getText().toString());
                editor.putInt("carrera", sp_carrera.getSelectedItemPosition());
                editor.putInt("Total horas", 960);
                editor.putInt("Horas", 0);
                editor.putBoolean("first",false);
                editor.commit();
                calcularFechasReportes();
                new enviarMiServicio().execute();

            } else if (sp_carrera.getSelectedItemPosition() > 11) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String fecha_inicio_salud = "";
                if(sp_fecha_inicio_salud.getSelectedItemPosition() == 1)fecha_inicio_salud = "01/02/"+anioSistema;
                if(sp_fecha_inicio_salud.getSelectedItemPosition() == 2)fecha_inicio_salud = "01/08/"+anioSistema;
                editor.putString("fecha_inicio_salud", fecha_inicio = fecha_inicio_salud);
                editor.putInt("carrera", sp_carrera.getSelectedItemPosition());
                editor.putInt("Total horas", 960);
                editor.putInt("Horas", 0);
                editor.putBoolean("first",false);
                editor.commit();
                calcularFechasReportes();
                new enviarMiServicio().execute();

            }
        }else{
            Toast.makeText(getApplicationContext(),"Incompleto",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para calcular las fechas de los reportes...
    public void calcularFechasReportes(){

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(sp_carrera.getSelectedItemPosition() < 12) {

            String[] aFechaIng = txt_fecha_inicio.getText().toString().split("/");
            String diaInicio = aFechaIng[0];
            int mesInicio = Integer.parseInt(aFechaIng[1]);
            int anioInicio = Integer.parseInt(aFechaIng[2]);

            if(sp_carrera.getSelectedItemPosition() < 11){

                int mes = 0;
                int anio = 0;
                if(mesInicio+2 > 12){mes = mesInicio+2-12; anio = anioInicio + 1;}else{mes = mesInicio+2; anio = anioInicio;}
                editor.putString("Primer reporte",diaInicio+"/"+mes+"/"+anio);
                if(mesInicio+4 > 12){mes = mesInicio+4-12; anio = anioInicio + 1;}else{mes = mesInicio+4; anio = anioInicio;}
                editor.putString("Segundo reporte",diaInicio+"/"+mes+"/"+anio);
                if(mesInicio+6 > 12){mes = mesInicio+6-12; anio = anioInicio + 1;}else{mes = mesInicio+6; anio = anioInicio;}
                editor.putString("Tercer reporte",diaInicio+"/"+mes+"/"+anio);

            }else if(sp_carrera.getSelectedItemPosition() == 11){

                int mes = 0;
                int anio = 0;
                if(mesInicio+3 > 12){mes = mesInicio+3-12; anio = anioInicio + 1;}else{mes = mesInicio+3; anio = anioInicio;}
                editor.putString("Primer reporte",diaInicio+"/"+mes+"/"+anio);
                if(mesInicio+6 > 12){mes = mesInicio+6-12; anio = anioInicio + 1;}else{mes = mesInicio+6; anio = anioInicio;}
                editor.putString("Segundo reporte",diaInicio+"/"+mes+"/"+anio);
                if(mesInicio+9 > 12){mes = mesInicio+9-12; anio = anioInicio + 1;}else{mes = mesInicio+9; anio = anioInicio;}
                editor.putString("Tercer reporte",diaInicio+"/"+mes+"/"+anio);
                if(mesInicio+12 > 12){mes = mesInicio+12-12; anio = anioInicio + 1;}else{mes = mesInicio+12; anio = anioInicio;}
                editor.putString("Cuarto reporte",diaInicio+"/"+mes+"/"+anio);

            }

        } else if(sp_carrera.getSelectedItemPosition() > 11){

            if(sp_fecha_inicio_salud.getSelectedItemPosition() == 1){

                editor.putString("Primer reporte","30/04/"+anioSistema);
                editor.putString("Segundo reporte","31/07/"+anioSistema);
                editor.putString("Tercer reporte","31/10/"+anioSistema);
                editor.putString("Cuarto reporte","31/01/"+(anioSistema+1));

            }else if(sp_fecha_inicio_salud.getSelectedItemPosition() == 2){

                editor.putString("Primer reporte","31/10/"+anioSistema);
                editor.putString("Segundo reporte","31/01/"+(anioSistema+1));
                editor.putString("Tercer reporte","30/04/"+(anioSistema+1));
                editor.putString("Cuarto reporte","31/07/"+(anioSistema+1));
            }
        }
        editor.commit();
        calcularDiasFaltantesEntreFechasReportes();
    }

    //Metodo para guardar los dias entre fechas de reportes...
    public void calcularDiasFaltantesEntreFechasReportes(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
        String reporte1 = sharedPreferences.getString("Primer reporte","0/0/0");
        String reporte2 = sharedPreferences.getString("Segundo reporte","0/0/0");
        String reporte3 = sharedPreferences.getString("Tercer reporte","0/0/0");
        String reporte4 = sharedPreferences.getString("Cuarto reporte","0/0/0");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Total dias primero",  calcularDias(fecha_inicio,reporte1));
        editor.putInt("Total dias segundo", calcularDias(reporte1,reporte2));
        editor.putInt("Total dias tercero", calcularDias(reporte2,reporte3));
        editor.putInt("Total dias cuarto", calcularDias(reporte3,reporte4));
        editor.commit();
    }

    //Metodo para calcular los dias entre fechas...
    public int calcularDias(String fechaInicio, String fechaReporte){
        String[] aFechaIng = fechaInicio.split("/");
        int diaInicio = Integer.parseInt(aFechaIng[0]);
        int mesInicio = Integer.parseInt(aFechaIng[1]);
        int anioInicio = Integer.parseInt(aFechaIng[2]);

        String[] afechaReporte = fechaReporte.split("/");
        int diaReporte = Integer.parseInt(afechaReporte[0]);
        int mesReporte = Integer.parseInt(afechaReporte[1]);
        int anioReporte = Integer.parseInt(afechaReporte[2]);

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día

        int año1 = anioInicio; int mes1 = mesInicio; int dia1 = diaInicio; //Fecha anterior
        Calendar calendar1 = new GregorianCalendar(año1, mes1-1, dia1);
        java.sql.Date fechainicio = new java.sql.Date(calendar1.getTimeInMillis());

        int año2 = anioReporte; int mes2 = mesReporte; int dia2 = diaReporte; //Fecha anterior
        Calendar calendar2 = new GregorianCalendar(año2, mes2-1, dia2);
        java.sql.Date fechareporte = new java.sql.Date(calendar2.getTimeInMillis());

        long diferencia = ( fechareporte.getTime() - fechainicio.getTime() )/MILLSECS_PER_DAY;
        String adias = String.valueOf(diferencia);
        int dias = Integer.parseInt(adias);

        return dias;
    }

    //Asyntask para enviar fechas de acuerdo a su fecha de inicio de ss...
    class enviarMiServicio extends AsyncTask<Void, Void, Void> {

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Ajustes.this);
            pDialog.setMessage("   Cargando");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
            String cuarto_reporte = sharedPreferences.getString("Cuarto reporte","0/0/0");
            if(sharedPreferences.getInt("carrera",0) < 11){
                cuarto_reporte = "0/0/0";
            }

            String url_servidor = getResources().getString(R.string.url_servidor)+"Guardar_Servicio.php";
            HttpClient cliente = new DefaultHttpClient();
            HttpPost post = new HttpPost(url_servidor);
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>(1);
            postParameters.add(new BasicNameValuePair("token",sharedPreferences.getString("token","ninguno")));
            postParameters.add(new BasicNameValuePair("reporte1",sharedPreferences.getString("Primer reporte","0/0/0")));
            postParameters.add(new BasicNameValuePair("reporte2",sharedPreferences.getString("Segundo reporte","0/0/0")));
            postParameters.add(new BasicNameValuePair("reporte3",sharedPreferences.getString("Tercer reporte","0/0/0")));
            postParameters.add(new BasicNameValuePair("reporte4",cuarto_reporte));
            try {
                post.setEntity(new UrlEncodedFormEntity(postParameters, HTTP.UTF_8));
                cliente.execute(post);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_ajustes),"Servicio Guardado",Snackbar.LENGTH_LONG)
                    .setAction("Ver mi Progreso", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Ajustes.this,LineaTiempo.class);
                            startActivity(intent);
                            finish();
                        }
                    });
            snackbar.show();
        }
    }


}
