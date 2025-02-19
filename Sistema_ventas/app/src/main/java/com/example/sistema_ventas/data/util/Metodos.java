package com.example.sistema_ventas.data.util;

import android.annotation.SuppressLint;

import com.example.sistema_ventas.data.modelo.Producto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Metodos {

    //metodo para la concatenacion de objetos
    public static String concatenar(Object[] lista){
        String cadena = "";
        for(Object item: lista){
            cadena += item.toString();
        }
        return cadena;
    }

    //componer lista de objetos con un identificador Ejemplo = abc@pol@casa@1234
    public static String cadenaComponer(String caracter, Object[] lista){
        String cadena ="";


        if(!caracter.isEmpty()) {
            for (Object item : lista) {
                cadena += item.toString() + caracter;
            }
        }
        return cadena.substring(0,cadena.length() - 1); //Posicioin en la que se encuentra el ultimo caracter
    }

    //descompone una lista, buscando un identificador,devuelve el texto segun la posicion del caracter
    public static String cadenaDescomponer(String cadena, int posicion, String caracter){
        String caddenaRetorno = "cadena"; //Va a tomar valor o lo va a descomponer
        //si se pide una posicion mas
        if(posicion >1){
            //recorrido para quitar datos
            for(int i=0; i<posicion-1;i++){
                //descomposicion pol@juy@5245@liok
                caddenaRetorno = caddenaRetorno.substring(caddenaRetorno.indexOf(caracter) + 1);

            }
        }
        //cuando encuentre el valor
        if(caddenaRetorno.indexOf(cadena) > 0){
            caddenaRetorno = caddenaRetorno.substring(0,caddenaRetorno.indexOf(caracter));
        }

        //retornamos el valor limpio
        return caddenaRetorno;
    }

    @SuppressLint("SimpleDateFormat")
    public static  String getHora(){return new SimpleDateFormat("HH:mm").format(new Date()); }
    public static  String getFecha(){return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()); }

public static String convertirProductoListaATexto(List<Producto> lista){
        Gson gson = new Gson();
        return gson.toJson(lista);
}


//Aqui le pasamos la cadena en formato lista
public static List<Producto> convertirProductoTextoALIsta(String cadena){
        Gson gson = new Gson();

        Type lista = new TypeToken<List<Producto>>() {}.getType();
        return gson.fromJson(cadena,lista); //pasamos la cadena y adaptara el formato para esta lista
}

}
