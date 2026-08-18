package com.airquality.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class AirQualityClientTest {

    private AirQualityClient airQualityClient;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {

        RestClient.Builder builder = RestClient.builder();

        mockServer = MockRestServiceServer.bindTo(builder).build();

        airQualityClient = new AirQualityClient(
                builder,
                "http://localhost"
        );
    }

    @Test
    void shouldGetCurrentAirQualitySuccessfully() {

        String response = """
                {
                    "latitude": 18.5204,
                    "longitude": 73.8567,
                    "current": {
                        "us_aqi": 85,
                        "pm2_5": 42.5,
                        "pm10": 65.2,
                        "carbon_monoxide": 250.0,
                        "nitrogen_dioxide": 30.5,
                        "sulphur_dioxide": 5.2,
                        "ozone": 100.4
                    }
                }
                """;

        String expectedUri =
                "http://localhost/v1/air-quality"
                        + "?latitude=18.5204"
                        + "&longitude=73.8567"
                        + "&current=us_aqi,pm2_5,pm10,carbon_monoxide,"
                        + "nitrogen_dioxide,sulphur_dioxide,ozone"
                        + "&timezone=auto";

        mockServer
                .expect(requestTo(expectedUri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                response,
                                MediaType.APPLICATION_JSON
                        )
                );

        Map<String, Object> result =
                airQualityClient.getCurrentAirQuality(
                        18.5204,
                        73.8567
                );

        assertThat(result).isNotNull();

        assertThat(result)
                .containsKeys(
                        "latitude",
                        "longitude",
                        "current"
                );

        assertThat(result.get("latitude"))
                .isEqualTo(18.5204);

        assertThat(result.get("longitude"))
                .isEqualTo(73.8567);

        @SuppressWarnings("unchecked")
        Map<String, Object> current =
                (Map<String, Object>) result.get("current");

        assertThat(current)
                .containsEntry("us_aqi", 85);

        assertThat(current)
                .containsEntry("pm2_5", 42.5);

        assertThat(current)
                .containsEntry("pm10", 65.2);

        assertThat(current)
                .containsEntry("carbon_monoxide", 250.0);

        assertThat(current)
                .containsEntry("nitrogen_dioxide", 30.5);

        assertThat(current)
                .containsEntry("sulphur_dioxide", 5.2);

        assertThat(current)
                .containsEntry("ozone", 100.4);

        mockServer.verify();
    }
}