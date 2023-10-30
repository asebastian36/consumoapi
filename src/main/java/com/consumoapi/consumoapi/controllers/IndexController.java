package com.consumoapi.consumoapi.controllers;

import java.util.*;
import java.util.regex.Pattern;
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
        model.addAttribute("animes", TemporadaService.getTemporadaActual());
        model.addAttribute("temporadas", TemporadaService.getTemporadas());
        return "index";
    }

    @GetMapping("/anime/{id}")
    public String busquedaId(@PathVariable String id, Model model) {
        Anime anime = AnimeService.busqueda(id);
        model.addAttribute("anime", anime);
        return "anime";
    }

    @GetMapping("/anime/listado/{id}")
    public String pagina(@PathVariable String id, Model model) {
        List<Anime> animes = AnimeService.getAnimes(Integer.parseInt(id));
        model.addAttribute("pagina", Integer.parseInt(id));
        model.addAttribute("ultimaPagina", AnimeService.paginasTotales);
        model.addAttribute("animes", animes);
        model.addAttribute("paginacion", paginacion);
        return "listado";
    }

    @PostMapping("/anime/busqueda")
    public String busqueda(@RequestParam String nombre, Model model) {
        Pattern patron = Pattern.compile("[0-9]+");
        if (patron.matcher(nombre).matches()) {
            return busquedaId(nombre, model);
        } else {
            if (nombre.contains(" "))
                nombre = nombre.replace(" ", "%20");
            List<Anime> animes = SearchAnimeService.getResultados(nombre);
            model.addAttribute("animes", animes);
            return "resultado";
        }
    }

    @GetMapping("anime/season/{informacion}")
    public String temporada(@PathVariable String informacion, Model model) {
        model.addAttribute("titulo", "AnimeTracker");
        model.addAttribute("subtitulo", "Bienvenid@");
        List<Anime> animes = TemporadaService.getTemporadActual(informacion);
        model.addAttribute("animes", animes);
        return "temporada";
    }
}
