package edu.pjatk.tin.restaurant.infrastructure.web.exception;

import edu.pjatk.tin.restaurant.application.reservation.ReservationCollisionException;
import edu.pjatk.tin.restaurant.util.validation.ValidationFailedException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException e) {
        logger.warn("Entity not found: {}", e.getMessage());
        return buildResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException e) {
        logger.warn("Bad credentials: {}", e.getMessage());
        return buildResponse(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({EntityExistsException.class, ReservationCollisionException.class})
    public ResponseEntity<ApiError> handleConflictExceptions(RuntimeException e) {
        logger.warn("Conflict occurred: {}", e.getMessage());
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(ValidationFailedException e) {
        logger.warn("Validation failed: {}", e.getMessage());
        return buildResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException e) {

        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            String field = violation.getPropertyPath().toString();
            errors.put(field, violation.getMessage());
        }

        return ResponseEntity.badRequest().body(
                ApiError.of(400, "Validation failed", errors)
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        f -> f.getDefaultMessage() == null ? "" : f.getDefaultMessage(),
                        (a, b) -> a
                ));

        return ResponseEntity.badRequest().body(
                ApiError.of(400, "Validation failed", errors)
        );
    }


    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ApiError> handleAllExceptions(Throwable e) {
        logger.error("Internal error occurred", e);
        return ResponseEntity.internalServerError().body(ApiError.of(500, "Internal error occurred"));
    }

    private ResponseEntity<ApiError> buildResponse(Exception e, HttpStatus status) {
        ApiError apiError = ApiError.of(
                status.value(),
                e.getMessage()
        );
        return new ResponseEntity<>(apiError, status);
    }
}
