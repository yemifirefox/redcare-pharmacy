package com.redcare.pharmacy.controller;

import com.redcare.pharmacy.dto.Repository;
import com.redcare.pharmacy.dto.RepositoryDto;
import com.redcare.pharmacy.service.GitHubService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/repositories")
public class RepositoryController {

    private GitHubService gitHubService;

    public RepositoryController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Repository.Item>> getPopularRepositories(@RequestParam(defaultValue = "10") int limit,
                                                                        @RequestParam(required = false, value = "created_at") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                                        @RequestParam(required = false) String language) {

        return ResponseEntity.ok(gitHubService.getPopularRepositories(new RepositoryDto(limit, fromDate, language)));
    }
}
