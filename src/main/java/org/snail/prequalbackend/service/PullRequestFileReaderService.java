package org.snail.prequalbackend.service;

import org.snail.prequalbackend.model.Comment;
import org.snail.prequalbackend.model.Metadata;
import org.snail.prequalbackend.model.Metrics;
import org.snail.prequalbackend.model.PullRequestData;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.List;

@Service
public class PullRequestFileReaderService {

    private final ObjectMapper mapper = new ObjectMapper();

    public PullRequestData readPRFromDisk(Path prFolder, String organisation, String repo, int prId) {
        try {
            Metadata metadata = mapper.readValue(prFolder.resolve("metadata.json").toFile(), Metadata.class);
            List<Comment> comments = mapper.readValue(prFolder.resolve("comments.json").toFile(),
                    mapper.getTypeFactory().constructCollectionType(List.class, Comment.class));

            Metrics head = mapper.readValue(prFolder.resolve("head_metrics.json").toFile(), Metrics.class);
            Metrics base = mapper.readValue(prFolder.resolve("base_metrics.json").toFile(), Metrics.class);

            return new PullRequestData(organisation, repo, prId, metadata, comments, head, base);

        } catch (JacksonException e) {
            throw new RuntimeException("Erreur lecture JSON: " + prFolder, e);
        }
    }

    public FileSystemResource readFileFromDisk(Path zipPath) {
        try {
            return new FileSystemResource(zipPath);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lecture zip: " + zipPath, e);
        }
    }
}
