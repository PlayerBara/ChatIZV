package com.example.chatizv.modelo;

import java.io.Serializable;

public class PaqueteDatos implements Serializable {
    private String usuario, mensaje;

    public PaqueteDatos(String usuario, String contenido) {
        this.usuario = usuario;
        this.mensaje = contenido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return usuario + ": " + mensaje;
    }
}
