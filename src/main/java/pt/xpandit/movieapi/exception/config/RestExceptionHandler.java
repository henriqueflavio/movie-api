package pt.xpandit.movieapi.exception.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pt.xpandit.movieapi.exception.MovieNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Global exception handler provides centralized exception handling across all
 * {@code @RequestMapping} methods through {@code @ExceptionHandler} methods.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles {@link MovieNotFoundException} by creating a custom error response.
     * This method is called when a movie is not found in the system.
     *
     * @param ex The MovieNotFoundException that was thrown
     * @return ResponseEntity containing error details and NOT_FOUND (404) status
     */
    @ExceptionHandler(MovieNotFoundException.class)
    private ResponseEntity<ErrorResponse> movieNotFoundHandler(MovieNotFoundException ex) {
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Handles validation errors that occur during request processing.
     * This method overrides the default Spring validation error handling to provide
     * a custom error response format with detailed validation error information.
     *
     * @param ex The MethodArgumentNotValidException that was thrown
     * @param headers The headers to be written to the response
     * @param status The selected response status
     * @param request The current request
     * @return ResponseEntity containing validation error details and BAD_REQUEST (400) status
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList();

        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Validation failed")
                .timestamp(LocalDateTime.now())
                .errors(validationErrors)
                .build();

        return ResponseEntity.badRequest().body(error);
    }
}
