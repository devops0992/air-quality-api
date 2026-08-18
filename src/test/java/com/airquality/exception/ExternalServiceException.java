package com.airquality.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ExternalServiceExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {

        ExternalServiceException exception =
                new ExternalServiceException(
                        "Unable to retrieve location data."
                );

        assertThat(exception)
                .isInstanceOf(RuntimeException.class);

        assertThat(exception.getMessage())
                .isEqualTo("Unable to retrieve location data.");

        assertThat(exception.getCause())
                .isNull();
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {

        Throwable cause =
                new RuntimeException("External API unavailable");

        ExternalServiceException exception =
                new ExternalServiceException(
                        "Unable to retrieve location data.",
                        cause
                );

        assertThat(exception)
                .isInstanceOf(RuntimeException.class);

        assertThat(exception.getMessage())
                .isEqualTo("Unable to retrieve location data.");

        assertThat(exception.getCause())
                .isSameAs(cause);
    }
}