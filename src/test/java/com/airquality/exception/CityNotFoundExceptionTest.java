package com.airquality.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CityNotFoundExceptionTest {

    @Test
    void shouldCreateExceptionWithCityName() {

        CityNotFoundException exception =
                new CityNotFoundException("Pune");

        assertThat(exception)
                .isInstanceOf(RuntimeException.class);

        assertThat(exception.getMessage())
                .isEqualTo("City not found: Pune");
    }
}