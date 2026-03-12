package org.snail.prequalbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Metrics(
        @JsonProperty("code_smells") Integer codeSmells,
        @JsonProperty("cognitive_complexity") Integer cognitiveComplexity,
        Integer complexity,
        @JsonProperty("development_cost") Integer developmentCost,
        @JsonProperty("duplicated_lines") Integer duplicatedLines,
        Integer ncloc,
        @JsonProperty("software_quality_maintainability_rating") Integer softwareQualityMaintainabilityRating
) {}
