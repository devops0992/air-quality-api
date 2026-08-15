package com.airquality.client;

import com.airquality.dto.LocationResponse;
import com.airquality.exception.CityNotFoundException;
import com.airquality.exception.ExternalServiceException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

@Component
public class GeocodingClient {

    private final RestClient restClient;

    public GeocodingClient(
            RestClient.Builder restClientBuilder,
            @Value("${geocoding.api.base-url}") String baseUrl) {

        this.restClient = restClientBuilder
                .baseUrl(baseUrl)
                .build();
    }

    public LocationResponse findLocation(String city) {

        try {

            Map<String, Object> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/search")
                            .queryParam("name", city)
                            .queryParam("count", 1)
                            .queryParam("language", "en")
                            .queryParam("format", "json")
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {});

            if (response == null || response.get("results") == null) {
                throw new CityNotFoundException(city);
            }

            List<Map<String, Object>> results =
                    (List<Map<String, Object>>) response.get("results");

            if (results.isEmpty()) {
                throw new CityNotFoundException(city);
            }

            Map<String, Object> location = results.get(0);

            return new LocationResponse(
                    (String) location.get("name"),
                    ((Number) location.get("latitude")).doubleValue(),
                    ((Number) location.get("longitude")).doubleValue(),
                    (String) location.get("country")
            );

        } catch (CityNotFoundException exception) {

            throw exception;

        } catch (RestClientException exception) {

            throw new ExternalServiceException(
                    "Unable to retrieve location data.",
                    exception
            );
        }
    }
}