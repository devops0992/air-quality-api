package com.airquality.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

    private static final String SERVICE_KEY = "service";

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${app.version}")
    private String version;

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                SERVICE_KEY, applicationName
        );
    }

    @GetMapping("/info")
    public Map<String, Object> info() {
        return Map.of(
                SERVICE_KEY, applicationName,
                "description", "Real-Time Air Quality Intelligence Platform",
                "environment", System.getenv().getOrDefault("APP_ENV", "local")
        );
    }

    @GetMapping("/version")
    public Map<String, String> version() {
        return Map.of(
                "version", version,
                SERVICE_KEY, applicationName
        );
    }
}