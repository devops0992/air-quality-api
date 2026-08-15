package com.airquality.client;
import org.springframework.web.client.RestClient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

@Component
public class AirQualityClient {
    
    private final RestClient restClient;

    public AirQualityClient(RestClient.Builder restClientBuilder, @Value("${air-quality.api.base-url}") String baseUrl) {
        this.restClient = restClientBuilder
                 .baseUrl(baseUrl)
                 .build();
    }

    public Map<String, Object> getCurrentAirQuality(
        double latitude,
        double longitude) {

      return restClient.get()
          .uri(uriBuilder -> uriBuilder
              .path("/v1/air-quality")
              .queryParam("latitude", latitude)
              .queryParam("longitude", longitude)
              .queryParam(
                "current",
                "us_aqi,pm2_5,pm10,carbon_monoxide," + 
                "nitrogen_dioxide,sulphur_dioxide,ozone")
              .queryParam("timezone", "auto")  
              .build())
          .retrieve()
          .body(new ParameterizedTypeReference<Map<String, Object>>() {});
    }

}
