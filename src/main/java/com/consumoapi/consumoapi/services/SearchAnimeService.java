package com.consumoapi.consumoapi.services;

import java.io.IOException;
import java.net.*;
import java.util.*;
import org.json.*;
import com.consumoapi.consumoapi.models.*;

public class SearchAnimeService {
    private static String urlNombre = "https://api.jikan.moe/v4/anime?q={}&sfw";

    public static List<Anime> getResultados(String busqueda) {
        List<Anime> animes = new ArrayList<>();
        System.out.println(urlNombre.replace("{}", busqueda));
        try {
            URL url = new URL(urlNombre.replace("{}", busqueda));
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
                JSONArray data = json.getJSONArray("data");

                animes = mapeo(data);
                System.out.println("Consumo exitoso de: " + url);
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Error al obtener los personajes");
        }
        return animes;
    }

    private static List<Anime> mapeo(JSONArray data) {
        List<Anime> animes = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            JSONObject datoActual = data.getJSONObject(i);
            int id = datoActual.getInt("mal_id");
            String nombre = datoActual.getString("title");
            String imagen = datoActual.getJSONObject("images").getJSONObject("jpg").getString("image_url");

            animes.add(new Anime(id, nombre, imagen));
        }

        return animes;
    }
}
