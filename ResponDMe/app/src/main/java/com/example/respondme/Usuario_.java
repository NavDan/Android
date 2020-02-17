package com.example.respondme;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario_ {

    @SerializedName("IdUsuario")
    @Expose
    private String idUsuario;
    @SerializedName("Usuario")
    @Expose
    private String usuario;
    @SerializedName("Nombre")
    @Expose
    private String nombre;
    @SerializedName("Pass")
    @Expose
    private String pass;
    @SerializedName("Nivel")
    @Expose
    private String nivel;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

}
