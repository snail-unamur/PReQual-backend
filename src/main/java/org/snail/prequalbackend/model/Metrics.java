package org.snail.prequalbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Metrics(
        @JsonProperty("code_smells") int codeSmells,
        @JsonProperty("cognitive_complexity") int cognitiveComplexity,
        int complexity,
        @JsonProperty("development_cost") int developmentCost,
        @JsonProperty("duplicated_lines") int duplicatedLines,
        int ncloc,
        @JsonProperty("software_quality_maintainability_rating") int softwareQualityMaintainabilityRating
) {}
