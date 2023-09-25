package com.consumoapi.consumoapi.models;

import java.util.List;

public class Pagina {
    private Integer id;
    private List<Anime> animes;

    public Pagina() {

    }

    public Pagina(Integer id, List<Anime> animes) {
        this.id = id;
        this.animes = animes;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Anime> getAnimes() {
        return this.animes;
    }

    public void setAnimes(List<Anime> animes) {
        this.animes = animes;
    }
}
