package com.example.dbwiki.data.model;

import java.io.Serializable;

public class Planet implements Serializable {
    private int id;
    private String nombre;
    private String imagen;
    private String descripcion;

    private Boolean destruido;

    public Planet(int id, String nombre, String imagen, String descripcion, Boolean destruido) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.destruido = destruido;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Boolean getDestruido() {
        return destruido;
    }
    public void setDestruido(Boolean destruido) {
        this.destruido = destruido;
    }
}
