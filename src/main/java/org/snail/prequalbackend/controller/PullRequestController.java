package org.snail.prequalbackend.controller;

import org.jspecify.annotations.NonNull;
import org.snail.prequalbackend.dto.PullRequestDTO;
import org.snail.prequalbackend.service.PullRequestService;
import org.snail.prequalbackend.service.PullRequestZipService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.FileSystemResource;

import java.util.Collection;
import java.util.stream.Collectors;
import java.io.IOException;

@RestController
@RequestMapping("/pr")
public class PullRequestController {

    private final PullRequestService pullRequestService;
    private final PullRequestZipService zipService;

    public PullRequestController(PullRequestService pullRequestService, PullRequestZipService zipService) {
        this.pullRequestService = pullRequestService;
        this.zipService = zipService;
    }

    @GetMapping("/{organisation}/{repo}/{id}")
    public PullRequestDTO getPR(
            @PathVariable String organisation,
            @PathVariable String repo,
            @PathVariable int id) {
        return PullRequestDTO.fromPullRequest(pullRequestService.getPullRequest(organisation, repo, id));
    }

    @GetMapping("/{organisation}/{repo}")
    public Collection<PullRequestDTO> getAllPR(
            @PathVariable String organisation,
            @PathVariable String repo) {
        return pullRequestService.getAllPullRequests(organisation, repo)
                .stream()
                .map(PullRequestDTO::fromPullRequest)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{organisation}/{repo}/{id}/head", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<FileSystemResource> downloadHeadZip(
            @PathVariable String organisation,
            @PathVariable String repo,
            @PathVariable int id) throws IOException {
        FileSystemResource headZip = zipService.getZipFile(organisation, repo, id, true);
        return generateFileResponse(headZip);
    }

    @GetMapping(value = "/{organisation}/{repo}/{id}/base", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<FileSystemResource> downloadBaseZip(
            @PathVariable String organisation,
            @PathVariable String repo,
            @PathVariable int id) throws IOException {
        FileSystemResource baseZip = zipService.getZipFile(organisation, repo, id, false);
        return generateFileResponse(baseZip);
    }

    private static @NonNull ResponseEntity<FileSystemResource> generateFileResponse(FileSystemResource resource) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
