package com.example.bookstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/proxy")
public class DataProxyController {

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Proxy app-books
     */
    @GetMapping("/books")
    public ResponseEntity<?> getBooks() {
        try {
            String url = "http://app-books:8030/books/all";

            var response = restTemplate.getForEntity(url, String.class);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Proxy app-authors
     */
    @GetMapping("/authors")
    public ResponseEntity<?> getAuthors() {
        try {
            String url = "http://app-authors:8080/authors";

            var response = restTemplate.getForEntity(url, String.class);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Proxy  ISBN
     */
    @GetMapping("/authors/isbn/{isbn}")
    public ResponseEntity<?> getAuthorsByIsbn(@PathVariable String isbn) {
        try {
            String url = "http://app-authors:8080/authors/isbn/" + isbn;

            var response = restTemplate.getForEntity(url, String.class);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
