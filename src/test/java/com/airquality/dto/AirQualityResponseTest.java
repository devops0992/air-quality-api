package com.airquality.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AirQualityResponseTest {

    @Test
    void shouldCreateAirQualityResponseUsingFullConstructor() {

        AirQualityResponse response = new AirQualityResponse(
                "Pune",
                18.5204,
                73.8567,
                85.0,
                "Moderate",
                42.5,
                65.2,
                250.0,
                30.5,
                5.2,
                100.4,
                "2026-08-19T00:00:00"
        );

        assertThat(response.city())
                .isEqualTo("Pune");

        assertThat(response.latitude())
                .isEqualTo(18.5204);

        assertThat(response.longitude())
                .isEqualTo(73.8567);

        assertThat(response.aqi())
                .isEqualTo(85.0);

        assertThat(response.category())
                .isEqualTo("Moderate");

        assertThat(response.pm25())
                .isEqualTo(42.5);

        assertThat(response.pm10())
                .isEqualTo(65.2);

        assertThat(response.carbonMonoxide())
                .isEqualTo(250.0);

        assertThat(response.nitrogenDioxide())
                .isEqualTo(30.5);

        assertThat(response.sulphurDioxide())
                .isEqualTo(5.2);

        assertThat(response.ozone())
                .isEqualTo(100.4);

        assertThat(response.timestamp())
                .isEqualTo("2026-08-19T00:00:00");
    }

    @Test
    void shouldCreateAirQualityResponseUsingOverloadedConstructor() {

        AirQualityResponse response = new AirQualityResponse(
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

        assertThat(response.city())
                .isEqualTo("Pune");

        assertThat(response.latitude())
                .isEqualTo(18.5204);

        assertThat(response.longitude())
                .isEqualTo(73.8567);

        assertThat(response.aqi())
                .isEqualTo(85.0);

        assertThat(response.category())
                .isEmpty();

        assertThat(response.pm25())
                .isEqualTo(42.5);

        assertThat(response.pm10())
                .isEqualTo(65.2);

        assertThat(response.carbonMonoxide())
                .isEqualTo(250.0);

        assertThat(response.nitrogenDioxide())
                .isEqualTo(30.5);

        assertThat(response.sulphurDioxide())
                .isEqualTo(5.2);

        assertThat(response.ozone())
                .isEqualTo(100.4);

        assertThat(response.timestamp())
                .isEqualTo("2026-08-19T00:00:00");
    }

    @Test
    void shouldSupportRecordEquality() {

        AirQualityResponse response1 = new AirQualityResponse(
                "Pune",
                18.5204,
                73.8567,
                85.0,
                "Moderate",
                42.5,
                65.2,
                250.0,
                30.5,
                5.2,
                100.4,
                "2026-08-19T00:00:00"
        );

        AirQualityResponse response2 = new AirQualityResponse(
                "Pune",
                18.5204,
                73.8567,
                85.0,
                "Moderate",
                42.5,
                65.2,
                250.0,
                30.5,
                5.2,
                100.4,
                "2026-08-19T00:00:00"
        );

        assertThat(response1)
                .isEqualTo(response2);

        assertThat(response1.hashCode())
                .isEqualTo(response2.hashCode());
    }
}