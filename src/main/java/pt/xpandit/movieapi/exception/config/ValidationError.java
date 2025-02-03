package pt.xpandit.movieapi.exception.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a validation error occurring in the application.
 * This class stores the field that caused the error and the corresponding error message.
 */
@Getter
@AllArgsConstructor
public class ValidationError {
    private String field;
    private String message;
}
