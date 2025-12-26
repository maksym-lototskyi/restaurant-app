package edu.pjatk.tin.restaurant.infrastructure.web.exception;

import edu.pjatk.tin.restaurant.application.reservation.ReservationCollisionException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class RestExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFoundException(EntityNotFoundException e) {
        return buildResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EntityExistsException.class, ReservationCollisionException.class})
    public ResponseEntity<ApiError> handleConflictExceptions(RuntimeException e) {
        return buildResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException e) {
        return buildResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception e) {
        logger.error("Unhandled exception occurred", e);
        return buildResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiError> buildResponse(Exception e, HttpStatus status) {
        ApiError apiError = ApiError.of(
                status.value(),
                e.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
        );
        return new ResponseEntity<>(apiError, status);
    }
}
