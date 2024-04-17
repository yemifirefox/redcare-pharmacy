package com.redcare.pharmacy.service;

import com.redcare.pharmacy.adapter.GithubPort;
import com.redcare.pharmacy.dto.RepositoryDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static util.TestData.buildResponse;

@ExtendWith(MockitoExtension.class)
class GitHubServiceImplTest {
    private GitHubService gitHubService;

    @Mock
    GithubPort githubPort;

    @BeforeEach
    void beforeEach() {
        gitHubService =
                new GitHubServiceImpl(githubPort);
    }

    @Test
    void returnPopularRepositories(){
        var date = LocalDate.now();
        var expected = buildResponse(date.toString());

        when(githubPort.getPopularRepositories(any())).thenReturn(expected);

        var response = gitHubService.getPopularRepositories(new RepositoryDto(10, date, "java"));

        assertNotNull(response);
        assertThat(response.size()).isEqualTo(2);
    }
}
