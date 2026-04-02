package org.snail.prequalbackend.model;

import org.springframework.data.mongodb.core.mapping.Field;

public record Size(
        int additions,
        int deletions,
        @Field("changed_files") int changedFiles
) {
}
