package io.rellyson.playground.core.health;

import org.springframework.http.HttpStatus;

public record HealthResponse(String message,
                             Integer statusCode,
                             String timestamp) {
}
