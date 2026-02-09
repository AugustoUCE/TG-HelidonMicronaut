package com.example.bookstore.service;

import com.example.bookstore.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final String apiBaseUrl;
    private final HttpClient httpClient;
    private final Gson gson;

    public BookService() {
        // Obtener URL base de variable de entorno o usar default
        this.apiBaseUrl = System.getenv("API_BASE_URL") != null ? 
            System.getenv("API_BASE_URL") : "http://localhost:8030/books";
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
        logger.info("BookService initialized with API_BASE_URL: {}", this.apiBaseUrl);
    }

    public BookService(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
        this.httpClient = HttpClient.newHttpClient();
        this.gson = new Gson();
        logger.info("BookService initialized with API_BASE_URL: {}", this.apiBaseUrl);
    }

    /**
     * Obtiene la lista de todos los libros
     */
    public List<Book> getAllBooks() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiBaseUrl + "/all"))
                    .GET()
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), new TypeToken<List<Book>>() {}.getType());
            } else {
                logger.error("Error fetching books: " + response.statusCode());
                return new ArrayList<>();
            }
        } catch (Exception e) {
            logger.error("Exception fetching books: ", e);
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene un libro específico por ISBN
     */
    public Book getBookByIsbn(String isbn) {
        try {
            String url = this.apiBaseUrl + "/" + isbn;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return gson.fromJson(response.body(), Book.class);
            } else {
                logger.error("Error fetching book: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("Exception fetching book: ", e);
            return null;
        }
    }
}
