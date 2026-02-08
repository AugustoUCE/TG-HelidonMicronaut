package com.example.bookstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "{\"status\":\"UP\", \"service\":\"bookstore-vaadin\"}";
    }

    @GetMapping("/api/status")
    public String status() {
        return "{\"status\":\"UP\", \"service\":\"bookstore-vaadin\", \"timestamp\":\"" + System.currentTimeMillis() + "\"}";
    }
}
