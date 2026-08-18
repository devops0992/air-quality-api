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

    public AirQualityResponse(String string, double d, double e, int i, double f, double g, double h, double j,
            double k, double l, String string2) {
        this(string, d, e, i, "", f, g, h, j, k, l, string2);
    }

    
}
