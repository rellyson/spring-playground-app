package io.rellyson.playground.core.health;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HealthController.class)
public class HealthControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static final String HEALTH_RESPONSE_JSON =
            "{" +
            "\"message\":\"OK\"," +
            "\"statusCode\":200" +
            "}";

    @Test
    public void whenGetHealth_thenReturnJsonWithStatusOk() throws Exception {
        mockMvc.perform(get("/v1/healthz")).andExpect(status().isOk())
                .andExpect(header().string("Content-type","application/json"))
                .andExpect(content().json(HEALTH_RESPONSE_JSON));
    }
}
