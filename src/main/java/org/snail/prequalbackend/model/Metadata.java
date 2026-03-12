package org.snail.prequalbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Metadata(
        String title,
        String body,
        String state,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("closed_at") String closedAt
) {}
