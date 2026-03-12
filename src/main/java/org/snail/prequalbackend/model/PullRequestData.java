package org.snail.prequalbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PullRequestData(
        String organisation,
        String repo,
        int prId,
        Metadata metadata,
        List<Comment> comments,
        @JsonProperty("head_metrics") Metrics headMetrics,
        @JsonProperty("base_metrics") Metrics baseMetrics
) {}
