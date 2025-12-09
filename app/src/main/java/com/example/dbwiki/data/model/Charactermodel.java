package com.example.dbwiki.data.model;

import java.io.Serializable;

public class Charactermodel implements Serializable {
    private int id;
    private String name;
    private String ki;
    private String maxki;
    private String race;
    private String image;
    private String affiliacion;

    public Charactermodel(int id, String nombre, String imagen, String ki, String maxki, String raza, String afiliacion) {
        this.id = id;
        this.name = nombre;
        this.image = imagen;
        this.ki = ki;
        this.maxki = maxki;
        this.race = raza;
        if(afiliacion.equals("Freelancer")|| afiliacion.equals("Army of Frieza")||afiliacion.equals("Villain")){
            this.affiliacion = "Villain";
        }else if(afiliacion.equals("Assistant of Beerus")|| afiliacion.equals("Assistant of Vermoud")||afiliacion.equals("Other")){
            this.affiliacion = "Other";
        } else if (afiliacion.equals("Pride Troopers")) {
            this.affiliacion = "Pride Troopers";
        }else{
            this.affiliacion = "Z Fighter";
        }
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return name;
    }
    public void setNombre(String nombre) {
        this.name = nombre;
    }
    public String getImagen() {
        return image;
    }
}
