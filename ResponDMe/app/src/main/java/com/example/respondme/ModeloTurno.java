package com.example.respondme;

public class ModeloTurno {

    private String user;
    private int ronda;

    public ModeloTurno() {
    }

    public ModeloTurno(String user, int ronda) {
        this.user = user;
        this.ronda = ronda;
    }

    public String getTitulo() {
        return user;
    }

    public void setTitulo(String user) {
        this.user = user;
    }

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

}
