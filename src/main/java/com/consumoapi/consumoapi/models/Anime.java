package com.consumoapi.consumoapi.models;

import java.util.List;

public class Anime {
    private int id;
    private String nombre;
    private String status;
    private String imagen;
    private String nombreJapones;
    private String sinopsis;
    private String tipo;
    private String clasificacion;
    private List<String> generos;
    private List<String> openings;
    private List<String> endings;
    private List<Personaje> personajes;
    private List<Recomendacion> recomendaciones;

    public Anime() {

    }

    public Anime(int id, String nombre, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Anime(int id, String nombre, String status, String imagen, String nombreJapones, String sinopsis,
            String tipo, String clasificacion, List<String> generos, List<String> openings, List<String> endings,
            List<Recomendacion> recomendaciones, List<Personaje> personajes) {
        this.id = id;
        this.nombre = nombre;
        this.status = status;
        this.imagen = imagen;
        this.nombreJapones = nombreJapones;
        this.sinopsis = sinopsis;
        this.tipo = tipo;
        this.clasificacion = clasificacion;
        this.generos = generos;
        this.openings = openings;
        this.endings = endings;
        this.recomendaciones = recomendaciones;
        this.personajes = personajes;
    }

    public String getNombreJapones() {
        return this.nombreJapones;
    }

    public void setNombreJapones(String nombreJapones) {
        this.nombreJapones = nombreJapones;
    }

    public String getSinopsis() {
        return this.sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClasificacion() {
        return this.clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public List<String> getGeneros() {
        return this.generos;
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public List<String> getOpenings() {
        return this.openings;
    }

    public void setOpenings(List<String> openings) {
        this.openings = openings;
    }

    public List<String> getEndings() {
        return this.endings;
    }

    public void setEndings(List<String> endings) {
        this.endings = endings;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Personaje> getPersonajes() {
        return this.personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    public List<Recomendacion> getRecomendaciones() {
        return this.recomendaciones;
    }

    public void setRecomendaciones(List<Recomendacion> recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", nombre='" + getNombre() + "'" +
                ", status='" + getStatus() + "'" +
                "}";
    }

}
