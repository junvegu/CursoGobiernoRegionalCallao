package com.bixspace.ciclodevida.data;

import java.io.Serializable;

/**
 * Created by junior on 30/11/16.
 */

public class PersonaEntity implements Serializable {


    private int id;
    private String apellidos;
    private int edad;
    private  String nombres;
   // private String foto;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

   // public String getFoto() {
    //  return foto;
    //}

   /* public void setFoto(String foto) {
        this.foto = foto;
    }*/
}
