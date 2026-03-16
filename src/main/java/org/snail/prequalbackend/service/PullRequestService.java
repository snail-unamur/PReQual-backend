package org.snail.prequalbackend.service;

import org.snail.prequalbackend.model.PullRequestData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Service
public class PullRequestService {

    @Value("${dataset.root}")
    private String datasetRoot;
    private final PullRequestFileReaderService fsReader;

    public PullRequestService(PullRequestFileReaderService fsReader) {
        this.fsReader = fsReader;
    }

    @Cacheable(value = "prCache", key = "#organisation + ':' + #repo + ':' + #prId")
    public PullRequestData getPullRequest(String organisation, String repo, int prId) {

        Path repoPath = Path.of(datasetRoot, organisation, repo);
        Path prFolder = repoPath.resolve("pr_" + prId);

        if (!Files.exists(prFolder)) {
            throw new RuntimeException("PR folder not found: " + prFolder);
        }

        return fsReader.readPRFromDisk(prFolder, organisation, repo, prId);
    }

    @Cacheable(value = "repoCache", key = "#organisation + ':' + #repo")
    public List<PullRequestData> getAllPullRequests(String organisation, String repo) {

        Path repoPath = Path.of(datasetRoot, organisation, repo);

        if (!Files.exists(repoPath)) {
            throw new RuntimeException("Repo not found: " + repoPath);
        }

        try (Stream<Path> paths = Files.list(repoPath)) {
            return paths
                    .filter(Files::isDirectory)
                    .filter(p -> p.getFileName().toString().startsWith("pr_"))
                    .map(prDir -> {
                        String folder = prDir.getFileName().toString();
                        int prId = Integer.parseInt(folder.substring(3));
                        return getPullRequest(organisation, repo, prId);
                    })
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("Erreur scan repo: " + repoPath, e);
        }
    }

    public FileSystemResource getBranchZip(String organisation, String repo, int id, boolean isHead) {
        String fileName = isHead ? "head.zip" : "base.zip";

        Path prFolder = Path.of(datasetRoot, organisation, repo, "pr_" + id);
        Path zipPath = prFolder.resolve(fileName);
        if (!Files.exists(zipPath)) {
            throw new RuntimeException("Zip file not found: " + zipPath);
        }

        return fsReader.readFileFromDisk(zipPath);
    }
}
