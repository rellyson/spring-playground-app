package io.rellyson.playground.core.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.time.Duration;

@Getter
public class LoggingBody {
    private final String method;
    private final String uri;
    private final int status;
    private final String durationInMs;

    private final ObjectMapper mapper;

    public LoggingBody(String method, String uri, int status, Duration duration) {
        this.mapper = new ObjectMapper();

        this.method = method;
        this.uri = uri;
        this.status = status;
        this.durationInMs = String.valueOf(duration.toMillis());
    }

    public String toJsonString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        String method = "method: " + this.method;
        String uri = "uri: " + this.uri;
        String status = "status: " + this.status;
        String durationInMs = "duration (ms): " + this.durationInMs;

        return String.join(", ", method, uri, status, durationInMs);
    }

}
