package com.consumoapi.consumoapi.services;

import java.io.IOException;
import java.net.*;
import java.util.*;
import org.json.*;
import com.consumoapi.consumoapi.models.Recomendacion;

public class RecomendacionService {
    private static String urlRecomendaciones = "https://api.jikan.moe/v4/anime/{id}/recommendations";

    public static List<Recomendacion> getRecomendations(String id) {
        return mapeo(solicitud(id));
    }

    public static JSONArray solicitud(String id) {
        JSONArray data = new JSONArray();
        try {
            URL url = new URL(urlRecomendaciones.replace("{id}", id));
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

                JSONObject json = new JSONObject(informacion.toString());
                data = json.getJSONArray("data");
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Error al obtener las recomendaciones");
        }
        return data;
    }

    public static List<Recomendacion> mapeo(JSONArray data) {
        List<Recomendacion> recomendaciones = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            JSONObject datoActual = data.getJSONObject(i).getJSONObject("entry");
            int id = datoActual.getInt("mal_id");
            String nombre = datoActual.getString("title");
            String imagen = datoActual.getJSONObject("images").getJSONObject("jpg").getString("image_url");

            recomendaciones.add(new Recomendacion(id, nombre, imagen));
        }

        return recomendaciones;
    }
}
