package com.consumoapi.consumoapi.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.consumoapi.consumoapi.services.*;
import com.consumoapi.consumoapi.models.Anime;

@Controller
public class IndexController {

    private List<Integer> paginacion;

    @RequestMapping(value = { "/index", "/", "" })
    public String listar(Model model) {
        model.addAttribute("titulo", "AnimeTracker");
        model.addAttribute("subtitulo", "Listado de anime");
        List<Anime> animes = AnimeService.getAnimes(1);
        model.addAttribute("animes", animes);
        paginacion = AnimeService.paginacion;
        model.addAttribute("paginacion", paginacion);
        return "index";
    }

    @GetMapping("/anime/{id}")
    public String busqueda(@PathVariable String id, Model model) {
        model.addAttribute("titulo", "Resultado de busqueda");
        model.addAttribute("anime", AnimeService.busqueda(Integer.parseInt(id)));
        model.addAttribute("personajes", PersonajeService.getAllCharacters(id));
        return "anime";
    }

    @GetMapping("/anime/listado/{id}")
    public String pagina(@PathVariable String id, Model model) {
        model.addAttribute("titulo", "AnimeTracker");
        model.addAttribute("subtitulo", "Listado de anime");
        List<Anime> animes = AnimeService.getAnimes(Integer.parseInt(id));
        model.addAttribute("animes", animes);
        model.addAttribute("paginacion", paginacion);
        return "listado";
    }
}
