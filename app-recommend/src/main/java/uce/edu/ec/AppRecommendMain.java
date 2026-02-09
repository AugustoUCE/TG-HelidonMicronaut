package uce.edu.ec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import uce.edu.ec.servicios.BooksAiService;


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
        LOG.info("app-recommend iniciada");
        LOG.info(" Ollama debe estar corriendo en: http://localhost:11434");
        LOG.info("==================================================");
        

    }
}
