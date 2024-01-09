package io.rellyson.playground.core.entities;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;

@Getter
public class ApiError {

    private final String message;
    private final Integer statusCode;
    private final ArrayList<String> reasons;

    private final String timestamp;

    public ApiError(HttpStatus httpStatus) {
        this.message = httpStatus.getReasonPhrase();
        this.statusCode = httpStatus.value();
        this.reasons = new ArrayList<>();
        this.timestamp = Instant.now().toString();
    }

    public void addReason(String reason) {
        this.reasons.add(reason);
    }
}