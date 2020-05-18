package com.example.sistema_ventas.data.modelo;

import com.example.sistema_ventas.data.util.Metodos;

import java.io.Serializable;

public class Cliente implements Serializable {


    private int clie_id;
    private String clie_nombre;
    private String clie_num_tel;
    private String clie_email;
    private String clie_direccion;

    public Cliente() {

    }

    public Cliente(int clie_id, String clie_nombre, String clie_num_tel, String clie_email, String clie_direccion) {
        this.clie_id = clie_id;
        this.clie_nombre = clie_nombre;
        this.clie_num_tel = clie_num_tel;
        this.clie_email = clie_email;
        this.clie_direccion = clie_direccion;
    }

    public int getClie_id() {
        return clie_id;
    }

    public void setClie_id(int clie_id) {
        this.clie_id = clie_id;
    }

    public String getClie_nombre() {
        return clie_nombre;
    }

    public void setClie_nombre(String clie_nombre) {
        this.clie_nombre = clie_nombre;
    }

    public String getClie_num_tel() {
        return clie_num_tel;
    }

    public void setClie_num_tel(String clie_num_tel) {
        this.clie_num_tel = clie_num_tel;
    }

    public String getClie_email() {
        return clie_email;
    }

    public void setClie_email(String clie_email) {
        this.clie_email = clie_email;
    }

    public String getClie_direccion() {
        return clie_direccion;
    }

    public void setClie_direccion(String clie_direccion) {
        this.clie_direccion = clie_direccion;
    }

    public String componer(String caracter){
        return Metodos.cadenaComponer(caracter,new Object[]{
                clie_id,
                clie_nombre,
                clie_num_tel,
                clie_email,clie_direccion
        });
    }
    public Cliente(String cadenaLeida, String caracter){
        this.clie_id = Integer.parseInt(Metodos.cadenaDescomponer(cadenaLeida,1,caracter));
        this.clie_nombre = Metodos.cadenaDescomponer(cadenaLeida,2,caracter);
        this.clie_num_tel = Metodos.cadenaDescomponer(cadenaLeida,3,caracter);
        this.clie_email = Metodos.cadenaDescomponer(cadenaLeida,4,caracter);
        this.clie_direccion = Metodos.cadenaDescomponer(cadenaLeida,5,caracter);
    }
}
