package com.example.sistema_ventas.esquemaSqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sistema_ventas.esquemaSqlite.tablas.ClienteTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.ProductoTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.VentaCabeceraTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.VentaDetalleTabla;

public class ConexionSqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION= 1;
    private static final String DATABASE_NAME = "venta_simple";

    public ConexionSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ProductoTabla.CREAR_TABLA_PRODUCTO);
        db.execSQL(ClienteTabla.CREAR_TABLA_CLIENTE);
        db.execSQL(VentaCabeceraTabla.CREAR_TABLA_VENTA_CABECERA);
        db.execSQL(VentaDetalleTabla.CREAR_TABLA_VENTA_DETALLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(ProductoTabla.ELIMINAR_TABLA_PRODUCTO);
        db.execSQL(ClienteTabla.ELIMINAR_TABLA_CLIENTE);
        db.execSQL(VentaCabeceraTabla.ELIMINAR_TABLA_VENTA_CABECERA);
        db.execSQL(VentaDetalleTabla.ELIMINAR_TABLA_VENTA_DETALLE);

    }
}
