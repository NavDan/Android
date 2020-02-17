package com.example.respondme;

public class ModeloAddAmigo {

    private String user;
    private String nom;

    public ModeloAddAmigo() {
    }

    public ModeloAddAmigo(String user, String nom) {
        this.user = user;
        this.nom = nom;
    }

    public String getTitulo() {
        return user;
    }

    public void setTitulo(String user) {
        this.user = user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(int ronda) {
        this.nom = nom;
    }

}
