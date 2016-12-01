package com.bixspace.ciclodevida.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by junior on 30/11/16.
 */

public class ResponseUser implements Serializable {

    private boolean exito;
    private ArrayList<PersonaEntity> personas;


    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public ArrayList<PersonaEntity> getPersonas() {
        return personas;
    }

    public void setPersonas(ArrayList<PersonaEntity> personas) {
        this.personas = personas;
    }
}
