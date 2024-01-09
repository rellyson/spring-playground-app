package io.rellyson.playground.core.error;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DefaultErrorHandlingControllerTests {

    private final MockMvc mockMvc;
    private final FakeController fakeController;

    public DefaultErrorHandlingControllerTests() {
        this.fakeController = mock(FakeController.class);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(fakeController)
                .setControllerAdvice(DefaultErrorHandlingController.class)
                .build();
    }

    @Test
    void whenDefaultException_ReturnsInternalError() throws Exception {
        String error = createApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Ops!");
        doAnswer(a -> { throw new RuntimeException("Ops!"); })
                .when(this.fakeController).fakeRequest();

        this.mockMvc.perform(get("/fake"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().json(error));
    }

    @Test
    void whenMethodNotSupported_ReturnsErrorBadRequest() throws Exception {
        String error = createApiError(HttpStatus.BAD_REQUEST, "Request method 'GET' is not supported");
        doAnswer(a -> { throw new HttpRequestMethodNotSupportedException(HttpMethod.GET.name()); })
                .when(this.fakeController).fakeRequest();

        this.mockMvc.perform(get("/fake"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(error));
    }

    @Test
    void whenValidationError_ReturnsBadRequest() throws Exception {
        String error = createApiError(HttpStatus.BAD_REQUEST, "name field not valid");
        Set<? extends ConstraintViolation<?>> violations = new HashSet<>();

        doAnswer(a -> { throw new ConstraintViolationException("name field not valid", violations); })
                .when(this.fakeController).fakeRequest();

        this.mockMvc.perform(get("/fake"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(error));
    }

    @Test
    void whenGetNonExistentPath_ReturnsErrorNotFound() throws Exception {
        String error = createApiError(HttpStatus.NOT_FOUND, "No static resource /fake.");
        doAnswer(a -> { throw new NoResourceFoundException(HttpMethod.GET, "/fake"); })
                .when(this.fakeController).fakeRequest();

        this.mockMvc.perform(get("/fake"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(error));
    }

    private String createApiError(HttpStatus status, String reason) {
        return "{" +
                "\"message\":" + "\"" + status.getReasonPhrase()+ "\"," +
                "\"statusCode\":" + status.value()+ "," +
                "\"reasons\": [" + "\"" + reason + "\"" + "]" +
                "}";
    }
}
