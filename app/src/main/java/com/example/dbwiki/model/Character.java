package com.example.dbwiki.model;

import java.io.Serializable;

public class Character implements Serializable {
    private int id;
    private String nombre;
    private String ki;
    private String maxki;
    private String raza;
    private String imagen;
    private String afiliacion;

    public Character(int id, String nombre, String imagen, String ki, String maxki, String raza, String afiliacion) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.ki = ki;
        this.maxki = maxki;
        this.raza = raza;
        if(afiliacion.equals("Freelancer")|| afiliacion.equals("Army of Frieza")||afiliacion.equals("Villain")){
            this.afiliacion = "Villain";
        }else if(afiliacion.equals("Assistant of Beerus")|| afiliacion.equals("Assistant of Vermoud")||afiliacion.equals("Other")){
            this.afiliacion = "Other";
        } else if (afiliacion.equals("Pride Troopers")) {
            this.afiliacion = "Pride Troopers";
        }else{
            this.afiliacion = "Z Fighter";
        }
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
}
