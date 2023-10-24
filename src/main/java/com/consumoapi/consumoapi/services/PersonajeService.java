package com.consumoapi.consumoapi.services;

import java.io.IOException;
import java.net.*;
import java.util.*;
import org.json.*;
import com.consumoapi.consumoapi.models.Personaje;

public class PersonajeService {

    public static String urlPersonajes = "https://api.jikan.moe/v4/anime/{id}/characters";

    public static List<Personaje> getAllCharacters(String id) {
        List<Personaje> personajes = new ArrayList<>();
        System.out.println(urlPersonajes.replace("{id}", id));
        try {
            URL url = new URL(urlPersonajes.replace("{id}", id));
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
                JSONArray jsonArray = json.getJSONArray("data");

                personajes.addAll(mapeo(jsonArray));
                System.out.println("Consumo exitoso de: " + url);
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Error al obtener los personajes");
        }

        return personajes;
    }

    private static List<Personaje> mapeo(JSONArray data) {
        List<Personaje> personajes = new ArrayList<>();

        for (int i = 0; i < data.length(); i++) {
            int idPersonaje = data.getJSONObject(i).getJSONObject("character").getInt("mal_id");
            String nombre = data.getJSONObject(i).getJSONObject("character").getString("name");
            String imagen = data.getJSONObject(i).getJSONObject("character").getJSONObject("images")
                    .getJSONObject("jpg")
                    .getString("image_url");
            String rol = data.getJSONObject(i).getString("role");
            personajes.add(new Personaje(idPersonaje, imagen, nombre, rol));
        }

        return personajes;
    }
}
