package com.consumoapi.consumoapi.models;

import java.util.List;

public class Temporada {
    List<Anime> animes;

    public Temporada() {
    }

    public Temporada(List<Anime> animes) {
        this.animes = animes;
    }

    public List<Anime> getAnimes() {
        return this.animes;
    }

    public void setAnimes(List<Anime> animes) {
        this.animes = animes;
    }

    @Override
    public String toString() {
        return "{" +
            " animes='" + getAnimes() + "'" +
            "}";
    }

}
