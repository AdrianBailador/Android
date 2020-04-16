package com.example.sistema_ventas.esquemaSqlite.tablas;

public class ClienteTabla {

    public static final String TABLA = "cliente";

    public static final String CLIE_ID = "clie_id";
    public static final String CLIE_NOMBRE = "clie_nombre";
    public static final String CLIE_TELEFONO = "clie_telefono";
    public static final String CLIE_EMAIL = "clie_email";
    public static final String CLIE_DIRECCION = "clie_direccion";


    //creacion de tabla

    public static final String CREAR_TABLA_CLIENTE =
            "CREATE TABLE " + TABLA + "("
            + CLIE_ID + " INT,"
            + CLIE_NOMBRE + " TEXT,"
            + CLIE_TELEFONO + " TEXT,"
            + CLIE_EMAIL + " TEXT,"
            + CLIE_DIRECCION + "TEXT "
            +");";

    public static final String ELIMINAR_TABLA_CLIENTE = "DROP TABLE IF EXISTS" + TABLA + ";";

}
