package com.example.sistema_ventas.esquemaSqlite.crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.FileObserver;

import com.example.sistema_ventas.data.modelo.Cliente;
import com.example.sistema_ventas.data.modelo.Producto;
import com.example.sistema_ventas.data.modelo.VentaCabecera;
import com.example.sistema_ventas.data.modelo.VentaDetalle;
import com.example.sistema_ventas.data.util.Metodos;
import com.example.sistema_ventas.esquemaSqlite.ConexionSqliteHelper;
import com.example.sistema_ventas.esquemaSqlite.tablas.ClienteTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.ProductoTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.VentaCabeceraTabla;
import com.example.sistema_ventas.esquemaSqlite.tablas.VentaDetalleTabla;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Select {


    private static ConexionSqliteHelper con = null;
    private static SQLiteDatabase db = null;

    private static List<Object> seleccionarRegistros(Context context, String tabla) {
        List<Object> listaRetorno = new ArrayList<>();

        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        Cursor cLista = db.query(tabla, null, null, null, null, null, null);

        while (cLista.moveToNext()) {
            switch (tabla) {
                case ClienteTabla.TABLA:
                    listaRetorno.add(
                            new Cliente(
                                    cLista.getInt(cLista.getColumnIndex(ClienteTabla.CLIE_ID)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NOMBRE)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_TELEFONO)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_EMAIL)),
                                    cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_DIRECCION))
                            )
                    );
                    break;

                case ProductoTabla.TABLA:
                    listaRetorno.add(
                            new Producto(
                                    cLista.getInt(cLista.getColumnIndex(ProductoTabla.PROD_ID)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_NOMBRE)),
                                    cLista.getDouble(cLista.getColumnIndex(ProductoTabla.PROD_PRECIO)),
                                    cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_RUTA_FOTO)),
                                    false
                            )
                    );
                    break;
            }

        }
        return listaRetorno;
    }

    public static void seleccionarClientes(Context context, List<Cliente> lista, String buscar) {

        lista.clear();

        List<Object> tempLista = seleccionarRegistros(context, ClienteTabla.TABLA);

        for (Object item : tempLista) {
            Cliente _item = (Cliente) item;

            if (buscar.length() > 0) {

                if (_item.getClie_nombre().length() >= buscar.length()) {
                    String cadenaRecortada = _item.getClie_nombre().substring(0, buscar.length());
                    //pan
                    //pan de molde

                    if (buscar.equals(cadenaRecortada)) lista.add(_item);
                }

            } else {
                lista.add(_item);
            }
        }
    }

    public static void seleccionarProductos(Context context, List<Producto> lista, String buscar) {

        lista.clear();

        List<Object> tempLista = seleccionarRegistros(context, ProductoTabla.TABLA);

        for (Object item : tempLista) {
            Producto _item = (Producto) item;

            if (buscar.length() > 0) {

                if (_item.getProd_nombre().length() >= buscar.length()) {
                    String cadenaRecortada = _item.getProd_nombre().substring(0, buscar.length());
                    //paneton
                    //pan
                    //pan de molde

                    if (buscar.equals(cadenaRecortada)) lista.add(_item);
                }

            } else {
                lista.add(_item);
            }
        }
    }


    public static void seleccionarVentaCabecera(Context context, List<VentaCabecera> lista, String fecha, String buscar) {
        lista.clear();
        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        Cursor tempLista;

        String query = Metodos.concatenar(new Object[]{"select * from ", VentaCabeceraTabla.TABLA, " where "
                , VentaCabeceraTabla.VC_FECHA, " = ? order by ", VentaCabeceraTabla.VC_FECHA, " , ", VentaCabeceraTabla.VC_HORA, " desc"});

        tempLista = db.rawQuery(query, new String[]{fecha});
        while (tempLista.moveToNext()) {
            VentaCabecera item = new VentaCabecera (
                    tempLista.getInt(tempLista.getColumnIndex(VentaCabeceraTabla.VC_ID)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.VC_FECHA)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.VC_HORA)),
                    tempLista.getDouble(tempLista.getColumnIndex(VentaCabeceraTabla.VC_MONTO)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.VC_COMENTARIO)),
                    tempLista.getString(tempLista.getColumnIndex(VentaCabeceraTabla.CLIE_NOMBRE))

                    );
            if (buscar.length() > 0) {

                if (item.getClie_nombre().length() >= buscar.length()) {
                    String cadenaRecortada = item.getClie_nombre().substring(0, buscar.length());
                    //paneton
                    //pan
                    //pan de molde

                    if (buscar.equals(cadenaRecortada)) lista.add(item);
                }

            } else {
                lista.add(item);
            }
        }
    }

    public static void seleccionarVentaDetalle(Context context, List<VentaDetalle> lista, int vc_id) {
        lista.clear();
        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        Cursor tempLista;

        String query = Metodos.concatenar(new Object[]{"select * from ", VentaDetalleTabla.TABLA, "where "
                , VentaDetalleTabla.VC_ID, " = ? order by ", VentaDetalleTabla.PROD_NOMBRE, "desc"});

        tempLista = db.rawQuery(query, new String[]{String.valueOf(vc_id)});
        while (tempLista.moveToNext()) {
            lista.add(new VentaDetalle(
                    tempLista.getInt(tempLista.getColumnIndex(VentaDetalleTabla.VD_ID)),
                    tempLista.getInt(tempLista.getColumnIndex(VentaDetalleTabla.VD_CANTIDAD)),
                    tempLista.getDouble((tempLista.getColumnIndex(VentaDetalleTabla.VD_PRECIO))),
                    tempLista.getInt(tempLista.getColumnIndex(VentaDetalleTabla.VC_ID)),
                    tempLista.getString(tempLista.getColumnIndex(VentaDetalleTabla.PROD_NOMBRE)),
                    tempLista.getString(tempLista.getColumnIndex(VentaDetalleTabla.PROD_RUTA_FOTO))


            ));
        }
    }

    //Cadena donde vamos a guardar los datos que tengamos en nuestras tablas

    public static void backup(Context context) {
        StringBuilder cadenaCompuesta = new StringBuilder();
        con = new ConexionSqliteHelper(context);
        db = con.getReadableDatabase();

        for (String tablaSeleccionada : new String[]{ClienteTabla.TABLA, ProductoTabla.TABLA, VentaCabeceraTabla.TABLA, VentaDetalleTabla.TABLA}) {

            cadenaCompuesta.append("/////").append(tablaSeleccionada.toUpperCase()).append("/////").append("\n");

            Cursor cLista = db.query(tablaSeleccionada, null, null, null, null, null, null);


            while (cLista.moveToNext()) {
                switch (tablaSeleccionada) {
                    case ClienteTabla.TABLA:
                        cadenaCompuesta.append(
                                new Cliente(
                                        cLista.getInt(cLista.getColumnIndex(ClienteTabla.CLIE_ID)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_NOMBRE)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_TELEFONO)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_EMAIL)),
                                        cLista.getString(cLista.getColumnIndex(ClienteTabla.CLIE_DIRECCION))
                                ).componer("|")).append("\n");

                        break;

                    case ProductoTabla.TABLA:
                        cadenaCompuesta.append(
                                new Producto(
                                        cLista.getInt(cLista.getColumnIndex(ProductoTabla.PROD_ID)),
                                        cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_NOMBRE)),
                                        cLista.getDouble(cLista.getColumnIndex(ProductoTabla.PROD_PRECIO)),
                                        cLista.getString(cLista.getColumnIndex(ProductoTabla.PROD_RUTA_FOTO)),
                                        false
                                ).componer("|")).append("\n");

                        break;
                    case VentaCabeceraTabla.TABLA:
                        cadenaCompuesta.append(
                                new VentaCabecera(
                                        cLista.getInt(cLista.getColumnIndex(VentaCabeceraTabla.VC_ID)),
                                        cLista.getString(cLista.getColumnIndex(VentaCabeceraTabla.VC_FECHA)),
                                        cLista.getString(cLista.getColumnIndex(VentaCabeceraTabla.VC_HORA)),
                                        cLista.getDouble(cLista.getColumnIndex(VentaCabeceraTabla.VC_MONTO)),
                                        cLista.getString(cLista.getColumnIndex(VentaCabeceraTabla.VC_COMENTARIO)),
                                        cLista.getString(cLista.getColumnIndex(VentaCabeceraTabla.CLIE_NOMBRE))

                                ).componer("|")).append("\n");
                        break;
                    case VentaDetalleTabla.TABLA:
                        cadenaCompuesta.append(
                                new VentaDetalle(
                                        cLista.getInt(cLista.getColumnIndex(VentaDetalleTabla.VD_ID)),
                                        cLista.getInt(cLista.getColumnIndex(VentaDetalleTabla.VD_CANTIDAD)),
                                        cLista.getDouble(cLista.getColumnIndex(VentaDetalleTabla.VD_PRECIO)),
                                        cLista.getInt(cLista.getColumnIndex(VentaDetalleTabla.VC_ID)),
                                        cLista.getString(cLista.getColumnIndex(VentaDetalleTabla.PROD_NOMBRE)),
                                        cLista.getString(cLista.getColumnIndex(VentaDetalleTabla.PROD_RUTA_FOTO))
                                ).componer("|")).append("\n");
                        break;

                }
            }

        }

        //Creamos un nuevo archivo
        try {
            String nombre = "tienda.txt";
            File tarjeta = Environment.getExternalStorageDirectory();
            File file = new File(tarjeta.getAbsolutePath(), nombre);

            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file));
            osw.write(cadenaCompuesta.toString());
            osw.flush();
            osw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
