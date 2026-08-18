package com.airquality.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.airquality.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private HttpServletRequest request;

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();

        when(request.getRequestURI())
                .thenReturn("/api/v1/air-quality/current");
    }

    @Test
    void shouldHandleCityNotFoundException() {

        CityNotFoundException exception =
                new CityNotFoundException("Pune");

        ResponseEntity<ErrorResponse> response =
                exceptionHandler.handleCityNotFound(
                        exception,
                        request
                );

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.NOT_FOUND);

        ErrorResponse body = response.getBody();

        assertThat(body).isNotNull();

        assertThat(body.status())
                .isEqualTo(404);

        assertThat(body.error())
                .isEqualTo("CITY_NOT_FOUND");

        assertThat(body.message())
                .isEqualTo("City not found: Pune");

        assertThat(body.path())
                .isEqualTo("/api/v1/air-quality/current");

        assertThat(body.timestamp())
                .isNotNull();

        assertThat(body.timestamp().getOffset())
                .isEqualTo(java.time.ZoneOffset.UTC);
    }

    @Test
    void shouldHandleExternalServiceException() {

        ExternalServiceException exception =
                new ExternalServiceException(
                        "Unable to retrieve location data."
                );

        ResponseEntity<ErrorResponse> response =
                exceptionHandler.handleExternalService(
                        exception,
                        request
                );

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);

        ErrorResponse body = response.getBody();

        assertThat(body).isNotNull();

        assertThat(body.status())
                .isEqualTo(503);

        assertThat(body.error())
                .isEqualTo("EXTERNAL_SERVICE_UNAVAILABLE");

        assertThat(body.message())
                .isEqualTo("Unable to retrieve location data.");

        assertThat(body.path())
                .isEqualTo("/api/v1/air-quality/current");

        assertThat(body.timestamp())
                .isNotNull();

        assertThat(body.timestamp().getOffset())
                .isEqualTo(java.time.ZoneOffset.UTC);
    }

    @Test
    void shouldHandleIllegalArgumentException() {

        IllegalArgumentException exception =
                new IllegalArgumentException(
                        "City must not be empty."
                );

        ResponseEntity<ErrorResponse> response =
                exceptionHandler.handleBadRequest(
                        exception,
                        request
                );

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        ErrorResponse body = response.getBody();

        assertThat(body).isNotNull();

        assertThat(body.status())
                .isEqualTo(400);

        assertThat(body.error())
                .isEqualTo("BAD_REQUEST");

        assertThat(body.message())
                .isEqualTo("City must not be empty.");

        assertThat(body.path())
                .isEqualTo("/api/v1/air-quality/current");

        assertThat(body.timestamp())
                .isNotNull();

        assertThat(body.timestamp().getOffset())
                .isEqualTo(java.time.ZoneOffset.UTC);
    }

    @Test
    void shouldHandleMissingRequestParameterException() {

        MissingServletRequestParameterException exception =
                new MissingServletRequestParameterException(
                        "city",
                        "String"
                );

        ResponseEntity<ErrorResponse> response =
                exceptionHandler.handleMissingParameter(
                        exception,
                        request
                );

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);

        ErrorResponse body = response.getBody();

        assertThat(body).isNotNull();

        assertThat(body.status())
                .isEqualTo(400);

        assertThat(body.error())
                .isEqualTo("BAD_REQUEST");

        assertThat(body.message())
                .isEqualTo(
                        "Required parameter 'city' is missing."
                );

        assertThat(body.path())
                .isEqualTo("/api/v1/air-quality/current");

        assertThat(body.timestamp())
                .isNotNull();

        assertThat(body.timestamp().getOffset())
                .isEqualTo(java.time.ZoneOffset.UTC);
    }

    @Test
    void shouldHandleGenericException() {

        Exception exception =
                new Exception("Unexpected database failure");

        ResponseEntity<ErrorResponse> response =
                exceptionHandler.handleGenericException(
                        exception,
                        request
                );

        assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        ErrorResponse body = response.getBody();

        assertThat(body).isNotNull();

        assertThat(body.status())
                .isEqualTo(500);

        assertThat(body.error())
                .isEqualTo("INTERNAL_SERVER_ERROR");

        assertThat(body.message())
                .isEqualTo("An unexpected error occurred.");

        assertThat(body.path())
                .isEqualTo("/api/v1/air-quality/current");

        assertThat(body.timestamp())
                .isNotNull();

        assertThat(body.timestamp().getOffset())
                .isEqualTo(java.time.ZoneOffset.UTC);
    }
}