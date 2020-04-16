package com.example.sistema_ventas.data.util;

import android.content.Context;
import android.widget.Toast;

public class Mensaje {
    private Context context;

    public Mensaje(Context context) {
        this.context = context;
    }

    public void mensajeToas(Object textoMensaje){
        Toast.makeText(context,textoMensaje.toString(), Toast.LENGTH_SHORT).show();
    }

    public void mensajeToasGuardar(){mensajeToas("La información se guardo correctamente");}
    public void mensajeToasEliminar(){mensajeToas("La información se elimino correctamente");}
}
