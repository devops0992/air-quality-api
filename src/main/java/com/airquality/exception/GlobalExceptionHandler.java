package com.airquality.exception;

import com.airquality.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCityNotFound(
            CityNotFoundException exception,
            HttpServletRequest request) {

        log.warn(
                "City not found. path={}, message={}",
                request.getRequestURI(),
                exception.getMessage()
        );

        return buildResponse(
                HttpStatus.NOT_FOUND,
                "CITY_NOT_FOUND",
                exception.getMessage(),
                request
        );
    }

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ErrorResponse> handleExternalService(
            ExternalServiceException exception,
            HttpServletRequest request) {

        log.error(
                "External service failure. path={}, message={}",
                request.getRequestURI(),
                exception.getMessage(),
                exception
        );

        return buildResponse(
                HttpStatus.SERVICE_UNAVAILABLE,
                "EXTERNAL_SERVICE_UNAVAILABLE",
                exception.getMessage(),
                request
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            IllegalArgumentException exception,
            HttpServletRequest request) {

        log.warn(
                "Bad request. path={}, message={}",
                request.getRequestURI(),
                exception.getMessage()
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "BAD_REQUEST",
                exception.getMessage(),
                request
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(
            MissingServletRequestParameterException exception,
            HttpServletRequest request) {

        log.warn(
                "Missing request parameter. path={}, parameter={}",
                request.getRequestURI(),
                exception.getParameterName()
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "BAD_REQUEST",
                "Required parameter '" +
                        exception.getParameterName() +
                        "' is missing.",
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception exception,
            HttpServletRequest request) {

        log.error(
                "Unhandled exception. path={}, message={}",
                request.getRequestURI(),
                exception.getMessage(),
                exception
        );

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred.",
                request
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(
            HttpStatus status,
            String error,
            String message,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                status.value(),
                error,
                message,
                request.getRequestURI(),
                OffsetDateTime.now(ZoneOffset.UTC)
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }
}