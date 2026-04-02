package org.snail.prequalbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.snail.prequalbackend.model.*;

import java.time.Instant;
import java.util.List;

public record PullRequestDTO(
        String id,
        @JsonProperty("analysed_at") Instant analysedAt,
        Metrics base,
        Metrics head,
        List<Comment> comments,
        Metadata meta,
        Size size,
        Stats stats,
        List<Review> reviews,
        String reviewDecision,
        String org,
        String repo
) {
    public static PullRequestDTO fromPullRequest(PullRequest pr) {
        return new PullRequestDTO(
                pr.id,
                pr.analysedAt,
                pr.base,
                pr.head,
                pr.comments,
                pr.meta,
                pr.size,
                pr.stats,
                pr.reviews,
                pr.reviewDecision,
                pr.org,
                pr.repo
        );
    }
}

