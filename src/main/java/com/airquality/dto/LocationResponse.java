package com.airquality.dto;

public record LocationResponse(
        String name,
        double latitude,
        double longitude,
        String country
) {
}
