package com.bixspace.ciclodevida.data;

import java.io.Serializable;

/**
 * Created by junior on 02/12/16.
 */

public class ResponseAddPersonModel implements Serializable {
    private boolean exito;
    private String mensaje;

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
