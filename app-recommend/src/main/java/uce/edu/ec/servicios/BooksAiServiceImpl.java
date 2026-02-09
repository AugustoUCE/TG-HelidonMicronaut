package uce.edu.ec.servicios;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uce.edu.ec.dto.BookRecDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementación del servicio de recomendaciones usando Ollama (IA Local)
 * Modelo: Meta-Llama-3.1-8B-Instruct
 * 
 * Ollama debe estar corriendo en http://localhost:11434
 * Modelo debe estar instalado: ollama pull llama3.1:8b
 */
@Singleton
public class BooksAiServiceImpl implements BooksAiService {

    private static final Logger LOG = LoggerFactory.getLogger(BooksAiServiceImpl.class);
    private static final String MODEL_NAME = "llama2:latest";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public BooksAiServiceImpl(
            @Client("http://localhost:11434") HttpClient httpClient,
            ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<BookRecDto> recommend(String title) {
        LOG.info("🤖 Solicitando recomendaciones a Llama 3.1 local para: {}", title);
        
        String promptText = String.format(
                """
                Eres un experto bibliotecario. Un usuario te dice que le gustó el libro "%s".
                
                Recomienda 2 libros similares en formato JSON VÁLIDO.
                
                Responde ÚNICAMENTE con un array JSON sin texto adicional, sin markdown, sin explicaciones:
                [
                    {
                        "titulo": "título del libro",
                        "isbn": "ISBN-13",
                        "editorial": "nombre de la editorial",
                        "descripcion": "breve descripción del libro"
                    }
                ]
                
                Ejemplo de respuesta:
                [
                    {"titulo":"El amor en los tiempos del cólera","isbn":"978-0307389732","editorial":"Vintage","descripcion":"Historia de amor épica"},
                    {"titulo":"Rayuela","isbn":"978-8420405964","editorial":"Alfaguara","descripcion":"Novela experimental"}
                ]
                """, title
        );

        try {
            // Construir request para Ollama API
            Map<String, Object> requestBody = Map.of(
                    "model", MODEL_NAME,
                    "prompt", promptText,
                    "stream", false,
                    "format", "json",
                    "options", Map.of(
                            "temperature", 0.7,
                            "num_predict", 800
                    )
            );

            LOG.debug("📤 Enviando request a Ollama con modelo: {}", MODEL_NAME);

            // Llamada a Ollama
            HttpRequest<?> request = HttpRequest.POST("/api/generate", requestBody)
                    .header("Content-Type", "application/json");

            String response = httpClient.toBlocking().retrieve(request);

            LOG.debug("📥 Respuesta recibida de Ollama");

            // Parsear respuesta de Ollama
            JsonNode jsonResponse = objectMapper.readTree(response);
            String content = jsonResponse.path("response").asText();

            // Limpiar el contenido
            content = cleanJsonResponse(content);

            LOG.debug("✨ Contenido limpiado: {}", content);

            // Convertir a lista de BookRecDto
            List<BookRecDto> recommendations = objectMapper.readValue(
                    content,
                    new TypeReference<List<BookRecDto>>() {}
            );

            LOG.info("✅ Se obtuvieron {} recomendaciones exitosamente", recommendations.size());
            return recommendations;

        } catch (Exception e) {
            LOG.error("❌ Error llamando a Ollama: {}", e.getMessage(), e);
            LOG.warn("⚠️  Retornando recomendaciones por defecto");
            return getDefaultRecommendations(title);
        }
    }

    /**
     * Limpia la respuesta JSON removiendo markdown, espacios extra, etc.
     */
    private String cleanJsonResponse(String content) {
        return content.trim()
                .replaceAll("```json\\s*", "")
                .replaceAll("```\\s*", "")
                .replaceAll("^[^\\[]*", "")  // Remover texto antes del [
                .replaceAll("[^\\]]*$", "")  // Remover texto después del ]
                .trim();
    }

    /**
     * Recomendaciones por defecto en caso de error con Ollama
     */
    private List<BookRecDto> getDefaultRecommendations(String title) {
        LOG.info("📚 Generando recomendaciones por defecto para: {}", title);
        List<BookRecDto> defaults = new ArrayList<>();

        defaults.add(BookRecDto.builder()
                .titulo("Cien años de soledad")
                .isbn("978-0307474728")
                .editorial("Vintage Español")
                .descripcion("Obra maestra del realismo mágico de Gabriel García Márquez")
                .build());

        defaults.add(BookRecDto.builder()
                .titulo("1984")
                .isbn("978-0451524935")
                .editorial("Signet Classic")
                .descripcion("Distopía clásica de George Orwell sobre vigilancia y control")
                .build());

        return defaults;
    }
}
