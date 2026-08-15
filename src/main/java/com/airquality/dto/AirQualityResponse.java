package com.airquality.dto;

public record AirQualityResponse (
        String city,
        double latitude,
        double longitude,
        double aqi,
        String category,
        double pm25,
        double pm10,
        double carbonMonoxide,
        double nitrogenDioxide,
        double sulphurDioxide,
        double ozone,
        String timestamp)
        {

    
}
