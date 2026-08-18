package com.airquality.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.airquality.client.AirQualityClient;
import com.airquality.client.GeocodingClient;
import com.airquality.dto.AirQualityResponse;
import com.airquality.dto.LocationResponse;

@ExtendWith(MockitoExtension.class)
class AirQualityServiceTest {

    @Mock
    private AirQualityClient airQualityClient;

    @Mock
    private GeocodingClient geocodingClient;

    private AirQualityService airQualityService;

    @BeforeEach
    void setUp() {
        airQualityService = new AirQualityService(
                airQualityClient,
                geocodingClient
        );
    }

    @Test
    void shouldReturnAirQualityResponseForGoodAqi() {

        LocationResponse location = new LocationResponse(
                "Pune",
                18.5204,
                73.8567,
                "India"
        );

        Map<String, Object> current = createCurrentData(45.0);

        Map<String, Object> response = new HashMap<>();
        response.put("current", current);

        when(geocodingClient.findLocation("Pune"))
                .thenReturn(location);

        when(airQualityClient.getCurrentAirQuality(
                18.5204,
                73.8567))
                .thenReturn(response);

        AirQualityResponse result =
                airQualityService.getCurrentAirQuality("Pune");

        assertThat(result).isNotNull();

        assertThat(result.city())
                .isEqualTo("Pune");

        assertThat(result.latitude())
                .isEqualTo(18.5204);

        assertThat(result.longitude())
                .isEqualTo(73.8567);

        assertThat(result.aqi())
                .isEqualTo(45.0);

        assertThat(result.category())
                .isEqualTo("Good");

        assertThat(result.pm25())
                .isEqualTo(42.5);

        assertThat(result.pm10())
                .isEqualTo(65.2);

        assertThat(result.carbonMonoxide())
                .isEqualTo(250.0);

        assertThat(result.nitrogenDioxide())
                .isEqualTo(30.5);

        assertThat(result.sulphurDioxide())
                .isEqualTo(5.2);

        assertThat(result.ozone())
                .isEqualTo(100.4);

        assertThat(result.timestamp())
                .isEqualTo("2026-08-19T00:30:00Z");

        verify(geocodingClient)
                .findLocation("Pune");

        verify(airQualityClient)
                .getCurrentAirQuality(
                        18.5204,
                        73.8567
                );
    }

    @Test
    void shouldReturnModerateCategoryWhenAqiIsBetween51And100() {

        AirQualityResponse result =
                executeWithAqi(100.0);

        assertThat(result.category())
                .isEqualTo("Moderate");
    }

    @Test
    void shouldReturnUnhealthyForSensitiveGroupsWhenAqiIsBetween101And150() {

        AirQualityResponse result =
                executeWithAqi(150.0);

        assertThat(result.category())
                .isEqualTo("Unhealthy for Sensitive Groups");
    }

    @Test
    void shouldReturnUnhealthyWhenAqiIsBetween151And200() {

        AirQualityResponse result =
                executeWithAqi(200.0);

        assertThat(result.category())
                .isEqualTo("Unhealthy");
    }

    @Test
    void shouldReturnVeryUnhealthyWhenAqiIsBetween201And300() {

        AirQualityResponse result =
                executeWithAqi(300.0);

        assertThat(result.category())
                .isEqualTo("Very Unhealthy");
    }

    @Test
    void shouldReturnHazardousWhenAqiIsGreaterThan300() {

        AirQualityResponse result =
                executeWithAqi(301.0);

        assertThat(result.category())
                .isEqualTo("Hazardous");
    }

    @Test
    void shouldReturnZeroWhenAirQualityValueIsNotANumber() {

        LocationResponse location = new LocationResponse(
                "Pune",
                18.5204,
                73.8567,
                "India"
        );

        Map<String, Object> current = new HashMap<>();

        current.put("us_aqi", "invalid");
        current.put("pm2_5", "invalid");
        current.put("pm10", null);
        current.put("carbon_monoxide", "invalid");
        current.put("nitrogen_dioxide", "invalid");
        current.put("sulphur_dioxide", "invalid");
        current.put("ozone", "invalid");
        current.put("time", "2026-08-19T00:30:00Z");

        Map<String, Object> response = new HashMap<>();
        response.put("current", current);

        when(geocodingClient.findLocation("Pune"))
                .thenReturn(location);

        when(airQualityClient.getCurrentAirQuality(
                18.5204,
                73.8567))
                .thenReturn(response);

        AirQualityResponse result =
                airQualityService.getCurrentAirQuality("Pune");

        assertThat(result.aqi())
                .isEqualTo(0.0);

        assertThat(result.category())
                .isEqualTo("Good");

        assertThat(result.pm25())
                .isEqualTo(0.0);

        assertThat(result.pm10())
                .isEqualTo(0.0);

        assertThat(result.carbonMonoxide())
                .isEqualTo(0.0);

        assertThat(result.nitrogenDioxide())
                .isEqualTo(0.0);

        assertThat(result.sulphurDioxide())
                .isEqualTo(0.0);

        assertThat(result.ozone())
                .isEqualTo(0.0);
    }

    @Test
    void shouldHandleIntegerNumericValues() {

        LocationResponse location = new LocationResponse(
                "Pune",
                18.5204,
                73.8567,
                "India"
        );

        Map<String, Object> current = new HashMap<>();

        current.put("us_aqi", 50);
        current.put("pm2_5", 40);
        current.put("pm10", 60);
        current.put("carbon_monoxide", 200);
        current.put("nitrogen_dioxide", 25);
        current.put("sulphur_dioxide", 4);
        current.put("ozone", 90);
        current.put("time", "2026-08-19T00:30:00Z");

        Map<String, Object> response = new HashMap<>();
        response.put("current", current);

        when(geocodingClient.findLocation("Pune"))
                .thenReturn(location);

        when(airQualityClient.getCurrentAirQuality(
                18.5204,
                73.8567))
                .thenReturn(response);

        AirQualityResponse result =
                airQualityService.getCurrentAirQuality("Pune");

        assertThat(result.aqi())
                .isEqualTo(50.0);

        assertThat(result.pm25())
                .isEqualTo(40.0);

        assertThat(result.pm10())
                .isEqualTo(60.0);

        assertThat(result.carbonMonoxide())
                .isEqualTo(200.0);

        assertThat(result.nitrogenDioxide())
                .isEqualTo(25.0);

        assertThat(result.sulphurDioxide())
                .isEqualTo(4.0);

        assertThat(result.ozone())
                .isEqualTo(90.0);
    }

    private AirQualityResponse executeWithAqi(double aqi) {

        LocationResponse location = new LocationResponse(
                "Pune",
                18.5204,
                73.8567,
                "India"
        );

        Map<String, Object> current =
                createCurrentData(aqi);

        Map<String, Object> response = new HashMap<>();
        response.put("current", current);

        when(geocodingClient.findLocation("Pune"))
                .thenReturn(location);

        when(airQualityClient.getCurrentAirQuality(
                18.5204,
                73.8567))
                .thenReturn(response);

        return airQualityService.getCurrentAirQuality("Pune");
    }

    private Map<String, Object> createCurrentData(double aqi) {

        Map<String, Object> current = new HashMap<>();

        current.put("us_aqi", aqi);
        current.put("pm2_5", 42.5);
        current.put("pm10", 65.2);
        current.put("carbon_monoxide", 250.0);
        current.put("nitrogen_dioxide", 30.5);
        current.put("sulphur_dioxide", 5.2);
        current.put("ozone", 100.4);
        current.put("time", "2026-08-19T00:30:00Z");

        return current;
    }
}