package com.example.respondme;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Usuario {

    private Integer estado;
    @SerializedName("usuario")
    private List<Usuario_> usuarios = null;

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public List<Usuario_> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario_> usuarios) {
        this.usuarios = usuarios;
    }
}
