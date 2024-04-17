package com.redcare.pharmacy.service;

import com.redcare.pharmacy.adapter.GithubPort;
import com.redcare.pharmacy.dto.Repository;
import com.redcare.pharmacy.dto.RepositoryDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService{
    private final GithubPort githubPort;

    public GitHubServiceImpl(GithubPort githubPort) {
        this.githubPort = githubPort;
    }

    @Override
    public List<Repository.Item> getPopularRepositories(RepositoryDto repositoryDto) {
        return githubPort.getPopularRepositories(repositoryDto);
    }
}
