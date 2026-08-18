package com.airquality.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

class ErrorResponseTest {

    @Test
    void shouldCreateErrorResponseWithAllFields() {

        OffsetDateTime timestamp =
                OffsetDateTime.parse("2026-08-19T00:30:00+05:30");

        ErrorResponse response = new ErrorResponse(
                404,
                "Not Found",
                "City not found.",
                "/api/v1/air-quality/current",
                timestamp
        );

        assertThat(response.status())
                .isEqualTo(404);

        assertThat(response.error())
                .isEqualTo("Not Found");

        assertThat(response.message())
                .isEqualTo("City not found.");

        assertThat(response.path())
                .isEqualTo("/api/v1/air-quality/current");

        assertThat(response.timestamp())
                .isEqualTo(timestamp);
    }

    @Test
    void shouldSupportRecordEquality() {

        OffsetDateTime timestamp =
                OffsetDateTime.parse("2026-08-19T00:30:00+05:30");

        ErrorResponse response1 = new ErrorResponse(
                404,
                "Not Found",
                "City not found.",
                "/api/v1/air-quality/current",
                timestamp
        );

        ErrorResponse response2 = new ErrorResponse(
                404,
                "Not Found",
                "City not found.",
                "/api/v1/air-quality/current",
                timestamp
        );

        assertThat(response1)
                .isEqualTo(response2);

        assertThat(response1.hashCode())
                .isEqualTo(response2.hashCode());
    }
}