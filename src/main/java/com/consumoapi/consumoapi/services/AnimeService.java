package com.consumoapi.consumoapi.services;

import java.io.IOException;
import java.net.*;
import java.util.*;
import org.json.*;

import com.consumoapi.consumoapi.models.Anime;

public class AnimeService {
    // Prueba para recopilar todos los animes
    public static int paginasTotales;
    public static List<Integer> paginacion;
    public static String urlAnimes = "https://api.jikan.moe/v4/anime?page=";
    public static String urlBusqueda = "https://api.jikan.moe/v4/anime/{id}/full";
    public static String urlPersonajes = "https://api.jikan.moe/v4/anime/{id}/characters";

    public static List<Anime> getAnimes(int id) {
        List<Anime> animes = new ArrayList<>();
        System.out.println("Url actual: " + urlAnimes + (id));
        long start = System.currentTimeMillis();
        // solicitar una peticion inicial
        try {
            URL url = new URL(urlAnimes + (id));
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

                // todos los datos
                JSONObject objeto = new JSONObject(informacion.toString());

                if (id == 1) {
                    // extraer la lista de animes
                    JSONObject pagination = objeto.getJSONObject("pagination");

                    paginasTotales = pagination.getInt("last_visible_page");
                    generarPaginas(paginasTotales);
                }

                // extraer la lista de animes
                JSONArray data = objeto.getJSONArray("data");

                // Extraer los animes del json y pasarlos al objeto Anime
                animes.addAll(AnimeService.mapeo(data));
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Error al obtener lista de animes");
            // Código que queremos medir
            long end = System.currentTimeMillis();

            long elapsedTime = end - start;
            System.out.println("Exito en obtener el JSON");
            System.out.println("El tiempo de ejecución fue de " + elapsedTime + " milisegundos.");
            System.out.println("");
        }

        // Código que queremos medir
        long end = System.currentTimeMillis();

        long elapsedTime = end - start;
        System.out.println("Exito en obtener el JSON");
        System.out.println("El tiempo de ejecución fue de " + elapsedTime + " milisegundos.");
        System.out.println("");

        return animes;
    }

    public static Anime busqueda(int id) {
        Anime anime = new Anime();

        try {
            URL url = new URL(AnimeService.urlBusqueda.replace("{id}", (id + "")));
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

                JSONObject json = new JSONObject(informacion.toString());
                JSONObject data = json.getJSONObject("data");
                anime = mapeo(data);
                System.out.println("consumo exitoso de la url: " + url.toString());
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Error en la busqueda");
        }

        return anime;
    }

    private static List<Anime> mapeo(JSONArray data) {
        List<Anime> animes = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject anime = data.getJSONObject(i);
            System.out.println();
            int id = anime.getInt("mal_id");
            String nombre = anime.getString("title");
            String status = anime.getString("status");
            String imagen = anime.getJSONObject("images").getJSONObject("jpg").getString("image_url");
            String sinopsis = anime.getString("synopsis");
            Anime animeActual = new Anime(id, nombre, status, imagen, sinopsis);
            animes.add(animeActual);
        }

        return animes;
    }

    private static Anime mapeo(JSONObject data) {
        Anime anime;

        int id = data.getInt("mal_id");
        String nombre = data.getString("title");
        String status = data.getString("status");
        String imagen = data.getJSONObject("images").getJSONObject("jpg").getString("image_url");
        String sinopsis = data.getString("synopsis");
        Anime animeActual = new Anime(id, nombre, status, imagen, sinopsis);
        anime = animeActual;

        return anime;
    }

    private static void generarPaginas(int cantidadPaginas) {
        paginacion = new ArrayList<>();

        for (int i = 0; i < cantidadPaginas; i++) {
            paginacion.add(i + 1);
        }

    }
}
