package io.rellyson.playground.core.entities;

public record HealthResponse(String message,
                             Integer statusCode,
                             String timestamp) {
}
