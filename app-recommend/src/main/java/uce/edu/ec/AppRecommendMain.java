package uce.edu.ec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import uce.edu.ec.servicios.BooksAiService;

/**
 * Clase de ejemplo para probar el servicio de recomendaciones al iniciar la aplicación
 * Implementa ApplicationEventListener para ejecutar código al iniciar
 */
@Singleton
public class AppRecommendMain implements ApplicationEventListener<StartupEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(AppRecommendMain.class);
    
    private final BooksAiService booksAiService;

    @Inject
    public AppRecommendMain(BooksAiService booksAiService) {
        this.booksAiService = booksAiService;
    }

    @Override
    public void onApplicationEvent(StartupEvent event) {
        LOG.info("==================================================");
        LOG.info("🚀 App Recommend iniciada correctamente");
        LOG.info("🤖 Servicio de recomendaciones con Llama 3.1 Local");
        LOG.info("📍 Ollama debe estar corriendo en: http://localhost:11434");
        LOG.info("==================================================");
        
        // Descomentar para probar recomendaciones al iniciar
        // var res = booksAiService.recommend("El Quijote");
        // LOG.info("Recomendación de prueba: {}", res);
    }
}
