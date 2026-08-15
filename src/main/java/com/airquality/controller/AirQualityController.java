package com.airquality.controller;

import com.airquality.dto.AirQualityResponse;
import com.airquality.service.AirQualityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/air-quality")
public class AirQualityController {

    private final AirQualityService airQualityService;

    public AirQualityController(
            AirQualityService airQualityService) {

        this.airQualityService = airQualityService;
    }

    @GetMapping("/current")
    public AirQualityResponse getCurrentAirQuality(
            @RequestParam String city) {

        if (city == null || city.isBlank()){
            throw new IllegalArgumentException("City must not be empty.");
        }        
        return airQualityService.getCurrentAirQuality(city.trim());
    }
}