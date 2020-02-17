package com.example.respondme;

public class ModeloSolicitud {

    private String user, nom;

    public ModeloSolicitud() {
    }

    public ModeloSolicitud(String user, String nom) {
        this.user = user;
        this.nom = nom;
    }

    public String getTitulo() {
        return user;
    }

    public void setTitulo(String user) {
        this.user = user;
    }

    public String getDesc() {
        return nom;
    }

    public void setDesc(String nom) {
        this.nom = nom;
    }

}
