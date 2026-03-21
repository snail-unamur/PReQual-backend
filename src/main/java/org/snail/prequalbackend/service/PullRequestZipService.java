package org.snail.prequalbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PullRequestZipService {

    @Value("${dataset.root}")
    private String datasetRoot;

    public FileSystemResource getZipFile(String organisation, String repo, int prId, boolean isHead) {
        String fileName = isHead ? "head.zip" : "base.zip";

        Path prFolder = Path.of(datasetRoot, organisation, repo, "pr_" + prId);
        Path zipPath = prFolder.resolve(fileName);

        if (!Files.exists(zipPath)) {
            throw new RuntimeException("Zip file not found: " + zipPath);
        }

        return new FileSystemResource(zipPath);
    }
}

