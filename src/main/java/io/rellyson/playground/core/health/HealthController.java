package io.rellyson.playground.core.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class HealthController {


    @GetMapping(value = "/v1/healthz", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HealthResponse> healthcheck() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new HealthResponse("OK",
                        HttpStatus.OK.value(),
                        Instant.now().toString()));
    }
}
