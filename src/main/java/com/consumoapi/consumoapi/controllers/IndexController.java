package com.consumoapi.consumoapi.controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.*;
import org.json.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.consumoapi.consumoapi.models.*;
import java.net.*;

@Controller
@RequestMapping("/app")
public class IndexController {
    @RequestMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("titulo", "Listado de anime");
        ConsumoApi.inicio();
        JSONArray json = new JSONArray(ConsumoApi.getAnimes());
        List<Anime> animes = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            JSONObject anime = json.getJSONObject(i);
            System.out.println();
            int id = anime.getInt("mal_id");
            String nombre = anime.getString("title");
            String status = anime.getString("status");
            String imagen = anime.getJSONObject("images").getJSONObject("jpg").getString("image_url");
            Anime animeActual = new Anime(id, nombre, status, imagen);
            animes.add(animeActual);
        }

        model.addAttribute("animes", animes);
        return "listar";
    }

    @GetMapping("/anime/{id}")
    public String busqueda(@PathVariable String id, Model modelo) {
        try {
            URL url = new URL(ConsumoApi.urlBusqueda.replace("{id}", id));
            System.out.println(url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // conectarnos
            conn.setRequestMethod("GET");
            conn.connect();

            // comprobar si la peticion es correcta
            int codigoRespuesta = conn.getResponseCode();
            if (codigoRespuesta != 200) {
                throw new RuntimeException("Ocurrio un error: " + codigoRespuesta);
            } else {
                // si es correcta, leer la informacion
                StringBuilder informacion = new StringBuilder();
                try (Scanner sc = new Scanner(url.openStream())) {
                    while (sc.hasNext()) {
                        informacion.append(sc.nextLine());
                    }
                }

                JSONObject anime = new JSONObject(informacion.toString());
                anime = anime.getJSONObject("data");

                // todos los datos
                int idAnime = anime.getInt("mal_id");
                String imagen = anime.getJSONObject("images").getJSONObject("jpg").getString("large_image_url");
                String nombre = anime.getString("title");
                String nombreJapones = anime.getString("title_japanese");
                String status = anime.getString("status");
                String sinopsis = anime.getString("synopsis");
                String tipo = anime.getString("type");
                String clasificacion = anime.getString("rating");

                System.out.println("generos:");
                List<String> generos = new ArrayList<>();
                for (int i = 0; i < anime.getJSONArray("genres").length(); i++) {
                    generos.add(anime.getJSONArray("genres").getJSONObject(i).getString("name"));
                }

                System.out.println(generos);

                System.out.println("openings:");
                List<String> openings = new ArrayList<>();
                for (int i = 0; i < anime.getJSONObject("theme").getJSONArray("openings").length(); i++) {
                    openings.add(anime.getJSONObject("theme").getJSONArray("openings").getString(i));
                }

                System.out.println(openings);

                System.out.println("endings");
                List<String> endings = new ArrayList<>();
                for (int i = 0; i < anime.getJSONObject("theme").getJSONArray("endings").length(); i++) {
                    endings.add(anime.getJSONObject("theme").getJSONArray("endings").getString(i));
                }

                System.out.println(endings);

                Anime resultado = new Anime(idAnime, nombre, status, imagen, nombreJapones, sinopsis, tipo,
                        clasificacion, generos, openings, endings);

                modelo.addAttribute("titulo", nombre);
                modelo.addAttribute("anime", resultado);
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Error en la busqueda");
        }
        return "anime";
    }
}
