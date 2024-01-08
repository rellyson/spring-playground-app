package io.rellyson.playground;

import io.rellyson.playground.core.error.DefaultErrorHandlingController;
import io.rellyson.playground.core.health.HealthController;
import io.rellyson.playground.core.health.HealthResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
class ApplicationTests {
	@LocalServerPort
	private int serverPort;

	@Autowired
	private DefaultErrorHandlingController defaultErrorHandlingController;
	@Autowired
	private HealthController healthController;
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		assertThat(defaultErrorHandlingController).isInstanceOf(DefaultErrorHandlingController.class).isNotNull();
		assertThat(healthController).isInstanceOf(HealthController.class).isNotNull();
	}

	@Test
	void healthChecking() {
		assertThat(this.restTemplate.getForObject(
				"http://localhost:" + serverPort + "/api/v1/healthz",
				HealthResponse.class).message()).isEqualTo("OK");
	}
}
