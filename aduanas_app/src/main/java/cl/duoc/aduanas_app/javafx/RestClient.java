package cl.duoc.aduanas_app.javafx;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class RestClient {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String BASE_URL = "http://localhost:8080/api";

    private RestClient() {
        // Clase utilitaria
    }

    /**
     * Realiza una petición GET que devuelve una lista de objetos T.
     * @param path Ruta relativa al recurso (p.ej. "/solicitudes-menores").
     * @param clazz Clase de los elementos de la lista.
     * @return Lista de objetos deserializados.
     */
    public static <T> List<T> getList(String path, Class<T> clazz) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Accept", "application/json")
                .GET()
                .build();

        String responseBody = CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return JsonUtils.fromJsonList(responseBody, clazz);
    }

    /**
     * Realiza una petición GET que devuelve el cuerpo completo como String.
     * @param path Ruta relativa.
     * @return Cadena JSON raw.
     */
    public static String get(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Accept", "application/json")
                .GET()
                .build();

        return CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    /**
     * Realiza una petición POST enviando un objeto como JSON.
     * @param path Ruta relativa.
     * @param obj  Objeto a serializar.
     */
    public static <R> void post(String path, R obj) throws Exception {
        String json = JsonUtils.toJson(obj);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
    }

    /**
     * Realiza una petición PUT para actualizar un recurso.
     * @param path Ruta relativa.
     * @param obj  Objeto a serializar.
     */
    public static <R> void put(String path, R obj) throws Exception {
        String json = JsonUtils.toJson(obj);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
    }

    /**
     * Realiza una petición DELETE sobre un recurso.
     * @param path Ruta relativa, incluyendo identificador si corresponde.
     */
    public static void delete(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .DELETE()
                .build();

        CLIENT.send(request, HttpResponse.BodyHandlers.discarding());
    }
}
