package org.snail.prequalbackend.controller;

import org.jspecify.annotations.NonNull;
import org.snail.prequalbackend.model.PullRequestData;
import org.snail.prequalbackend.service.PullRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.FileSystemResource;

import java.util.Collection;
import java.io.IOException;

@RestController
@RequestMapping("/pr")
public class PullRequestController {

    private final PullRequestService service;

    public PullRequestController(PullRequestService service) {
        this.service = service;
    }

    @GetMapping("/{organisation}/{repo}/{id}")
    public PullRequestData getPR(
            @PathVariable String organisation,
            @PathVariable String repo,
            @PathVariable int id) {
        return service.getPullRequest(organisation, repo, id);
    }

    @GetMapping("/{organisation}/{repo}")
    public Collection<PullRequestData> getAllPR(
            @PathVariable String organisation,
            @PathVariable String repo) {
        return service.getAllPullRequests(organisation, repo);
    }

    @GetMapping(value = "/{organisation}/{repo}/{id}/head", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<FileSystemResource> downloadHeadZip(
            @PathVariable String organisation,
            @PathVariable String repo,
            @PathVariable int id) throws IOException {
        FileSystemResource headZip = service.getBranchZip(organisation, repo, id, true);
        return generateFileResponse(headZip);
    }

    @GetMapping(value = "/{organisation}/{repo}/{id}/base", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<FileSystemResource> downloadBaseZip(
            @PathVariable String organisation,
            @PathVariable String repo,
            @PathVariable int id) throws IOException {
        FileSystemResource baseZip = service.getBranchZip(organisation, repo, id, false);
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
