package com.consumoapi.consumoapi.models;

public class Personaje {
    private Integer id;
    private String imagen;
    private String nombre;
    private String rol;

    public Personaje() {

    }

    public Personaje(Integer id, String imagen, String nombre, String rol) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.rol = rol;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setName(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return this.rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", imagen='" + getImagen() + "'" +
                ", nombre='" + getNombre() + "'" +
                ", rol='" + getRol() + "'" +
                "}";
    }
}
