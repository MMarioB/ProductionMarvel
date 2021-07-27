package com.example.personajesmarvel.models;

public class Personaje {

    private String id;
    private String nombre;
    private String url;
    private String description;

    public Personaje(String id, String nombre, String url, String resultDescription){
        this.id = id;
        this.nombre = nombre;
        this.url = url;
        this.description = resultDescription;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
