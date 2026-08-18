package com.airquality.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class HealthControllerTest {

    private HealthController healthController;

    @BeforeEach
    void setUp() {

        healthController = new HealthController();

        ReflectionTestUtils.setField(
                healthController,
                "applicationName",
                "air-quality-api"
        );

        ReflectionTestUtils.setField(
                healthController,
                "version",
                "1.0.0"
        );
    }

    @Test
    void shouldReturnHealthInformation() {

        Map<String, Object> result =
                healthController.health();

        assertThat(result).isNotNull();

        assertThat(result)
                .containsEntry("status", "UP");

        assertThat(result)
                .containsEntry("service", "air-quality-api");

        assertThat(result)
                .hasSize(2);
    }

    @Test
    void shouldReturnApplicationInfo() {

        Map<String, Object> result =
                healthController.info();

        assertThat(result).isNotNull();

        assertThat(result)
                .containsEntry("service", "air-quality-api");

        assertThat(result)
                .containsEntry(
                        "description",
                        "Real-Time Air Quality Intelligence Platform"
                );

        assertThat(result)
                .containsKey("environment");

        assertThat(result)
                .hasSize(3);
    }

    @Test
    void shouldReturnVersionInformation() {

        Map<String, String> result =
                healthController.version();

        assertThat(result).isNotNull();

        assertThat(result)
                .containsEntry("version", "1.0.0");

        assertThat(result)
                .containsEntry("service", "air-quality-api");

        assertThat(result)
                .hasSize(2);
    }
}