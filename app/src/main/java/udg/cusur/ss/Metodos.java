package udg.cusur.ss;

/**
 * Created by Jorge on 01/12/16.
 */

public class Metodos {

    public String obtenerMes(int mes){
        String mes_cadena = "";
        switch (mes) {
            case (1):
                mes_cadena = "Enero";
                break;
            case (2):
                mes_cadena = "Febrero";
                break;
            case (3):
                mes_cadena = "Marzo";
                break;
            case (4):
                mes_cadena = "Abril";
                break;
            case (5):
                mes_cadena = "Mayo";
                break;
            case (6):
                mes_cadena = "Junio";
                break;
            case (7):
                mes_cadena = "Julio";
                break;
            case (8):
                mes_cadena = "Agosto";
                break;
            case (9):
                mes_cadena = "Septiembre";
                break;
            case (10):
                mes_cadena = "Octubre";
                break;
            case (11):
                mes_cadena = "Noviembre";
                break;
            case (12):
                mes_cadena = "Diciembre";
                break;
        }
        return mes_cadena;
    }
}
