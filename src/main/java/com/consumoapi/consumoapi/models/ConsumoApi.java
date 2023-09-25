package com.consumoapi.consumoapi.models;

import java.io.IOException;
import java.net.*;
import org.json.*;
import java.util.Scanner;

public class ConsumoApi {
    // Prueba para recopilar todos los animes
    public static int paginasTotales;
    public static JSONArray animes = new JSONArray();
    public static JSONObject paginacion = new JSONObject();
    public static final String URL_INICIAL = "https://api.jikan.moe/v4/anime";
    public static String urlDinamica = "https://api.jikan.moe/v4/anime?page=";
    public static String urlBusqueda = "https://api.jikan.moe/v4/anime/{id}/full";
    public static String urlPersonajes = "https://api.jikan.moe/v4/anime/{id}/characters";

    public static void inicio() {
        try {
            paginasTotales = 0;
            URL url = new URL(URL_INICIAL);
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

                paginacion = (JSONObject) objeto.get("pagination");
                paginasTotales = paginacion.getInt("last_visible_page");
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Error en inicio");
        }
    }

    public static JSONArray getAnimes() {
        if (paginasTotales != 0) {

            for (int i = 0; i < 5; i++) {
                System.out.println("Url actual: " + urlDinamica + (i + 1));
                long start = System.currentTimeMillis();
                // solicitar una peticion inicial
                try {
                    URL url = new URL(urlDinamica + (i + 1));
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
                        animes.putAll(objeto.getJSONArray("data"));

                    }
                } catch (IOException | RuntimeException e) {
                    System.out.println("Error en bucle");
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
            }
        } else {
            System.out.println("No has inicializado, ejecuta el metodo inicio...");
        }

        return animes;
    }
}
