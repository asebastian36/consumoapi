package com.consumoapi.consumoapi.services;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

import com.consumoapi.consumoapi.models.Anime;

public class TemporadaService {
    public static int paginasTotales;
    public static String urlTemporadaActual = "https://api.jikan.moe/v4/seasons/now";
    public static String urlTemporadas = "https://api.jikan.moe/v4/seasons";
    public static List<String> temporadas;

    public static List<Anime> getTemporadaActual() {
        JSONArray data = solicitudLista(urlTemporadaActual);
        return mapeo(data);
    }

    public static List<String> getTemporadas() {
        JSONArray data = solicitudLista(urlTemporadas);
        temporadas = mapeoTemporadas(data);
        return temporadas;
    }

    private static JSONArray solicitudLista(String url) {
        JSONArray data = new JSONArray();
        try {
            URL rutaApi = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) rutaApi.openConnection();

            // conectarnos
            conn.setRequestMethod("GET");
            conn.connect();

            System.out.println(rutaApi);

            // comprobar si la peticion es correcta
            int codigoRespuesta = conn.getResponseCode();
            if (codigoRespuesta != 200) {
                throw new RuntimeException("Ocurrio un error: " + codigoRespuesta);
            } else {
                // si es correcta, leer la informacion
                StringBuilder informacion = new StringBuilder();
                try (Scanner sc = new Scanner(rutaApi.openStream())) {
                    while (sc.hasNext()) {
                        informacion.append(sc.nextLine());
                    }
                }

                // todos los datos
                JSONObject objeto = new JSONObject(informacion.toString());

                // extraer la lista de animes
                data = objeto.getJSONArray("data");
                System.out.println("Consumo exitoso de: " + rutaApi);

            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Error al obtener: " + url);
        }

        return data;
    }

    private static List<Anime> mapeo(JSONArray data) {
        List<Anime> animes = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject anime = data.getJSONObject(i);
            int id = anime.getInt("mal_id");
            String nombre = anime.getString("title");
            String imagen = anime.getJSONObject("images").getJSONObject("jpg").getString("image_url");
            Anime animeActual = new Anime(id, nombre, imagen);
            animes.add(animeActual);
        }

        return animes;
    }

    private static List<String> mapeoTemporadas(JSONArray data) {
        List<String> temporadas = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject dato = data.getJSONObject(i);
            int year = dato.getInt("year");
            JSONArray temporada = dato.getJSONArray("seasons");
            
            for (int j = 0; j < temporada.length(); j++) {
                String resultado = temporada.getString(j) + " " + year;
                temporadas.add(resultado);
            }

        }

        return temporadas;
    }
}
