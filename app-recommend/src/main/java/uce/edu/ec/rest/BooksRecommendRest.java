package uce.edu.ec.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import uce.edu.ec.dto.BookRecDto;
import uce.edu.ec.servicios.BooksAiService;

@Controller("/recommend")
@ExecuteOn(TaskExecutors.BLOCKING)
public class BooksRecommendRest {

    private static final Logger LOG = LoggerFactory.getLogger(BooksRecommendRest.class);
    
    private final BooksAiService booksAiService;

    @Inject
    public BooksRecommendRest(BooksAiService booksAiService) {
        this.booksAiService = booksAiService;
    }


    @Get(produces = MediaType.APPLICATION_JSON)
    public List<BookRecDto> findRecommend(@QueryValue String title) {
        LOG.info("Buscando recomendaciones para: {}", title);
        return booksAiService.recommend(title);
    }
}