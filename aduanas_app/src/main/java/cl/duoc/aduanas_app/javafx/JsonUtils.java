package cl.duoc.aduanas_app.javafx;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.List;

/**
 * Utilitario sencillo para convertir de JSON a objetos y viceversa.
 */
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtils {

    // Crear un mapper estático con JavaTimeModule registrado
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            // Hace que use texto ISO en vez de timestamps numéricos
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) throws IOException {
        JavaType type = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, clazz);
        return objectMapper.readValue(json, type);
    }

    public static String toJson(Object obj) throws IOException {
        return objectMapper.writeValueAsString(obj);
    }
    // (si tienes toJson, úsalo también con el mismo objectMapper)
}