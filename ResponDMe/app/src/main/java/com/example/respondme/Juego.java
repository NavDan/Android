package com.example.respondme;

import java.util.List;

public class Juego {

    private Integer estado;
    private List<Juego_> juego = null;

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public List<Juego_> getJuego() {
        return juego;
    }

    public void setJuego(List<Juego_> juego) {
        this.juego = juego;
    }

}