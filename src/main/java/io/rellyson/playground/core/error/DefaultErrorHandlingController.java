package io.rellyson.playground.core.error;

import io.rellyson.playground.core.entities.ApiError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class DefaultErrorHandlingController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> onDefaultException(Exception ex) {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
        error.addReason(ex.getMessage());

        return ResponseEntity.status(error.getStatusCode()).body(error);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> onBadRequestException(Exception ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST);
        error.addReason(ex.getMessage());

        return ResponseEntity.status(error.getStatusCode()).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> onValidationException(ConstraintViolationException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST);
        error.addReason(ex.getMessage());

        for (ConstraintViolation violation : ex.getConstraintViolations())
            error.addReason((String.join(": ",
                    violation.getInvalidValue().toString(), violation.getMessage())));

        return ResponseEntity.status(error.getStatusCode()).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiError> onNotFoundException(NoResourceFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND);
        error.addReason(ex.getMessage());

        return ResponseEntity.status(error.getStatusCode()).body(error);
    }
}
