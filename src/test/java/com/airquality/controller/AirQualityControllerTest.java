package com.airquality.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.airquality.dto.AirQualityResponse;
import com.airquality.service.AirQualityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AirQualityControllerTest {

    @Mock
    private AirQualityService airQualityService;

    private AirQualityController airQualityController;

    @BeforeEach
    void setUp() {
        airQualityController =
                new AirQualityController(airQualityService);
    }

    @Test
    void shouldReturnCurrentAirQualityForValidCity() {

        String city = "Pune";

        AirQualityResponse expectedResponse =
                new AirQualityResponse(
                        "Pune",
                        18.5204,
                        73.8567,
                        85,
                        42.5,
                        65.2,
                        250.0,
                        30.5,
                        5.2,
                        100.4,
                        "2026-08-19T00:00:00"
                );

        when(airQualityService.getCurrentAirQuality(city))
                .thenReturn(expectedResponse);

        AirQualityResponse actualResponse =
                airQualityController.getCurrentAirQuality(city);

        assertThat(actualResponse)
                .isSameAs(expectedResponse);

        verify(airQualityService)
                .getCurrentAirQuality("Pune");
    }

    @Test
    void shouldTrimCityBeforeCallingService() {

        String city = "  Pune  ";

        AirQualityResponse expectedResponse =
                new AirQualityResponse(
                        "Pune",
                        18.5204,
                        73.8567,
                        85,
                        42.5,
                        65.2,
                        250.0,
                        30.5,
                        5.2,
                        100.4,
                        "2026-08-19T00:00:00"
                );

        when(airQualityService.getCurrentAirQuality("Pune"))
                .thenReturn(expectedResponse);

        AirQualityResponse actualResponse =
                airQualityController.getCurrentAirQuality(city);

        assertThat(actualResponse)
                .isSameAs(expectedResponse);

        verify(airQualityService)
                .getCurrentAirQuality("Pune");
    }

    @Test
    void shouldThrowExceptionWhenCityIsNull() {

        assertThatThrownBy(
                () -> airQualityController.getCurrentAirQuality(null)
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("City must not be empty.");

        verifyNoInteractions(airQualityService);
    }

    @Test
    void shouldThrowExceptionWhenCityIsBlank() {

        assertThatThrownBy(
                () -> airQualityController.getCurrentAirQuality("   ")
        )
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("City must not be empty.");

        verifyNoInteractions(airQualityService);
    }
}