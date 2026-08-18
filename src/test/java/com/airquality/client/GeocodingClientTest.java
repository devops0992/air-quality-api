package com.airquality.client;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import com.airquality.dto.LocationResponse;
import com.airquality.exception.CityNotFoundException;
import com.airquality.exception.ExternalServiceException;

class GeocodingClientTest {

    private GeocodingClient geocodingClient;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {

        RestClient.Builder builder = RestClient.builder();

        mockServer = MockRestServiceServer.bindTo(builder).build();

        geocodingClient = new GeocodingClient(
                builder,
                "http://localhost"
        );
    }

    @Test
    void shouldFindLocationSuccessfully() {

        String response = """
                {
                    "results": [
                        {
                            "name": "Pune",
                            "latitude": 18.5204,
                            "longitude": 73.8567,
                            "country": "India"
                        }
                    ]
                }
                """;

        String expectedUri =
                "http://localhost/v1/search"
                        + "?name=Pune"
                        + "&count=1"
                        + "&language=en"
                        + "&format=json";

        mockServer
                .expect(requestTo(expectedUri))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                response,
                                MediaType.APPLICATION_JSON
                        )
                );

        LocationResponse result =
                geocodingClient.findLocation("Pune");

        assertThat(result).isNotNull();

        assertThat(result.name())
                .isEqualTo("Pune");

        assertThat(result.latitude())
                .isEqualTo(18.5204);

        assertThat(result.longitude())
                .isEqualTo(73.8567);

        assertThat(result.country())
                .isEqualTo("India");

        mockServer.verify();
    }

    @Test
    void shouldThrowCityNotFoundExceptionWhenResponseIsNull() {

        mockServer
                .expect(requestTo(
                        "http://localhost/v1/search"
                                + "?name=Pune"
                                + "&count=1"
                                + "&language=en"
                                + "&format=json"
                ))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body("null")
                );

        assertThatThrownBy(
                () -> geocodingClient.findLocation("Pune")
        )
                .isInstanceOf(CityNotFoundException.class);

        mockServer.verify();
    }

    @Test
    void shouldThrowCityNotFoundExceptionWhenResultsAreNull() {

        String response = """
                {
                    "results": null
                }
                """;

        mockServer
                .expect(requestTo(
                        "http://localhost/v1/search"
                                + "?name=Pune"
                                + "&count=1"
                                + "&language=en"
                                + "&format=json"
                ))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                response,
                                MediaType.APPLICATION_JSON
                        )
                );

        assertThatThrownBy(
                () -> geocodingClient.findLocation("Pune")
        )
                .isInstanceOf(CityNotFoundException.class);

        mockServer.verify();
    }

    @Test
    void shouldThrowCityNotFoundExceptionWhenResultsAreEmpty() {

        String response = """
                {
                    "results": []
                }
                """;

        mockServer
                .expect(requestTo(
                        "http://localhost/v1/search"
                                + "?name=Pune"
                                + "&count=1"
                                + "&language=en"
                                + "&format=json"
                ))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                response,
                                MediaType.APPLICATION_JSON
                        )
                );

        assertThatThrownBy(
                () -> geocodingClient.findLocation("Pune")
        )
                .isInstanceOf(CityNotFoundException.class);

        mockServer.verify();
    }

    @Test
    void shouldRethrowCityNotFoundException() {

        String response = """
                {
                    "results": []
                }
                """;

        mockServer
                .expect(requestTo(
                        "http://localhost/v1/search"
                                + "?name=UnknownCity"
                                + "&count=1"
                                + "&language=en"
                                + "&format=json"
                ))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withSuccess(
                                response,
                                MediaType.APPLICATION_JSON
                        )
                );

        assertThatThrownBy(
                () -> geocodingClient.findLocation("UnknownCity")
        )
                .isExactlyInstanceOf(CityNotFoundException.class)
                .hasMessageContaining("UnknownCity");

        mockServer.verify();
    }

    @Test
    void shouldThrowExternalServiceExceptionWhenRestClientFails() {

        mockServer
                .expect(requestTo(
                        "http://localhost/v1/search"
                                + "?name=Pune"
                                + "&count=1"
                                + "&language=en"
                                + "&format=json"
                ))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withBadRequest()
                );

        assertThatThrownBy(
                () -> geocodingClient.findLocation("Pune")
        )
                .isInstanceOf(ExternalServiceException.class)
                .hasMessageContaining(
                        "Unable to retrieve location data."
                );

        mockServer.verify();
    }
}