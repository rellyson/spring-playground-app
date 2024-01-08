package io.rellyson.playground.core.error;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;

public record ApiError(String message,
                       Integer statusCode,
                       String reason,
                       String timestamp) {
}
