package com.example.sistema_ventas.esquemaSqlite.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.sistema_ventas.data.modelo.Cliente;
import com.example.sistema_ventas.data.modelo.Producto;
import com.example.sistema_ventas.data.modelo.VentaCabecera;
import com.example.sistema_ventas.data.modelo.VentaDetalle;
import com.example.sistema_ventas.data.preferencia.SessionPreferences;
import com.example.sistema_ventas.esquemaSqlite.ConexionSqliteHelper;
import com.example.sistema_ventas.esquemaSqlite.tablas.ClienteTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.ProductoTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.VentaCabeceraTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.VentaDetalleTabla;

public class Update {


    public static void actualizar(Context context, Object param, String tabla){

        ConexionSqliteHelper con = new ConexionSqliteHelper(context);

        SQLiteDatabase db= con.getWritableDatabase();
        ContentValues values = new ContentValues();

        switch (tabla){
            case ClienteTabla.TABLA :
                Cliente cliente = (Cliente)param;
                values.put(ClienteTabla.CLIE_ID, cliente.getClie_id());
                values.put(ClienteTabla.CLIE_NOMBRE, cliente.getClie_nombre());
                values.put(ClienteTabla.CLIE_TELEFONO, cliente.getClie_num_tel());
                values.put(ClienteTabla.CLIE_EMAIL, cliente.getClie_email());
                values.put(ClienteTabla.CLIE_DIRECCION, cliente.getClie_direccion());

                db.insert(ClienteTabla.TABLA,ClienteTabla.CLIE_ID,values);
                db.update(ClienteTabla.TABLA,values,ClienteTabla.CLIE_ID + "= ?" , new String[]{String.valueOf(cliente.getClie_id())});

                break;
            case ProductoTabla.TABLA :
                Producto producto = (Producto)param;

                values.put(ProductoTabla.PROD_ID, producto.getProd_id());
                values.put(ProductoTabla.PROD_NOMBRE, producto.getProd_nombre());
                values.put(ProductoTabla.PROD_PRECIO, producto.getProd_precio());
                values.put(ProductoTabla.PROD_RUTA_FOTO, producto.getProd_ruta_foto());

                db.insert(ProductoTabla.TABLA,ProductoTabla.PROD_ID,values);
                db.update(ProductoTabla.TABLA,values,ProductoTabla.PROD_ID + "= ?" , new String[]{String.valueOf(producto.getProd_id())});

                break;

        }
    }
}
