package com.airquality.service;

import com.airquality.client.AirQualityClient;
import com.airquality.client.GeocodingClient;
import com.airquality.dto.AirQualityResponse;
import com.airquality.dto.LocationResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AirQualityService {

    private final AirQualityClient airQualityClient;
    private final GeocodingClient geocodingClient;

    public AirQualityService(
            AirQualityClient airQualityClient,
            GeocodingClient geocodingClient) {

        this.airQualityClient = airQualityClient;
        this.geocodingClient = geocodingClient;
    }

    public AirQualityResponse getCurrentAirQuality(String city) {

        // Step 1: Convert city name to latitude/longitude
        LocationResponse location =
                geocodingClient.findLocation(city);

        // Step 2: Get current AQI using coordinates
        Map<String, Object> response =
                airQualityClient.getCurrentAirQuality(
                        location.latitude(),
                        location.longitude());

        // Step 3: Extract current weather/air-quality data
        Map<String, Object> current =
                (Map<String, Object>) response.get("current");

        double aqi = getDouble(current, "us_aqi");

        // Step 4: Build our API response
        return new AirQualityResponse(
                location.name(),
                location.latitude(),
                location.longitude(),
                aqi,
                getAqiCategory(aqi),
                getDouble(current, "pm2_5"),
                getDouble(current, "pm10"),
                getDouble(current, "carbon_monoxide"),
                getDouble(current, "nitrogen_dioxide"),
                getDouble(current, "sulphur_dioxide"),
                getDouble(current, "ozone"),
                (String) current.get("time")
        );
    }

    private double getDouble(
            Map<String, Object> data,
            String key) {

        Object value = data.get(key);

        if (value instanceof Number number) {
            return number.doubleValue();
        }

        return 0.0;
    }

    private String getAqiCategory(double aqi) {

        if (aqi <= 50) {
            return "Good";
        }

        if (aqi <= 100) {
            return "Moderate";
        }

        if (aqi <= 150) {
            return "Unhealthy for Sensitive Groups";
        }

        if (aqi <= 200) {
            return "Unhealthy";
        }

        if (aqi <= 300) {
            return "Very Unhealthy";
        }

        return "Hazardous";
    }
}