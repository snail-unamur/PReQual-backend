package org.snail.prequalbackend.controller;

import org.snail.prequalbackend.model.PullRequestData;
import org.snail.prequalbackend.service.PullRequestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

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
}
