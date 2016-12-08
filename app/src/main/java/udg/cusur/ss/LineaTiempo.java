package udg.cusur.ss;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LineaTiempo extends AppCompatActivity {

    String fecha_actual = "";
    int carrera = 0;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4;
    ProgressBar progressBar1horas, progressBar2horas, progressBar3horas, progressBar4horas;
    ImageView linea1, linea2, linea3, linea4;
    TextView reporte1, reporte2, reporte3, reporte4, reporte_final;
    TextView fecha_inicio_ss;
    TextView txt_fecha_actual;
    EditText txt_horas;
    TextView txt_horas_realizadas;
    String diaSistema = "";
    int mesSistema = 0;
    int anioSistema = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea_tiempo);


        //Instanciando los objetos de la vista..
        fecha_inicio_ss = (TextView) findViewById(R.id.txt_fecha_inicio_linea_tiempo);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        progressBar1horas = (ProgressBar) findViewById(R.id.progressBar1horas);
        progressBar2horas = (ProgressBar) findViewById(R.id.progressBar2horas);
        progressBar3horas = (ProgressBar) findViewById(R.id.progressBar3horas);
        progressBar4horas = (ProgressBar) findViewById(R.id.progressBar4horas);
        linea1 = (ImageView) findViewById(R.id.linea1);
        linea2 = (ImageView) findViewById(R.id.linea2);
        linea3 = (ImageView) findViewById(R.id.linea3);
        linea4 = (ImageView) findViewById(R.id.linea4);
        reporte1 = (TextView) findViewById(R.id.txt_reporte1);
        reporte2 = (TextView) findViewById(R.id.txt_reporte2);
        reporte3 = (TextView) findViewById(R.id.txt_reporte3);
        reporte4 = (TextView) findViewById(R.id.txt_reporte4);
        txt_fecha_actual = (TextView) findViewById(R.id.txt_fecha_actual);
        txt_horas = (EditText) findViewById(R.id.txt_horas);
        txt_horas_realizadas = (TextView) findViewById(R.id.txt_total_horas_realizadas);
        reporte_final = (TextView) findViewById(R.id.txt_reporte_final);

        setTitle("Tu linea del tiempo");

        //Obteniendo la fecha del sistema...
        Date d = new Date();
        String fechaActual  = String.valueOf(DateFormat.format("dd/MM/yyyy", d.getTime()));
        String[] aFechaIng = fechaActual.split("/");
        diaSistema = aFechaIng[0];
        mesSistema = Integer.parseInt(aFechaIng[1]);
        anioSistema = Integer.parseInt(aFechaIng[2]);
        fecha_actual = diaSistema+"/"+mesSistema+"/"+anioSistema;
        Metodos metodos = new Metodos();
        txt_fecha_actual.setText("Fecha de hoy "+diaSistema+"/"+metodos.obtenerMes(mesSistema)+"/"+anioSistema);


        //Mostrar la linea del tiempo con sus reportes...
        mostrarReportes();
        new calcularProgreso().execute();
        new calcularProgresoHoras().execute();

    }


    //Asyntask para calcular el progreso de fechas de acuerdo a su fecha de inicio de ss...
    class calcularProgreso extends AsyncTask<Void, Void, Void>{

        double total_dias_primer_reporte ;
        double dias_faltantes_primer_reporte ;
        double total_dias_segundo_reporte ;
        double dias_faltantes_segundo_reporte ;
        double total_dias_tercer_reporte ;
        double dias_faltantes_tercer_reporte ;
        double total_dias_cuarto_reporte ;
        double dias_faltantes_cuarto_reporte ;

        @Override
        protected void onPreExecute() {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
            //Toast.makeText(getApplicationContext(),"Total de dias del inicio al primer reporte"+sharedPreferences.getInt("Total dias primero",0),Toast.LENGTH_LONG).show();
            total_dias_primer_reporte = sharedPreferences.getInt("Total dias primero",0);
            //Toast.makeText(getApplicationContext(),"Total de dias faltantes de la fecha actual a el primer reporte"+(calcularDias(sharedPreferences.getString("Primer reporte","0/0/0"))),Toast.LENGTH_LONG).show();
            dias_faltantes_primer_reporte = calcularDias(sharedPreferences.getString("Primer reporte","0/0/0"));
            //Toast.makeText(getApplicationContext(),"Total de dias del inicio al segundo reporte"+sharedPreferences.getInt("Total dias segundo",0),Toast.LENGTH_LONG).show();
            total_dias_segundo_reporte = sharedPreferences.getInt("Total dias segundo",0);
            //Toast.makeText(getApplicationContext(),"Total de dias faltantes de la fecha actual a el segundo reporte"+(calcularDias(sharedPreferences.getString("Segundo reporte","0/0/0"))),Toast.LENGTH_LONG).show();
            dias_faltantes_segundo_reporte = calcularDias(sharedPreferences.getString("Segundo reporte","0/0/0"));
            //Toast.makeText(getApplicationContext(),"Total de dias del inicio al tercer reporte"+sharedPreferences.getInt("Total dias tercero",0),Toast.LENGTH_LONG).show();
            total_dias_tercer_reporte = sharedPreferences.getInt("Total dias tercero",0);
            //Toast.makeText(getApplicationContext(),"Total de dias faltantes de la fecha actual a el tercer reporte"+(calcularDias(sharedPreferences.getString("Tercer reporte","0/0/0"))),Toast.LENGTH_LONG).show();
            dias_faltantes_tercer_reporte = calcularDias(sharedPreferences.getString("Tercer reporte","0/0/0"));
            //Toast.makeText(getApplicationContext(),"Total de dias del inicio al cuarto reporte"+sharedPreferences.getInt("Total dias cuarto",0),Toast.LENGTH_LONG).show();
            total_dias_cuarto_reporte = sharedPreferences.getInt("Total dias cuarto",0);
            //Toast.makeText(getApplicationContext(),"Total de dias faltantes de la fecha actual a el cuarto reporte"+(calcularDias(sharedPreferences.getString("Cuarto reporte","0/0/0"))),Toast.LENGTH_LONG).show();
            dias_faltantes_cuarto_reporte = calcularDias(sharedPreferences.getString("Cuarto reporte","0/0/0"));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*System.out.println(dias_faltantes_primer_reporte+" "+total_dias_primer_reporte);
            System.out.println(dias_faltantes_segundo_reporte+" "+total_dias_segundo_reporte);
            System.out.println(dias_faltantes_tercer_reporte+" "+total_dias_tercer_reporte);
            System.out.println(dias_faltantes_cuarto_reporte+" "+total_dias_cuarto_reporte);*/


            if(dias_faltantes_primer_reporte > 0 && dias_faltantes_primer_reporte <= total_dias_primer_reporte){
                double p1 = (dias_faltantes_primer_reporte/total_dias_primer_reporte)*100;
                final int progreso1 = (int) p1;
                System.out.println(progreso1);
                Thread worker1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar1.setProgress((progreso1 - 100) * -1);
                    }
                });
                worker1.start();

            }else if(dias_faltantes_primer_reporte == 0){
                Thread worker12 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar1.setProgress(100);
                    }
                });
                worker12.start();
            }
            if(dias_faltantes_segundo_reporte > 0 && dias_faltantes_segundo_reporte <= total_dias_segundo_reporte){
                double p2 = (dias_faltantes_segundo_reporte/total_dias_segundo_reporte)*100;
                final int progreso2 = (int) p2;
                System.out.println(progreso2);
                Thread worker2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar2.setProgress((progreso2 - 100) * -1);
                    }
                });
                worker2.start();
            }else if(dias_faltantes_segundo_reporte == 0){
                Thread worker22 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar2.setProgress(100);
                    }
                });
                worker22.start();
            }
            if(dias_faltantes_tercer_reporte > 0 && dias_faltantes_tercer_reporte <= total_dias_tercer_reporte){
                double p3 =  (dias_faltantes_tercer_reporte/total_dias_tercer_reporte)*100;
                final int progreso3 = (int) p3;
                Thread worker3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar3.setProgress((progreso3 - 100) * -1);
                    }
                });
                worker3.start();
            }else if(dias_faltantes_tercer_reporte == 0){
                Thread worker32 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar3.setProgress(100);
                    }
                });
                worker32.start();
            }
            if(dias_faltantes_cuarto_reporte > 0 && dias_faltantes_cuarto_reporte <= total_dias_cuarto_reporte){
                double p4 = (dias_faltantes_cuarto_reporte/total_dias_cuarto_reporte)*100;
                final int progreso4 = (int) p4;
                System.out.println(progreso4);
                Thread worker4 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar4.setProgress((progreso4 - 100) * -1);
                    }
                });
                worker4.start();
            }else if(dias_faltantes_cuarto_reporte == 0){
                Thread worker42 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar4.setProgress(100);
                    }
                });
                worker42.start();
            }
            return null;
        }
    }

    //Asyntask para calular las horas reralizadas con el total de horas que debe contar...
    class calcularProgresoHoras extends AsyncTask<Void, Void, Void>{

        double horas_realizadas;
        double total_horas;

        @Override
        protected void onPreExecute() {
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
            horas_realizadas = sharedPreferences.getInt("Horas",0);
            txt_horas_realizadas.setText("Total de Horas Realizadas: "+(int) horas_realizadas);
            total_horas = sharedPreferences.getInt("Total horas",0);
        }

        @Override
        protected Void doInBackground(Void... voids) {


            if(total_horas == 480){

                if(horas_realizadas <= 160){
                    double p1 = (horas_realizadas/160)*100;
                    final int progreso1 = (int) p1;
                    Thread worker1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar1horas.setProgress(progreso1);
                        }
                    });
                    worker1.start();

                }else if(horas_realizadas > 160){
                    Thread worker12 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar1horas.setProgress(100);
                        }
                    });
                    worker12.start();
                }

                if(horas_realizadas <= 320){
                    double p2 = ((horas_realizadas-160)/160)*100;
                    final int progreso2 = (int) p2;
                    Thread worker2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar2horas.setProgress(progreso2);
                        }
                    });
                    worker2.start();

                }else if(horas_realizadas > 320){
                    Thread worker22 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar2horas.setProgress(100);
                        }
                    });
                    worker22.start();
                }

                if(horas_realizadas <= 480){
                    double p3 = ((horas_realizadas-320)/160)*100;
                    final int progreso3 = (int) p3;
                    Thread worker3 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar3horas.setProgress(progreso3);
                        }
                    });
                    worker3.start();

                }else if(horas_realizadas > 160){
                    Thread worker32 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar3horas.setProgress(100);
                        }
                    });
                    worker32.start();
                }


            }else if(total_horas == 960){

                if(horas_realizadas <= 240){
                    double p1 = (horas_realizadas/240)*100;
                    final int progreso1 = (int) p1;
                    Thread worker1 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar1horas.setProgress(progreso1);
                        }
                    });
                    worker1.start();

                }else if(horas_realizadas > 240){
                    Thread worker12 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar1horas.setProgress(100);
                        }
                    });
                    worker12.start();
                }

                if(horas_realizadas <= 480){
                    double p2 = ((horas_realizadas-240)/240)*100;
                    final int progreso2 = (int) p2;
                    Thread worker2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar2horas.setProgress(progreso2);
                        }
                    });
                    worker2.start();

                }else if(horas_realizadas > 480){
                    Thread worker22 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar2horas.setProgress(100);
                        }
                    });
                    worker22.start();
                }

                if(horas_realizadas <= 720){
                    double p3 = ((horas_realizadas-480)/240)*100;
                    final int progreso3 = (int) p3;
                    Thread worker3 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar3horas.setProgress(progreso3);
                        }
                    });
                    worker3.start();

                }else if(horas_realizadas > 720){
                    Thread worker32 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar3horas.setProgress(100);
                        }
                    });
                    worker32.start();
                }

                if(horas_realizadas <= 960){
                    double p3 = ((horas_realizadas-720)/240)*100;
                    final int progreso3 = (int) p3;
                    Thread worker4 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar4horas.setProgress(progreso3);
                        }
                    });
                    worker4.start();

                }else if(horas_realizadas > 960){
                    Thread worker42 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar4horas.setProgress(100);
                        }
                    });
                    worker42.start();
                }
            }
            return null;
        }
    }

    //Metodo para sumar las horas ingresadas al total de horas que lleva el prestador de ss...
    public void sumarHorasServicio(View view){
        if(!txt_horas.getText().toString().equals("")){
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int horas_sumar = Integer.parseInt(txt_horas.getText().toString());
            int horas_realizadas = sharedPreferences.getInt("Horas",0);
            int resultado = horas_sumar + horas_realizadas;
            editor.putInt("Horas", resultado);
            editor.commit();
            new calcularProgresoHoras().execute();

        }else{
            Toast.makeText(getApplicationContext(),"Ingresa las horas para sumarlas",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para restar las horas ingresadas del total de horas que lleva el prestador de ss...
    public void restarHorasServicio(View view){
        if(!txt_horas.getText().toString().equals("")){
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            int horas_restar = Integer.parseInt(txt_horas.getText().toString());
            int horas_realizadas = sharedPreferences.getInt("Horas",0);
            if(horas_restar <= horas_realizadas){
                int resultado = horas_realizadas - horas_restar;
                editor.putInt("Horas", resultado);
                editor.commit();
                new calcularProgresoHoras().execute();
            }else{
                Toast.makeText(getApplicationContext(),"No puedes restar mas horas de las que no tienes",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(),"Ingresa las horas para restarlas",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para recibir los datos de fecha inicio de ss y carrera para poder mostrar su requeridos fechas de reportes...
    public void mostrarReportes(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Parametros", Context.MODE_PRIVATE);
        carrera = sharedPreferences.getInt("carrera",0);
        Metodos metodos = new Metodos();
        if(carrera != 0) {
            if(carrera < 12) {

                String[] fecha = sharedPreferences.getString("fecha_inicio","0/0/0").split("/");
                fecha_inicio_ss.setText("Fecha Inicio Servicio Social " + fecha[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha[1]))+"/"+fecha[2]);

                if (carrera <= 10) {

                    progressBar4.setVisibility(View.INVISIBLE);
                    linea4.setVisibility(View.INVISIBLE);
                    reporte4.setVisibility(View.INVISIBLE);
                    progressBar4horas.setVisibility(View.INVISIBLE);
                    String[] fecha1 = sharedPreferences.getString("Primer reporte","0/0/0").split("/");
                    reporte1.setText("Primer reporte " + fecha1[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha1[1]))+"/"+fecha1[2]);
                    String[] fecha2 = sharedPreferences.getString("Segundo reporte","0/0/0").split("/");
                    reporte2.setText("Segundo reporte " + fecha2[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha2[1]))+"/"+fecha2[2]);
                    String[] fecha3 = sharedPreferences.getString("Tercer reporte","0/0/0").split("/");
                    reporte3.setText("Tercer reporte " + fecha3[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha3[1]))+"/"+fecha3[2]);
                    reporte_final.setText("Reporte Final "+ fecha3[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha3[1]))+"/"+fecha3[2]);

                } else if (carrera == 11) {

                    String[] fecha1 = sharedPreferences.getString("Primer reporte","0/0/0").split("/");
                    reporte1.setText("Primer reporte " + fecha1[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha1[1]))+"/"+fecha1[2]);
                    String[] fecha2 = sharedPreferences.getString("Segundo reporte","0/0/0").split("/");
                    reporte2.setText("Segundo reporte " + fecha2[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha2[1]))+"/"+fecha2[2]);
                    String[] fecha3 = sharedPreferences.getString("Tercer reporte","0/0/0").split("/");
                    reporte3.setText("Tercer reporte " + fecha3[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha3[1]))+"/"+fecha3[2]);
                    String[] fecha4 = sharedPreferences.getString("Cuarto reporte","0/0/0").split("/");
                    reporte4.setText("Cuarto reporte " + fecha4[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha4[1]))+"/"+fecha4[2]);
                    reporte_final.setText("Reporte Final "+ fecha4[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha4[1]))+"/"+fecha4[2]);

                }

            }else if(carrera > 11){

                String[] fecha = sharedPreferences.getString("fecha_inicio_salud","0/0/0").split("/");
                fecha_inicio_ss.setText("Fecha Inicio Servicio Social " + fecha[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha[1]))+"/"+fecha[2]);

                String[] fecha1 = sharedPreferences.getString("Primer reporte","0/0/0").split("/");
                reporte1.setText("Primer reporte " + fecha1[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha1[1]))+"/"+fecha1[2]);
                String[] fecha2 = sharedPreferences.getString("Segundo reporte","0/0/0").split("/");
                reporte2.setText("Segundo reporte " + fecha2[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha2[1]))+"/"+fecha2[2]);
                String[] fecha3 = sharedPreferences.getString("Tercer reporte","0/0/0").split("/");
                reporte3.setText("Tercer reporte " + fecha3[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha3[1]))+"/"+fecha3[2]);
                String[] fecha4 = sharedPreferences.getString("Cuarto reporte","0/0/0").split("/");
                reporte4.setText("Cuarto reporte " + fecha4[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha4[1]))+"/"+fecha4[2]);
                reporte_final.setText("Reporte Final "+ fecha4[0]+"/"+metodos.obtenerMes(Integer.parseInt(fecha4[1]))+"/"+fecha4[2]);
            }
        }else{
            Toast.makeText(getApplicationContext(),"Para comenzar porfavor agregar tu fecha de inicio " +
                    "de servicio social que viene en tu oficio de comision y carrera e",Toast.LENGTH_LONG).show();
        }
    }

    //Metodo para calcular los dias entre fechas....
    public int calcularDias(String fecha_final){
        String fechaReporte = fecha_final;

        String[] afechaReporte = fechaReporte.split("/");
        int diaReporte = Integer.parseInt(afechaReporte[0]);
        int mesReporte = Integer.parseInt(afechaReporte[1]);
        int anioReporte = Integer.parseInt(afechaReporte[2]);

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día
        java.util.Date hoy = new Date(); //Fecha de hoy

        int año = anioReporte; int mes = mesReporte; int dia = diaReporte; //Fecha anterior
        Calendar calendar = new GregorianCalendar(año, mes-1, dia);
        java.sql.Date fechareporte = new java.sql.Date(calendar.getTimeInMillis());

        long diferencia = ( hoy.getTime() - fechareporte.getTime() )/MILLSECS_PER_DAY;

        if(diferencia < 0){
            diferencia = diferencia * -1;
            String adias = String.valueOf(diferencia);
            int dias = Integer.parseInt(adias);
            return dias;
        }else{
            return 0;
        }

    }


}
