package com.bookstore.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Hidden // Hide this endpoint from Swagger documentation
public class HomeController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getApiInfo() {
        Map<String, Object> apiInfo = new HashMap<>();
        apiInfo.put("name", "Bookstore API");
        apiInfo.put("version", "1.0.0");
        apiInfo.put("description", "RESTful API for managing books and authors");
        apiInfo.put("credits", "vaibhav from elevate labs");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("authors", "/api/authors");
        endpoints.put("books", "/api/books");
        endpoints.put("swagger-ui", "/swagger-ui.html");
        endpoints.put("api-docs", "/api-docs");
        endpoints.put("h2-console", "/h2-console");
        
        apiInfo.put("endpoints", endpoints);
        apiInfo.put("status", "running");
        
        return ResponseEntity.ok(apiInfo);
    }
}
