package org.snail.prequalbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Comment (
        String body,
        @JsonProperty("created_at") String createdAt
) {
}
